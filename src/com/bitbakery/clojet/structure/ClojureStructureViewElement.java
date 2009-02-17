package com.bitbakery.clojet.structure;

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

import com.bitbakery.clojet.psi.ClojureElement;
import com.bitbakery.clojet.psi.Defmacro;
import com.bitbakery.clojet.psi.Defn;
import com.bitbakery.clojet.psi.Fn;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiNamedElement;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kurtc
 * Date: Feb 11, 2009
 * Time: 1:40:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClojureStructureViewElement implements StructureViewTreeElement {
    private PsiElement myElement;

    public ClojureStructureViewElement(PsiElement element) {
        myElement = element;
    }

    public PsiElement getValue() {
        return myElement;
    }

    public void navigate(boolean requestFocus) {
        ((NavigationItem) myElement).navigate(requestFocus);
    }

    public boolean canNavigate() {
        return ((NavigationItem) myElement).canNavigate();
    }

    public boolean canNavigateToSource() {
        return ((NavigationItem) myElement).canNavigateToSource();
    }

    public StructureViewTreeElement[] getChildren() {
        final List<ClojureElement> childrenElements = new ArrayList<ClojureElement>();
        myElement.acceptChildren(new PsiElementVisitor() {
            public void visitElement(PsiElement element) {
                if (isBrowsableElement(element)) {
                    childrenElements.add((ClojureElement) element);
                } else {
                    element.acceptChildren(this);
                }
            }
        });

        StructureViewTreeElement[] children = new StructureViewTreeElement[childrenElements.size()];
        for (int i = 0; i < children.length; i++) {
            children[i] = new ClojureStructureViewElement(childrenElements.get(i));
        }

        return children;
    }

    private boolean isBrowsableElement(PsiElement element) {
        return element instanceof Defn
                || element instanceof Defmacro
                || element instanceof Fn;
    }

    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            public String getPresentableText() {
                return ((PsiNamedElement) myElement).getName();
            }

            public TextAttributesKey getTextAttributesKey() {
                return null;
            }

            public String getLocationString() {
                return null;
            }

            public Icon getIcon(boolean open) {
                return myElement.getIcon(Iconable.ICON_FLAG_OPEN);
            }
        };
    }
}