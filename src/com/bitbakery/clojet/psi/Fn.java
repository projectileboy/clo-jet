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

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.bitbakery.clojet.CloJetIcons;

import javax.swing.*;

/**
 * PSI elemenet representing an anonymous function definition ("fn") in Clojure.
 */
public class Fn extends VariableAssignment {
    public Fn(@NotNull final ASTNode node) {
        super(node);
    }

    public Icon getIcon(int flags) {
        return CloJetIcons.CLOJURE_DEFN_ICON;
    }
}