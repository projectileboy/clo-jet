package com.bitbakery.clojet;

import com.bitbakery.clojet.lexer.ClojureLexer;
import com.bitbakery.clojet.lexer.ClojureTokenTypes;
import static com.bitbakery.clojet.lexer.ClojureTokenTypes.*;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines Clojure tokens and elements which can have custom font and color to enhance readability
 */
public class ClojureSyntaxHighlighter extends SyntaxHighlighterBase {
    private static Map<IElementType, TextAttributesKey> keys;

    static {
        keys = new HashMap<IElementType, TextAttributesKey>();

        keys.put(TILDE, createTextAttributesKey("CLOJURE.TILDE", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(EQ, createTextAttributesKey("CLOJURE.EQ", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(BACKQUOTE, createTextAttributesKey("CLOJURE.BACKQUOTE", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(QUOTE, createTextAttributesKey("CLOJURE.QUOTE", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(COMMA, createTextAttributesKey("CLOJURE.COMMA", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(COMMA_AT, createTextAttributesKey("CLOJURE.COMMA_AT", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(DOT, createTextAttributesKey("CLOJURE.DOT", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(COMPOSER, createTextAttributesKey("CLOJURE.COMPOSER", HighlighterColors.TEXT.getDefaultAttributes()));

        keys.put(TRUE, createTextAttributesKey("CLOJURE.TRUE", SyntaxHighlighterColors.NUMBER.getDefaultAttributes()));
        keys.put(NIL, createTextAttributesKey("CLOJURE.NIL", SyntaxHighlighterColors.NUMBER.getDefaultAttributes()));

        keys.put(NUMERIC_LITERAL, createTextAttributesKey("CLOJURE.NUMERIC_LITERAL", SyntaxHighlighterColors.NUMBER.getDefaultAttributes()));
        keys.put(STRING_LITERAL, createTextAttributesKey("CLOJURE.STRING_LITERAL", SyntaxHighlighterColors.STRING.getDefaultAttributes()));
        keys.put(CHAR_LITERAL, createTextAttributesKey("CLOJURE.CHAR_LITERAL", SyntaxHighlighterColors.STRING.getDefaultAttributes()));
        keys.put(BAD_CHARACTER, createTextAttributesKey("CLOJURE.BAD_CHARACTER", HighlighterColors.BAD_CHARACTER.getDefaultAttributes()));
        keys.put(LINE_COMMENT, createTextAttributesKey("CLOJURE.LINE_COMMENT", SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()));

        // TODO - How can we color docstrings? 
        keys.put(DO, createTextAttributesKey("CLOJURE.LINE_COMMENT", SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()));

        keys.put(QUOTE_KEYWORD, createTextAttributesKey("CLOJURE.QUOTE_KEYWORD", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(FN, createTextAttributesKey("CLOJURE.FN", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(IF, createTextAttributesKey("CLOJURE.IF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DO, createTextAttributesKey("CLOJURE.DO", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(LET, createTextAttributesKey("CLOJURE.LET", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(WITH, createTextAttributesKey("CLOJURE.WITH", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEFMACRO, createTextAttributesKey("CLOJURE.MAC", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEFN, createTextAttributesKey("CLOJURE.DEF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEF, createTextAttributesKey("CLOJURE.DEF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));

        keys.put(SYMBOL, createTextAttributesKey("CLOJURE.SYMBOL", HighlighterColors.TEXT.getDefaultAttributes()));
    }

    @NotNull
    public Lexer getHighlightingLexer() {
        return new ClojureLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(keys.get(tokenType));
    }
}

