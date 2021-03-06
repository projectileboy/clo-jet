package com.bitbakery.clojet;

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

import com.bitbakery.clojet.psi.Defmacro;
import com.bitbakery.clojet.psi.Defn;
import com.bitbakery.clojet.psi.VariableDefinition;
import com.bitbakery.clojet.psi.VariableReference;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Provides documentation for elements within an editor, where possible.
 */
public class ClojureDocumentationProvider implements DocumentationProvider {
    // TODO - I needs to be configurablzzz
    private static final String DOC_ROOT_URL = "http://clojure.org/api#";

    @Nullable
    public String getQuickNavigateInfo(PsiElement element) {
        // TODO - For some elements, we need to pull the docstring from the metadata
        if (element instanceof Defn) {
            return ((Defn) element).getDocstring();
        } else if (element instanceof Defmacro) {
            return ((Defmacro) element).getDocstring();
        }
        return null;
    }

    @Nullable
    public String getUrlFor(PsiElement element, PsiElement originalElement) {
        // TODO - We need to be able to specify which URL we should go to, based on the file we're in
        if (element instanceof VariableReference) {
            return DOC_ROOT_URL + ((VariableReference) element).getName();
        }
        return null;
    }

    @Nullable
    public String generateDoc(PsiElement element, PsiElement originalElement) {
        // TODO - Make me real, somehow...? Pull from arcfn, maybe...? Plus parameter info...?
        StringBuffer buf = new StringBuffer();
        BufferedReader in = null;
        try {
            URL url = new URL(getUrlFor(element, originalElement));
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
            return buf.toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        return null; // TODO - What's this guy for??
    }

    @Nullable
    public PsiElement getDocumentationElementForLink(PsiManager psiManager, String link, PsiElement context) {
        return null; // TODO - What's this guy for??
    }
}
