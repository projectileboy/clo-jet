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

import com.bitbakery.clojet.ClojureFileType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IElementType;

public interface ClojureElementTypes {
    IFileElementType FILE = new IFileElementType(ClojureFileType.CLOJURE);

    IElementType DEFINITION = new ClojureElementType("def");
    IElementType FUNCTION_DEFINITION = new ClojureElementType("defn");
    IElementType METHOD_DEFINITION = new ClojureElementType("defmethod");
    IElementType MULTIMETHOD_DEFINITION = new ClojureElementType("defmulti");
    IElementType STRUCTURE_DEFINITION = new ClojureElementType("defstruct");
    IElementType MACRO_DEFINITION = new ClojureElementType("defmacro");
    IElementType ANONYMOUS_FUNCTION_DEFINITION = new ClojureElementType("fn");

    IElementType JAVA_EXPRESSION = new ClojureElementType("(. )");
    IElementType EXPRESSION = new ClojureElementType("()");
    IElementType CLOJURE_VECTOR = new ClojureElementType("[]");
    IElementType CLOJURE_MAP = new ClojureElementType("{}");
    IElementType CLOJURE_METADATA = new ClojureElementType("metadata");
/*
    IElementType SINGLE_ARG_ANONYMOUS_FUNCTION_DEFINITION = new ArcElementType("single arg fn");
*/

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
