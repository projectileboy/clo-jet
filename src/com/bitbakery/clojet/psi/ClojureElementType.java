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

import com.intellij.psi.tree.IElementType;
import com.bitbakery.clojet.ClojureFileType;
import org.jetbrains.annotations.NonNls;


/**
 * Simple wrapper for IElementType which enables Clojure PSI elements to own theirown parsing
 */
public class ClojureElementType extends IElementType {
    public ClojureElementType(@NonNls String debugName) {
        super(debugName, ClojureFileType.CLOJURE);
    }

    @SuppressWarnings({"HardCodedStringLiteral"})
    public String toString() {
        return "Clojure:" + super.toString();
    }

/*
    */
/**
 * Concrete subclasses tell ClojureParser how they should be broken down into PSI elements.
 */
/*
    public PsiBuilder.Marker parse(PsiBuilder builder) {
        // By default, we do nothing...
        advanceLexer(builder);
        return null;
    }

    */
/**
 * Advances the lexer if we haven't fallen off the end of the token stream
 */
/*
    protected void advanceLexer(PsiBuilder builder) {
        if (builder.getTokenType() != null) {
            builder.advanceLexer();
        }
    }
*/
}


