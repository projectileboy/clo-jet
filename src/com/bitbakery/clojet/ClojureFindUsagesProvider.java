package com.bitbakery.clojet;

import com.bitbakery.clojet.lexer.ClojureLexer;
import static com.bitbakery.clojet.lexer.ClojureTokenTypes.COMMENTS;
import static com.bitbakery.clojet.lexer.ClojureTokenTypes.LITERALS;
import static com.bitbakery.clojet.psi.ClojureElementTypes.VARIABLE_DEFINITION_FILTER;
import com.bitbakery.clojet.psi.*;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

/**
 * Enables the "find usages" feature for Clojure files.
 */
public class ClojureFindUsagesProvider implements FindUsagesProvider {
    private WordsScanner wordsScanner;

    // TODO - We can't seem to find usages in different files, and yet navigation from reference to definition works. WTF??


    @Nullable
    public WordsScanner getWordsScanner() {
        if (wordsScanner == null) {
            wordsScanner = new DefaultWordsScanner(new ClojureLexer(), VARIABLE_DEFINITION_FILTER, COMMENTS, LITERALS);
        }
        return wordsScanner;
    }

    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof Def
                || psiElement instanceof Defn
                || psiElement instanceof Defmacro
                || psiElement instanceof VariableDefinition
                || psiElement instanceof Defmethod;
    }

    @Nullable
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;  // TODO - Create a JavaHelp file for the Arc plugin
    }

    @NotNull
    public String getType(@NotNull PsiElement element) {
        if (element instanceof Def) {
            return "def";
        } else if (element instanceof Defn) {
            return "defn";
        } else if (element instanceof Defmacro) {
            return "defmacro";
        } else if (element instanceof Defmethod) {
            return "defmethod";
        }
        return "gleef";
    }

    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof PsiNamedElement) {
            return ((PsiNamedElement) element).getName();
        }
        return null;
    }

    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof PsiNamedElement) {
            return element.getText();
        }
        return null;
    }
}
