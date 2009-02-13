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
    IElementType TILDE = new ClojureElementType("~");

    IElementType COMPOSER = new ClojureElementType(":");
    IElementType DOT = new ClojureElementType(".");
    IElementType EQ = new ClojureElementType("=");
    IElementType BACKQUOTE = new ClojureElementType("`");

    IElementType QUOTE = new ClojureElementType("'");
    IElementType COMMA = new ClojureElementType(",");
    IElementType COMMA_AT = new ClojureElementType(",@");
    TokenSet SPECIAL_CHARACTERS = TokenSet.create(TILDE, COMPOSER, DOT, EQ, BACKQUOTE, QUOTE, COMMA, COMMA_AT);

    // This guy is a little special, at least within single-var anonymous fn definitions
    IElementType ANONYMOUS_PARAM = new ClojureElementType("%");


    // Keywords and special forms
    IElementType DEF = new ClojureElementType("def");
    IElementType DEFN = new ClojureElementType("defn");
    IElementType DEFMACRO = new ClojureElementType("defmacro");


    IElementType QUOTE_KEYWORD = new ClojureElementType("quote");
    IElementType FN = new ClojureElementType("fn");
    IElementType IF = new ClojureElementType("if");


    // Library stuff from arc.arc
    IElementType DO = new ClojureElementType("do");
    IElementType LET = new ClojureElementType("let");
    IElementType WITH = new ClojureElementType("with");

    TokenSet KEYWORDS = TokenSet.create(DEF, DEFN, DEFMACRO, FN, IF, DO, LET, WITH);

    // Comments
    IElementType BLOCK_COMMENT = new ClojureElementType("block comment");
    IElementType LINE_COMMENT = new ClojureElementType("line comment");
    TokenSet COMMENTS = TokenSet.create(BLOCK_COMMENT, LINE_COMMENT);


    // Literals
    IElementType STRING_LITERAL = new ClojureElementType("string literal");
    IElementType NUMERIC_LITERAL = new ClojureElementType("numeric literal");
    IElementType CHAR_LITERAL = new ClojureElementType("character literal");

    IElementType TRUE = new ClojureElementType("t");
    IElementType NIL = new ClojureElementType("nil");
    TokenSet BOOLEAN_LITERAL = TokenSet.create(TRUE, NIL);

    TokenSet LITERALS = TokenSet.create(STRING_LITERAL, NUMERIC_LITERAL, CHAR_LITERAL, TRUE, NIL);
    TokenSet READABLE_TEXT = TokenSet.create(STRING_LITERAL, BLOCK_COMMENT, LINE_COMMENT);


    IElementType SYMBOL = new ClojureElementType("symbol");
    TokenSet SYMBOL_FILTER = TokenSet.create(SYMBOL);


    // Control characters
    IElementType EOL = new ClojureElementType("end of line");
    IElementType EOF = new ClojureElementType("end of file");
    IElementType WHITESPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    TokenSet WHITESPACE_SET = TokenSet.create(EOL, EOF, WHITESPACE);
    // TODO - Not tokens, but we should know what library functions are available depending on the CL implementation we're using??
    // TODO - We should understand the syntax of common macros, like do, print format synatx, etc.
    // TODO - Should we distinguish between macros and functions that are destructive?
}
