package com.bitbakery.clojet;

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

import com.bitbakery.clojet.lexer.ClojureLexer;
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
    public static Map<IElementType, TextAttributesKey> keys;

    static {
        keys = new HashMap<IElementType, TextAttributesKey>();

        keys.put(UNQUOTE, createTextAttributesKey("CLOJURE.UNQUOTE", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(DEREF, createTextAttributesKey("CLOJURE.DEREF", HighlighterColors.TEXT.getDefaultAttributes()));

        keys.put(REST, createTextAttributesKey("CLOJURE.REST", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(BACKQUOTE, createTextAttributesKey("CLOJURE.BACKQUOTE", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(QUOTE, createTextAttributesKey("CLOJURE.QUOTE", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(COMMA, createTextAttributesKey("CLOJURE.COMMA", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(METADATA, createTextAttributesKey("CLOJURE.METADATA", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(META, createTextAttributesKey("CLOJURE.META", HighlighterColors.TEXT.getDefaultAttributes()));

        keys.put(VAR_QUOTE, createTextAttributesKey("CLOJURE.VAR.QUOTE", HighlighterColors.TEXT.getDefaultAttributes()));
        keys.put(UNQUOTE_SPLICE, createTextAttributesKey("CLOJURE.UNQUOTE_SPLICE", HighlighterColors.TEXT.getDefaultAttributes()));

        // TODO - Color other special characters...


        
        keys.put(TRUE, createTextAttributesKey("CLOJURE.TRUE", SyntaxHighlighterColors.NUMBER.getDefaultAttributes()));
        keys.put(FALSE, createTextAttributesKey("CLOJURE.FALSE", SyntaxHighlighterColors.NUMBER.getDefaultAttributes()));
        keys.put(NIL, createTextAttributesKey("CLOJURE.NIL", SyntaxHighlighterColors.NUMBER.getDefaultAttributes()));

        keys.put(NUMERIC_LITERAL, createTextAttributesKey("CLOJURE.NUMERIC_LITERAL", SyntaxHighlighterColors.NUMBER.getDefaultAttributes()));
        keys.put(STRING_LITERAL, createTextAttributesKey("CLOJURE.STRING_LITERAL", SyntaxHighlighterColors.STRING.getDefaultAttributes()));
        keys.put(CHAR_LITERAL, createTextAttributesKey("CLOJURE.CHAR_LITERAL", SyntaxHighlighterColors.STRING.getDefaultAttributes()));
        keys.put(BAD_CHARACTER, createTextAttributesKey("CLOJURE.BAD_CHARACTER", HighlighterColors.BAD_CHARACTER.getDefaultAttributes()));
        keys.put(LINE_COMMENT, createTextAttributesKey("CLOJURE.LINE_COMMENT", SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()));

        // TODO - How can we color docstrings? 
        keys.put(QUOTE_KEYWORD, createTextAttributesKey("CLOJURE.QUOTE_KEYWORD", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(META_KEYWORD, createTextAttributesKey("CLOJURE.META_KEYWORD", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(IF, createTextAttributesKey("CLOJURE.IF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(COND, createTextAttributesKey("CLOJURE.COND", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(LOOP, createTextAttributesKey("CLOJURE.DO", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(RECUR, createTextAttributesKey("CLOJURE.RECUR", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(VAR, createTextAttributesKey("CLOJURE.VAR", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(THROW, createTextAttributesKey("CLOJURE.THROW", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(TRY, createTextAttributesKey("CLOJURE.TRY", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(NEW, createTextAttributesKey("CLOJURE.NEW", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(SET, createTextAttributesKey("CLOJURE.SET", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(MONITOR_ENTER, createTextAttributesKey("CLOJURE.MONITOR.ENTER", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(MONITOR_EXIT, createTextAttributesKey("CLOJURE.MONITOR.EXIT", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));

        keys.put(FN, createTextAttributesKey("CLOJURE.FN", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DO, createTextAttributesKey("CLOJURE.DO", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(LET, createTextAttributesKey("CLOJURE.LET", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEFSTRUCT, createTextAttributesKey("CLOJURE.DEFSTRUCT", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEFMULTI, createTextAttributesKey("CLOJURE.DEFMULTI", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEFMETHOD, createTextAttributesKey("CLOJURE.METHOD", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEFMACRO, createTextAttributesKey("CLOJURE.DEFMACRO", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEFN, createTextAttributesKey("CLOJURE.DEFN", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));
        keys.put(DEF, createTextAttributesKey("CLOJURE.DEF", SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()));

        keys.put(KEYWORD, createTextAttributesKey("CLOJURE.KEYWORD", HighlighterColors.TEXT.getDefaultAttributes()));
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

