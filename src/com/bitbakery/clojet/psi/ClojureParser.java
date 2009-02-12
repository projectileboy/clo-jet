package com.bitbakery.clojet.psi;

import static com.bitbakery.clojet.lexer.ClojureTokenTypes.*;
import static com.bitbakery.clojet.psi.ClojureElementTypes.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
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
        parsers.put(FN, new FnParser());
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
            markAndAdvance();
            while (notAt(RIGHT_CURLY)) {
                advanceAndCheck();
            }
            builder.advanceLexer();
            done(MAP);
        }
    }

    private class SquareBracketParser implements Parser {
        public void parse() {
            markAndAdvance();
            while (notAt(RIGHT_SQUARE)) {
                advanceAndCheck();
            }
            builder.advanceLexer();
            done(VECTOR);
        }
    }

    private class ParenParser implements Parser {
        public void parse() {
            markAndAdvance();

            IElementType type = EXPRESSION;
            if (isAt(DEF)) type = DEFINITION;
            else if (isAt(DEFN)) type = FUNCTION_DEFINITION;
            else if (isAt(DEFMACRO)) type = MACRO_DEFINITION;
            else if (isAt(FN)) type = ANONYMOUS_FUNCTION_DEFINITION;

            // TODO - The first element requires special handling - could be def, defn, defmacro,...
            while (notAt(RIGHT_PAREN)) {
                getParser().parse();
            }
            builder.advanceLexer();
            done(type);
        }
    }

    private class DefmacroParser implements Parser {
        public void parse() {
            // We are pointing at 'defmacro'
            advanceAndCheck();

            parseVariableDefinition();
            parseDocstring();

            while (notAt(RIGHT_PAREN)) {
                getParser().parse(); // TODO - This will properly parse the parameters, but it won't recognize them as parameters... hmm....
                // TODO - We don't handle macro-y things at all (backquoted statements, etc.)
            }
        }
    }

    private class DefnParser implements Parser {
        public void parse() {
            // We are pointing at 'defn'
            advanceAndCheck();

            parseVariableDefinition();
            parseDocstring();

            while (notAt(RIGHT_PAREN)) {
                getParser().parse(); // TODO - This will properly parse the parameters, but it won't recognize them as parameters... hmm....
            }
        }
    }

    private class DefParser implements Parser {
        public void parse() {
            // We are pointing at 'def'
            advanceAndCheck();

            parseVariableDefinition();

            while (notAt(RIGHT_PAREN)) {
                getParser().parse();
            }
        }
    }

    private class FnParser implements Parser {
        public void parse() {
            // We are pointing at 'fn'
            advanceAndCheck();

            while (notAt(RIGHT_PAREN)) {
                getParser().parse(); // TODO - This will properly parse the parameters, but it won't recognize them as parameters... hmm....
            }
        }
    }

    private class ExpressionParser implements Parser {
        public void parse() {
        }
    }

    private class DefaultParser implements Parser {
        public void parse() {
            advanceAndCheck();
        }
    }

    private class EofException extends RuntimeException {

    }

    private interface Parser {
        public void parse();
    }

    private void parseVariableDefinition() {
        if (isAt(SYMBOL)) {
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

    private boolean isAt(IElementType elementType) {
        return elementType == builder.getTokenType();
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