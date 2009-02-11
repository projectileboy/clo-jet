package com.bitbakery.clojet.psi;

import com.bitbakery.clojet.ClojureFileType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IElementType;

public interface ClojureElementTypes {
    IFileElementType FILE = new IFileElementType(ClojureFileType.CLOJURE);

    IElementType DEFINITION = new ClojureElementType("def");
    IElementType FUNCTION_DEFINITION = new ClojureElementType("defn");
    IElementType EXPRESSION = new ClojureElementType("()");
    IElementType VECTOR = new ClojureElementType("[]");
    IElementType MAP = new ClojureElementType("{}");
/*
    IElementType SINGLE_ARG_ANONYMOUS_FUNCTION_DEFINITION = new ArcElementType("single arg fn");
*/

    IElementType ANONYMOUS_FUNCTION_DEFINITION = new ClojureElementType("fn");
    IElementType MACRO_DEFINITION = new ClojureElementType("defmacro");
/*
    IElementType LET_BLOCK = new ArcElementType("let");
    IElementType WITH_BLOCK = new ArcElementType("with");
*/
    IElementType DOCSTRING = new ClojureElementType("docstring");
/*
    IElementType QUOTED_EXPRESSION = new ArcElementType("quoted expression");
    IElementType BACKQUOTED_EXPRESSION = new ArcElementType("backquoted expression");
    IElementType COMMA_EXPRESSION = new ArcElementType("comma expression");
    IElementType COMMA_AT_EXPRESSION = new ArcElementType("comma-at expression");

    IElementType VARIABLE_ASSIGNMENT = new ArcElementType("variable assignment");
*/
    IElementType VARIABLE_DEFINITION = new ClojureElementType("variable definition");
/*
    IElementType PARAMETER = new ArcElementType("parameter");
    IElementType REST_PARAMETER = new ArcElementType("rest parameter");
    IElementType OPTIONAL_PARAMETER = new ArcElementType("optional parameter");

    IElementType PARAMETER_LIST = new ArcElementType("parameter list");

    IElementType VARIABLE_REFERENCE = new ArcElementType("variable reference");

    TokenSet VARIABLE_DEFINITION_FILTER = TokenSet.create(VARIABLE_DEFINITION);
    TokenSet VARIABLE_ASSIGNMENT_FILTER = TokenSet.create(VARIABLE_ASSIGNMENT);
*/
}
