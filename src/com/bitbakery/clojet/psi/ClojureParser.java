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
    private DefaultParser defaultParser;

    public ClojureParser() {
        defaultParser = new DefaultParser();

        markers = new Stack<PsiBuilder.Marker>();

        parsers = new HashMap<IElementType, Parser>();
        parsers.put(LEFT_PAREN, new ParenParser());
        parsers.put(LEFT_SQUARE, new SquareBracketParser());
        parsers.put(LEFT_CURLY, new CurlyBracketParser());
/*
        parsers.put(DEF, new DefParser());
        parsers.put(DEFN, new DefnParser());
        parsers.put(DEFMACRO, new DefmacroParser());
        parsers.put(FN, new FnParser());
*/
    }

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();
        try {
            while (!builder.eof()) {
                getParser(builder.getTokenType()).parse(builder);
            }
        } catch (EofException e) {
            while (!markers.empty()) {
                markers.pop().error("Unexpected end-of-file");
            }
        }
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    private boolean is(PsiBuilder builder, IElementType elementType) {
        return elementType == builder.getTokenType();
    }

    private boolean not(PsiBuilder builder, IElementType elementType) {
        return !is(builder, elementType);
    }

    private Parser getParser(IElementType tokenType) {
        Parser p = parsers.get(tokenType);
        return p != null ? p : defaultParser;
    }


    private void markAndAdvance(PsiBuilder builder) {
        mark(builder);
        advanceAndCheck(builder);
    }

    private void mark(PsiBuilder builder) {
        markers.push(builder.mark());
    }

    private void advanceAndCheck(PsiBuilder builder) {
        advance(builder);
        if (builder.eof()) {
            throw new EofException();
        }
    }

    private void advance(PsiBuilder builder) {
        System.out.println(builder.getTokenText());
        builder.advanceLexer();
    }

    private void done(IElementType type) {
        markers.pop().done(type);
    }

    private class CurlyBracketParser implements Parser {
        public void parse(PsiBuilder builder) {
            markAndAdvance(builder);
            while (not(builder, RIGHT_CURLY)) {
                advanceAndCheck(builder);
            }
            advance(builder);
            done(MAP);
        }
    }

    private class SquareBracketParser implements Parser {
        public void parse(PsiBuilder builder) {
            markAndAdvance(builder);
            while (not(builder, RIGHT_SQUARE)) {
                advanceAndCheck(builder);
            }
            advance(builder);
            done(VECTOR);
        }
    }

    private class ParenParser implements Parser {
        public void parse(PsiBuilder builder) {
            markAndAdvance(builder);

            IElementType type = EXPRESSION;
            if (is(builder, DEF)) type = DEFINITION;
            else if (is(builder, DEFN)) type = FUNCTION_DEFINITION;
            else if (is(builder, DEFMACRO)) type = MACRO_DEFINITION;
            else if (is(builder, FN)) type = ANONYMOUS_FUNCTION_DEFINITION;

            // TODO - The first element requires special handling - could be def, defn, defmacro,...
            while (not(builder, RIGHT_PAREN)) {
                getParser(builder.getTokenType()).parse(builder);
            }
            advance(builder);
            done(type);
        }
    }

    private class DefmacroParser implements Parser {
        public void parse(PsiBuilder builder) {
        }
    }

    private class DefnParser implements Parser {
        public void parse(PsiBuilder builder) {
        }
    }

    private class DefParser implements Parser {
        public void parse(PsiBuilder builder) {
        }
    }

    private class FnParser implements Parser {
        public void parse(PsiBuilder builder) {
        }
    }

    private class ExpressionParser implements Parser {
        public void parse(PsiBuilder builder) {
        }
    }

    private class DefaultParser implements Parser {
        public void parse(PsiBuilder builder) {
            advanceAndCheck(builder);
        }
    }

    private class EofException extends RuntimeException {

    }

    private interface Parser {
        public void parse(PsiBuilder builder);
    }
}