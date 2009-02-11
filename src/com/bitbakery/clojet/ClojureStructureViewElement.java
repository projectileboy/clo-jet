package com.bitbakery.clojet;

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