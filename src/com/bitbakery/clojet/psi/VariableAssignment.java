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

import com.bitbakery.clojet.CloJetIcons;
import static com.bitbakery.clojet.psi.ClojureElementTypes.*;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: kurtc
 * Date: Feb 9, 2009
 * Time: 2:45:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class VariableAssignment extends Expression implements PsiNamedElement {
    protected String name;

    public VariableAssignment(ASTNode node) {
        super(node);
        ASTNode[] children = node.getChildren(TokenSet.create(VARIABLE_DEFINITION));
        name = isEmpty(children) ? "=" : children[0].getText();
    }

    public String getName() {
        return name;
    }

    public int[] getParameterCounts() {
        ASTNode[] impls = getNode().getChildren(TokenSet.create(IMPLEMENTATION));
        if (impls != null && impls.length > 0) {
            int[] paramCounts = new int[impls.length];

            for (int i = 0; i < impls.length; i++) {
                ASTNode[] paramList = impls[i].getChildren(TokenSet.create(PARAMETER_LIST));
                paramCounts[i] = paramList[0].getChildren(TokenSet.create(PARAMETER, REST_PARAMETER)).length;
            }
            return paramCounts;
        }
        return new int[0];
    }

    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        // TODO - Need to actually set the text in the symbol child element that defines the name
        this.name = name;
        return this;
    }

    public Icon getIcon(int flags) {
        return CloJetIcons.CLOJURE_DEFN_ICON; // TODO - Create an icon for variable assignment
    }

    // TODO - Figure out where this should really live... ClojureElement, maybe??
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            public String getPresentableText() {
                return getName();
            }

            @Nullable
            public String getLocationString() {
                return getNode().getPsi().getContainingFile().getName();
            }

            @Nullable
            public Icon getIcon(boolean open) {
                return VariableAssignment.this.getIcon(0);
            }

            @Nullable
            public TextAttributesKey getTextAttributesKey() {
                return null;  // TODO - Investigate different fonts/colors/etc.for def vs. mac...?
            }
        };
    }

    /**
     * Strips quote marks from a string - utility method for Def and Mac.
     */
    protected String stripQuotes(String s) {
        s = s.trim();
        if (s.length() > 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
