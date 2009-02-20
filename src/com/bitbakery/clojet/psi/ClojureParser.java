package com.bitbakery.clojet.psi;

/*
 * Copyright (c) Kurt Christensen, 2009
 *
 * Licensed under the Artistic License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import static com.bitbakery.clojet.lexer.ClojureTokenTypes.*;
import static com.bitbakery.clojet.psi.ClojureElementTypes.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.containers.Stack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Walk through the token stream of a Clojure source file and generate the appropriate PSI tree.
 */
public class ClojureParser implements PsiParser {

    private Stack<PsiBuilder.Marker> markers;
    private Map<IElementType, Parser> parsers;
    private Map<IElementType, IElementType> types;
    private PsiBuilder builder;
    private DefaultParser defaultParser;

    public ClojureParser() {
        defaultParser = new DefaultParser();

        markers = new Stack<PsiBuilder.Marker>();

        parsers = new HashMap<IElementType, Parser>();
        parsers.put(LEFT_PAREN, new ParenParser());
        parsers.put(LEFT_SQUARE, new SquareBracketParser());
        parsers.put(LEFT_CURLY, new CurlyBracketParser());
        parsers.put(DEF, new DefParser());
        parsers.put(DEFN, new DefnParser());
        parsers.put(DEFMACRO, new DefmacroParser());
        parsers.put(DEFMULTI, new DefmultiParser());
        parsers.put(DEFMETHOD, new DefmethodParser());
        parsers.put(DEFSTRUCT, new DefstructParser());
        parsers.put(FN, new FnParser());
        //  parsers.put(LET, new LetParser());

        types = new HashMap<IElementType, IElementType>();
        types.put(DEF, DEFINITION);
        types.put(DEFN, FUNCTION_DEFINITION);
        types.put(DEFMACRO, MACRO_DEFINITION);
        types.put(DEFMULTI, MULTIMETHOD_DEFINITION);
        types.put(DEFMETHOD, METHOD_DEFINITION);
        types.put(DEFSTRUCT, STRUCTURE_DEFINITION);
        types.put(FN, ANONYMOUS_FUNCTION_DEFINITION);
        //   types.put(LET, LET_BLOCK);
    }

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        this.builder = builder;
        final PsiBuilder.Marker rootMarker = builder.mark();
        try {
            while (!builder.eof()) {
                getParser().parse();
            }
        } catch (EofException e) {
            while (!markers.empty()) {
                markers.pop().error("Unexpected end-of-file");
            }
        }
        rootMarker.done(root);

        // Just in case... I can't find any documentation telling me whether or not
        //   this parser is re-used, or even it must be thread-safe!
        this.builder = null;
        this.markers.clear();

        return builder.getTreeBuilt();
    }

    private class CurlyBracketParser implements Parser {
        public void parse() {
            parseMap();
        }
    }

    private class SquareBracketParser implements Parser {
        public void parse() {
            markAndAdvance();
            while (notAt(RIGHT_SQUARE)) {
                advanceAndCheck();
            }
            builder.advanceLexer();
            done(CLOJURE_VECTOR);
        }
    }

    private class ParenParser implements Parser {
        public void parse() {
            // We are pointing at a left paren
            markAndAdvance();

            IElementType type = getExpressionType();

            parseBody(RIGHT_PAREN);
            builder.advanceLexer();
            done(type);
        }

    }

    private class DefmacroParser implements Parser {
        public void parse() {
            // We are pointing at 'defmacro'
            advanceAndCheck();

            parseMetadata();
            parseVariableDefinition();
            parseDocstring();

            // Instead of optional parameters, defmacro allows for multiple implementations based on the number of parameters
            if (isAt(LEFT_PAREN)) {
                while (isAt(LEFT_PAREN)) {
                    advanceAndCheck();
                    parseImplementation();
                    advanceAndCheck();
                }
            } else {
                parseImplementation();
            }
        }
    }

    private class DefnParser implements Parser {
        public void parse() {
            // We are pointing at 'defn'
            advanceAndCheck();

            parseMetadata();
            parseVariableDefinition();
            parseDocstring();
            parseFunctionMetadata();

            // Instead of optional parameters, defn allows for multiple implementations based on the number of parameters
            if (isAt(LEFT_PAREN)) {
                while (isAt(LEFT_PAREN)) {
                    advanceAndCheck();
                    parseImplementation();
                    advanceAndCheck();
                }
            } else {
                parseImplementation();
            }
        }


    }

    private class DefmethodParser implements Parser {
        public void parse() {
            // We are pointing at 'defmethod'
            advanceAndCheck();

            parseVariableDefinition();
            parseDocstring();
            parseDispatchValue();
            parseImplementation();
        }
    }

    private class DefstructParser implements Parser {
        public void parse() {
            // We are pointing at 'defstruct'
            advanceAndCheck();

            parseVariableDefinition();
            parseDocstring();
            parseBody(RIGHT_PAREN); // TODO - This isn't properly parsing struct properties
        }
    }

    private class DefmultiParser implements Parser {
        public void parse() {
            // We are pointing at 'defmulti'
            advanceAndCheck();

            parseVariableDefinition();
            parseDocstring();
            parseBody(RIGHT_PAREN);
        }
    }

    private class DefParser implements Parser {
        public void parse() {
            // We are pointing at 'def'
            advanceAndCheck();
            parseMetadata();
            parseVariableDefinition();
            parseBody(RIGHT_PAREN);
        }
    }

    private class FnParser implements Parser {
        public void parse() {
            // We are pointing at 'fn'
            advanceAndCheck();

            // TODO - This is a bit of a hack... for right now, we'll silently ignore any name specified for an fn
            if (isAt(SYMBOL)) {
                advanceAndCheck();
            }

            parseImplementation();
        }
    }

    private class LetParser implements Parser {
        public void parse() {
            // We are pointing at 'let'
            advanceAndCheck();
            parseBindings();
            parseBody(RIGHT_PAREN);
        }
    }

    private class ExpressionParser implements Parser {
        public void parse() {
        }
    }

    private class DefaultParser implements Parser {
        public void parse() {
            if (isAt(SYMBOL)) {
                markAndAdvance(VARIABLE_REFERENCE);
            } else {
                advanceAndCheck();
            }
        }
    }

    private class EofException extends RuntimeException {

    }

    private interface Parser {
        public void parse();
    }


    private IElementType getExpressionType() {
        IElementType type = types.get(builder.getTokenType());
        return type != null ? type : EXPRESSION;
    }

    private void parseImplementation() {
        mark();
        parseParameters();
        parseBody(RIGHT_PAREN);
        done(IMPLEMENTATION);
    }

    private void parseBody(IElementType terminator) {
        while (notAt(terminator)) {
            getParser().parse();
        }
    }

    private void parseBindings() {
        if (isAt(LEFT_SQUARE)) {

            // TODO - Identify var/value pairs; also handle destructuring binds 
            while (notAt(RIGHT_SQUARE)) {
                getParser().parse();
            }

            builder.advanceLexer();
            done(LET_BINDINGS);
        }
    }

    private void parseMetadata() {
        if (isAt(METADATA)) {
            markAndAdvance();
            if (isAt(LEFT_CURLY)) {
                advanceAndCheck();
                while (notAt(RIGHT_CURLY)) {
                    advanceAndCheck(); // TODO - Group key/value pairs
                }
                builder.advanceLexer();
                done(CLOJURE_METADATA);
            }
        }
    }

    private void parseFunctionMetadata() {
        if (isAt(LEFT_CURLY)) {
            markAndAdvance();
            while (notAt(RIGHT_CURLY)) {
                parseMetadataPair();
            }
            builder.advanceLexer();
            done(CLOJURE_METADATA);
        }
    }

    private void parseMetadataPair() {
        mark();
        parseMetadataKey();
        parseMetadataValue();
        done(CLOJURE_METADATA_PAIR);
    }

    private void parseMetadataKey() {
        if (isAt(KEYWORD)) {
            mark();
            getParser().parse();
            done(CLOJURE_METADATA_KEY);
        } else {
            builder.error("Expected keyword");
        }
    }

    private void parseMetadataValue() {
        mark();
        getParser().parse();
        done(CLOJURE_METADATA_VALUE);
    }

    private void parseVariableDefinition() {
        if (isAt(SYMBOL) || isIn(PSEUDO_SPECIAL_FORMS)) {
            markAndAdvance(VARIABLE_DEFINITION);
        } else {
            builder.error("Expected function name");
        }
    }

    private void parseDocstring() {
        if (isAt(STRING_LITERAL)) {
            markAndAdvance(DOCSTRING);
        }
    }

    private void parseDispatchValue() {
        // TODO - We'll want to do something real here eventually; for now, just glide over anything ahead of the param list
        if (notAt(LEFT_SQUARE)) {
            advanceAndCheck();
        }
    }

    private void parseParameters() {
        if (isAt(LEFT_SQUARE)) {
            markAndAdvance();
            while (notAt(RIGHT_SQUARE)) {
                parseParameter();
            }
            builder.advanceLexer();
            done(PARAMETER_LIST);
        } else {
            builder.error("Expected parameter list");
        }
    }

    private void parseParameter() {
        if (isAt(SYMBOL)) {
            markAndAdvance(PARAMETER);
        } else if (isAt(KEYWORD)) {
            advanceAndCheck(); // TODO - Eventually, we'll want to associate metadata with parameters
        } else if (isAt(REST)) {
            advanceAndCheck();
            if (isAt(SYMBOL)) {
                markAndAdvance(REST_PARAMETER);
            }
            // TODO - Handle errors gracefully - right here, we should assert that we've hit the closing right square bracket
        } else {
            advanceAndCheck(); // TODO - For now, just glide over anything we don't understand
        }
    }

    private void parseMap() {
        markAndAdvance();
        while (notAt(RIGHT_CURLY)) {
            advanceAndCheck();
        }
        builder.advanceLexer();
        done(CLOJURE_MAP);
    }

    private boolean isAt(IElementType elementType) {
        return elementType == builder.getTokenType();
    }

    private boolean isIn(TokenSet tokens) {
        return tokens.contains(builder.getTokenType());
    }

    private boolean notAt(IElementType elementType) {
        return !isAt(elementType);
    }

    private Parser getParser() {
        Parser p = parsers.get(builder.getTokenType());
        return p != null ? p : defaultParser;
    }


    private void markAndAdvance(IElementType type) {
        markAndAdvance();
        done(type);
    }

    private void markAndAdvance() {
        mark();
        advanceAndCheck();
    }

    private void mark() {
        markers.push(builder.mark());
    }

    private void advanceAndCheck() {
        builder.advanceLexer();
        if (builder.eof()) {
            throw new EofException();
        }
    }

    private void done(IElementType type) {
        markers.pop().done(type);
    }
}