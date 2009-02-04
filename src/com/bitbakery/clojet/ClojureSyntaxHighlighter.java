package com.bitbakery.clojet;

import com.bitbakery.clojet.lexer.ClojureLexer;
import com.bitbakery.clojet.lexer.ClojureTokenTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
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

    public static final TextAttributesKey TILDE = TextAttributesKey.createTextAttributesKey("CLOJURE.TILDE", HighlighterColors.TEXT.getDefaultAttributes());
    public static final TextAttributesKey EQ = TextAttributesKey.createTextAttributesKey("CLOJURE.EQ", HighlighterColors.TEXT.getDefaultAttributes());
    public static final TextAttributesKey BACKQUOTE = TextAttributesKey.createTextAttributesKey("CLOJURE.BACKQUOTE", HighlighterColors.TEXT.getDefaultAttributes());
    public static final TextAttributesKey QUOTE = TextAttributesKey.createTextAttributesKey("CLOJURE.QUOTE", HighlighterColors.TEXT.getDefaultAttributes());
    public static final TextAttributesKey COMMA = TextAttributesKey.createTextAttributesKey("CLOJURE.COMMA", HighlighterColors.TEXT.getDefaultAttributes());
    public static final TextAttributesKey COMMA_AT = TextAttributesKey.createTextAttributesKey("CLOJURE.COMMA_AT", HighlighterColors.TEXT.getDefaultAttributes());
    public static final TextAttributesKey DOT = TextAttributesKey.createTextAttributesKey("CLOJURE.DOT", HighlighterColors.TEXT.getDefaultAttributes());
    public static final TextAttributesKey COMPOSER = TextAttributesKey.createTextAttributesKey("CLOJURE.COMPOSER", HighlighterColors.TEXT.getDefaultAttributes());

    public static final TextAttributesKey TRUE = TextAttributesKey.createTextAttributesKey("CLOJURE.TRUE", SyntaxHighlighterColors.NUMBER.getDefaultAttributes());
    public static final TextAttributesKey NIL = TextAttributesKey.createTextAttributesKey("CLOJURE.NIL", SyntaxHighlighterColors.NUMBER.getDefaultAttributes());

    public static final TextAttributesKey NUMERIC_LITERAL = TextAttributesKey.createTextAttributesKey("CLOJURE.NUMERIC_LITERAL", SyntaxHighlighterColors.NUMBER.getDefaultAttributes());
    public static final TextAttributesKey STRING_LITERAL = TextAttributesKey.createTextAttributesKey("CLOJURE.STRING_LITERAL", SyntaxHighlighterColors.STRING.getDefaultAttributes());
    public static final TextAttributesKey CHAR_LITERAL = TextAttributesKey.createTextAttributesKey("CLOJURE.CHAR_LITERAL", SyntaxHighlighterColors.STRING.getDefaultAttributes());
    public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("CLOJURE.BAD_CHARACTER", HighlighterColors.BAD_CHARACTER.getDefaultAttributes());
    public static final TextAttributesKey LINE_COMMENT = TextAttributesKey.createTextAttributesKey("CLOJURE.LINE_COMMENT", SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes());

    public static final TextAttributesKey QUOTE_KEYWORD = TextAttributesKey.createTextAttributesKey("CLOJURE.QUOTE_KEYWORD", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey FN = TextAttributesKey.createTextAttributesKey("CLOJURE.FN", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey IF = TextAttributesKey.createTextAttributesKey("CLOJURE.IF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey DO = TextAttributesKey.createTextAttributesKey("CLOJURE.DO", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey LET = TextAttributesKey.createTextAttributesKey("CLOJURE.LET", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey WITH = TextAttributesKey.createTextAttributesKey("CLOJURE.WITH", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey DEFMACRO = TextAttributesKey.createTextAttributesKey("CLOJURE.MAC", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey DEF = TextAttributesKey.createTextAttributesKey("CLOJURE.DEF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    public static final TextAttributesKey DEFN = TextAttributesKey.createTextAttributesKey("CLOJURE.DEF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());

    public static final TextAttributesKey SYMBOL = TextAttributesKey.createTextAttributesKey("CLOJURE.SYMBOL", HighlighterColors.TEXT.getDefaultAttributes());

    static {
        keys = new HashMap<IElementType, TextAttributesKey>();

        keys.put(ClojureTokenTypes.TILDE, TILDE);
        keys.put(ClojureTokenTypes.EQ, EQ);
        keys.put(ClojureTokenTypes.BACKQUOTE, BACKQUOTE);
        keys.put(ClojureTokenTypes.QUOTE, QUOTE);
        keys.put(ClojureTokenTypes.COMMA, COMMA);
        keys.put(ClojureTokenTypes.COMMA_AT, COMMA_AT);
        keys.put(ClojureTokenTypes.DOT, DOT);
        keys.put(ClojureTokenTypes.COMPOSER, COMPOSER);

        keys.put(ClojureTokenTypes.TRUE, TRUE);
        keys.put(ClojureTokenTypes.NIL, NIL);

        keys.put(ClojureTokenTypes.NUMERIC_LITERAL, NUMERIC_LITERAL);
        keys.put(ClojureTokenTypes.STRING_LITERAL, STRING_LITERAL);
        keys.put(ClojureTokenTypes.CHAR_LITERAL, CHAR_LITERAL);
        keys.put(ClojureTokenTypes.BAD_CHARACTER, BAD_CHARACTER);
        keys.put(ClojureTokenTypes.LINE_COMMENT, LINE_COMMENT);

        // TODO - How can we color docstrings? 
        keys.put(ClojureTokenTypes.DO, LINE_COMMENT);

        keys.put(ClojureTokenTypes.QUOTE_KEYWORD, QUOTE_KEYWORD);
        keys.put(ClojureTokenTypes.FN, FN);
        keys.put(ClojureTokenTypes.IF, IF);
        keys.put(ClojureTokenTypes.DO, DO);
        keys.put(ClojureTokenTypes.LET, LET);
        keys.put(ClojureTokenTypes.WITH, WITH);
        keys.put(ClojureTokenTypes.DEFMACRO, DEFMACRO);
        keys.put(ClojureTokenTypes.DEFN, DEFN);
        keys.put(ClojureTokenTypes.DEF, DEF);

        keys.put(ClojureTokenTypes.SYMBOL, SYMBOL);
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

