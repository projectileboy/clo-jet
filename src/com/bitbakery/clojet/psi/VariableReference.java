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
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.bitbakery.clojet.ClojureFileType;

public class VariableReference extends ClojureElement {
    private MyPsiReference reference;

    public VariableReference(@NotNull final ASTNode node) {
        super(node);
        reference = new MyPsiReference(this);
    }

    public PsiReference getReference() {
        return reference;
    }


    private class MyPsiReference extends PsiReferenceBase<VariableReference> {
        public MyPsiReference(VariableReference element) {
            super(element);
        }

        public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
            return super.handleElementRename(newElementName);    // TODO - Do something!
        }

        public PsiElement resolve() {
            return walkTree(myElement.getParent());
        }

        private PsiElement walkTree(PsiElement e) {
            if (e == null) {
                // TODO - This works!! Now I just need to incorporate standard Arc source files!

                VirtualFile[] roots = ProjectRootManager.getInstance(myElement.getProject()).getContentRoots();
                return search(roots, myElement.getProject());
            } else if (e instanceof PsiFile) {
                for (PsiElement def : e.getChildren()) {
                    if (nameMatches(def)) {
                        return def;
                    }
                }
/*
            } else if (e instanceof Def || e instanceof Mac || e instanceof Fn) {
                if (nameMatches(e)) {
                    return e;
                }

                ParameterList params = PsiTreeUtil.getChildOfType(e, ParameterList.class);
                if (params != null) {
                    for (PsiElement param : params.getChildren()) {
                        if (nameMatches(param)) {
                            return param;
                        }
                    }
                }
*/
                
/*
            } else if (e instanceof Let) {
                VariableDefinition var = PsiTreeUtil.getChildOfType(e, VariableDefinition.class);
                if (var != null) {
                    if (nameMatches(var)) {
                        return var;
                    }
                }
            } else if (e instanceof With) {

                // TODO - Check the variables defined by the Let/With
                ParameterList params = PsiTreeUtil.getChildOfType(e, ParameterList.class);
                if (params != null) {
                    for (PsiElement param : params.getChildren()) {
                        if (nameMatches(param)) {
                            return param;
                        }
                    }
                }
*/
            }

            return walkTree(e.getParent());
        }

        private PsiElement search(VirtualFile[] children, Project p) {
            for (VirtualFile file : children) {
                PsiElement e = file.isDirectory() ? search(file.getChildren(), p) : search(file, p);
                if (e != null) return e;
            }
            return null;
        }

        private PsiElement search(VirtualFile file, Project project) {
            if (!"clj".equalsIgnoreCase(file.getExtension())) {
                return null;
            }
            PsiDocumentManager.getInstance(project).commitAllDocuments();
            return search(PsiManager.getInstance(project).findFile(file).getChildren());
        }

        private PsiElement search(PsiElement[] elements) {
            for (PsiElement e : elements) {
                if (nameMatches(e)) return e;
                PsiElement el = search(e.getChildren());
                if (el != null) return el;
            }
            return null;
        }

        private boolean nameMatches(PsiElement e) {
            return e instanceof PsiNamedElement
                    && ((PsiNamedElement) e).getName() != null
                    && ((PsiNamedElement) e).getName().equals(myElement.getText());
        }

        public TextRange getRangeInElement() {
            return new TextRange(0, myElement.getTextLength());
        }

        public Object[] getVariants() {
            // TODO - Implement me to get code completion working - need to search up the tree and gather every variable def (including def/mac), plus all top-level declarations, including other files
            //return new Object[0];
            return new String[]{"var-one", "var-two", "var-three"};
        }
    }
}