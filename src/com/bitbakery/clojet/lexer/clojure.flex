/***** JFlex specification for Clojure *****/

package com.bitbakery.clojet.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.bitbakery.clojet.lexer.ClojureTokenTypes.*;

%%

%class _ClojureLexer
%implements FlexLexer
%unicode
%public

%function advance
%type IElementType



/***** Custom user code *****/

%{
    StringBuffer string = new StringBuffer();
%}




/***** Macros - Regular expressions *****/

LineTerminator=\r|\n|\r\n
InputCharacter=[^\r\n]
WhiteSpace={LineTerminator}|[ \t\f]

LineComment=([ \t\f]*";"{InputCharacter}*{LineTerminator}+)
CommentContent = ( [^*] | \*+ [^/*] )*
BlockComment = "#\|"{CommentContent}"\|#"


Digit=[0-9]
IntegerLiteral=(0|([1-9]({Digit})*))

ExponentPart=[Ee]["+""-"]?({Digit})*
FloatingPointLiteral1=({Digit})+"."({Digit})*({ExponentPart})?
FloatingPointLiteral2="."({Digit})+({ExponentPart})?
FloatingPointLiteral3=({Digit})+({ExponentPart})
FloatingPointLiteral4=({Digit})+
FloatLiteral=({FloatingPointLiteral1})|({FloatingPointLiteral2})|({FloatingPointLiteral3})|({FloatingPointLiteral4})

NumericLiteral=["+""-"]?({IntegerLiteral})|({FloatLiteral})

/**** TODO - Is there some nice way to generalize this to other languages with non-Roman chars?? ******/
/**** TODO - Can we intercept special characters before we intercept symbols? ******/
Char=[A-Za-z0-9!@$%<>_/?\+\*\-=\?\;\|]
CharLiteral=\\(newline|space|tab|return|\"|{Char})

Keyword= :{Char}*
Symbol={Char}*
/**** TODO - If the leading character is a ., then that's actually a method call, but it's getting read as a 'classname' token ****/
Classname=(({Symbol})("."{Symbol})+)

EscapeSequence=\\[^\r\n]
StringLiteral=\"([^\\\"]|{EscapeSequence})*(\"|\\)?

%%

{WhiteSpace}     { return WHITESPACE; }
{CharLiteral}    { return CHAR_LITERAL; }
{LineComment}    { return LINE_COMMENT; }
{BlockComment}   { return BLOCK_COMMENT; }
{NumericLiteral} { return NUMERIC_LITERAL; }
{StringLiteral}  { return STRING_LITERAL; }


"("             { return LEFT_PAREN; }
")"             { return RIGHT_PAREN; }

"{"             { return LEFT_CURLY; }
"}"             { return RIGHT_CURLY; }

"["             { return LEFT_SQUARE; }
"]"             { return RIGHT_SQUARE; }

/** TODO - We're not handling the anonymous parameter correctly. We probably need to do the whole JFlex-state-while-in-square-brackets thingy...  ******/
/** " %[0-9]* "             { return ANONYMOUS_PARAM; }  *****/


[Qq][Uu][Oo][Tt][Ee]        { return QUOTE_KEYWORD; }
[Mm][ee][Tt][Aa]            { return META_KEYWORD; }
[Ff][Nn]                    { return FN; }
[Ii][Ff]                    { return IF; }
[Cc][Oo][Nn][Dd]            { return COND; }
[Ll][Oo][Oo][Pp]            { return LOOP; }
[Rr][Ee][Cc][Uu][Rr]        { return RECUR; }
[Dd][Oo]                    { return DO; }
[Ll][Ee][Tt]                { return LET; }
[Vv][Aa][Rr]                { return VAR; }
[Tt][Hh][Rr][Oo][Ww]        { return THROW; }
[Tt][Rr][Yy]                { return TRY; }
[Nn][Ee][Ww]                { return NEW; }
[Ss][Ee][Tt]\!              { return SET; }

[Mm][Oo][Nn][Ii][Tt][Oo][Rr]-[Ee][Nn][Tt][Ee][Rr]  { return MONITOR_ENTER; }
[Mm][Oo][Nn][Ii][Tt][Oo][Rr]-[Ee][Xx][Ii][Tt]      { return MONITOR_EXIT; }


[Tt][Rr][Uu][Ee]        { return TRUE; }
[Ff][Aa][Ll][Ss][Ee]    { return FALSE; }
[Nn][Ii][Ll]            { return NIL; }


"~@"    { return UNQUOTE_SPLICE; }
"~"     { return UNQUOTE; }
"@"     { return DEREF; }
"#^"    { return METADATA; }
"#'"    { return VAR_QUOTE; }
"^"     { return META; }
"#"     { return REGEX; }
"`"     { return BACKQUOTE; }
"'"     { return QUOTE; }
","     { return COMMA; }
"&"     { return REST; }
"."     { return DOT; }

[Dd][Ee][Ff][Nn]                     { return DEFN; }
[Dd][Ee][Ff][Mm][Uu][Ll][Tt][Ii]     { return DEFMULTI; }
[Dd][Ee][Ff][Mm][Ee][Tt][Hh][Oo][Dd] { return DEFMETHOD; }
[Dd][Ee][Ff][Ss][Tt][Rr][Uu][Cc][Tt] { return DEFSTRUCT; }
[Dd][Ee][Ff][Mm][Aa][Cc][Rr][Oo]     { return DEFMACRO; }
[Dd][Ee][Ff]                         { return DEF; }


{Keyword}       { return KEYWORD; }
{Classname}     { return CLASSNAME; }
{Symbol}        { return SYMBOL; }

.               { return BAD_CHARACTER; }