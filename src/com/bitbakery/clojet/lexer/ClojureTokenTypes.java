package com.bitbakery.clojet.lexer;

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

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.TokenType;
import com.bitbakery.clojet.ClojureLanguage;
import com.bitbakery.clojet.ClojureFileType;
import com.bitbakery.clojet.psi.ClojureElementType;

/**
 * Identifies all of the token types (at least, the ones we'll care about) in Clojure.
 * Used by the lexer to break an Clojure source file down into tokens.
 */
public interface ClojureTokenTypes {
    static ClojureLanguage language = ClojureFileType.CLOJURE;


    // Special characters
    IElementType LEFT_PAREN = new ClojureElementType("(");
    IElementType RIGHT_PAREN = new ClojureElementType(")");

    IElementType LEFT_CURLY = new ClojureElementType("{");
    IElementType RIGHT_CURLY = new ClojureElementType("}");

    IElementType LEFT_SQUARE = new ClojureElementType("[");
    IElementType RIGHT_SQUARE = new ClojureElementType("]");

    IElementType UNQUOTE = new ClojureElementType("~");
    IElementType DEREF = new ClojureElementType("@");
    IElementType DOT = new ClojureElementType(".");
    IElementType REST = new ClojureElementType("&");
    IElementType BACKQUOTE = new ClojureElementType("`");
    IElementType QUOTE = new ClojureElementType("'");
    IElementType COMMA = new ClojureElementType(",");
    IElementType METADATA = new ClojureElementType("#^");
    IElementType META = new ClojureElementType("^");
    IElementType REGEX = new ClojureElementType("#");
    IElementType VAR_QUOTE = new ClojureElementType("#'");
    IElementType UNQUOTE_SPLICE = new ClojureElementType(",@");

    // This guy is a little special, at least within single-var anonymous fn definitions
    IElementType ANONYMOUS_PARAM = new ClojureElementType("%");

    TokenSet SPECIAL_CHARACTERS = TokenSet.create(UNQUOTE, DEREF, METADATA, META,
            REGEX, REST, VAR_QUOTE, DOT, BACKQUOTE, QUOTE, COMMA, UNQUOTE_SPLICE);


    // Keywords and special forms (as well as some keywords that behave like
    IElementType DEF = new ClojureElementType("def");

    IElementType QUOTE_KEYWORD = new ClojureElementType("quote");
    IElementType META_KEYWORD = new ClojureElementType("meta");
    IElementType IF = new ClojureElementType("if");
    IElementType COND = new ClojureElementType("cond");
    IElementType LOOP = new ClojureElementType("loop");
    IElementType RECUR = new ClojureElementType("recur");
    IElementType VAR = new ClojureElementType("var");
    IElementType THROW = new ClojureElementType("throw");
    IElementType TRY = new ClojureElementType("try");
    IElementType NEW = new ClojureElementType("new");
    IElementType SET = new ClojureElementType("set!");
    IElementType MONITOR_ENTER = new ClojureElementType("monitor-enter");
    IElementType MONITOR_EXIT = new ClojureElementType("monitor-exit");

    IElementType DO = new ClojureElementType("do");
    IElementType LET = new ClojureElementType("let");

    TokenSet SPECIAL_FORMS = TokenSet.create(DEF, COND, IF, DO, LET, LOOP, RECUR, VAR, THROW, TRY, NEW, SET);

    IElementType DEFN = new ClojureElementType("defn");
    IElementType DEFMACRO = new ClojureElementType("defmacro");
    IElementType DEFMETHOD = new ClojureElementType("defmethod");
    IElementType DEFMULTI = new ClojureElementType("defmulti");
    IElementType DEFSTRUCT = new ClojureElementType("defstruct");
    IElementType FN = new ClojureElementType("fn");

    TokenSet PSEUDO_SPECIAL_FORMS = TokenSet.create(DEF, DEFN, DEFMACRO, DEFMULTI, DEFMETHOD, DEFSTRUCT, FN);

    // Comments
    IElementType BLOCK_COMMENT = new ClojureElementType("block comment");
    IElementType LINE_COMMENT = new ClojureElementType("line comment");
    TokenSet COMMENTS = TokenSet.create(BLOCK_COMMENT, LINE_COMMENT);


    // Literals
    IElementType STRING_LITERAL = new ClojureElementType("string literal");
    IElementType NUMERIC_LITERAL = new ClojureElementType("numeric literal");
    IElementType CHAR_LITERAL = new ClojureElementType("character literal");

    IElementType TRUE = new ClojureElementType("true");
    IElementType FALSE = new ClojureElementType("false");
    IElementType NIL = new ClojureElementType("nil");
    TokenSet BOOLEAN_LITERAL = TokenSet.create(TRUE, FALSE, NIL);

    TokenSet LITERALS = TokenSet.create(STRING_LITERAL, NUMERIC_LITERAL, CHAR_LITERAL, TRUE, NIL);
    TokenSet READABLE_TEXT = TokenSet.create(STRING_LITERAL, BLOCK_COMMENT, LINE_COMMENT);


    IElementType KEYWORD = new ClojureElementType("keyword");
    IElementType SYMBOL = new ClojureElementType("symbol");
    IElementType CLASSNAME = new ClojureElementType("classname");
    TokenSet SYMBOL_FILTER = TokenSet.create(SYMBOL);


    // Control characters
    IElementType EOL = new ClojureElementType("end of line");
    IElementType EOF = new ClojureElementType("end of file");
    IElementType WHITESPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    TokenSet WHITESPACE_SET = TokenSet.create(EOL, EOF, WHITESPACE);
    // TODO - We should understand the syntax of common macros, like do, print format synatx, etc.
    // TODO - Should we distinguish between macros and functions that are destructive?
}
