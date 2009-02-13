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

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class CloJetSupportLoader extends FileTypeFactory implements ApplicationComponent { //, InspectionToolProvider, FileTypeIndentOptionsProvider, IconProvider {

    public static final LanguageFileType CLOJURE = new ClojureFileType();


    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "Clojure support loader";
    }

/*
TODO - See Javascript plugin for good examples of inspection goodies
    public Class[] getInspectionClasses() {
        return new Class[]{
        };
    }
*/

    //public CodeStyleSettings.IndentOptions createIndentOptions() {
    //    return new CodeStyleSettings.IndentOptions();
    //}

    public FileType getFileType() {
        return CLOJURE;
    }

    public Icon getIcon(@NotNull final PsiElement element, final int flags) {
        return CloJetIcons.CLOJURE_FILE_ICON;
    }

    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        // TODO - Is this correct?? The Javadoc is non-existent!!
        consumer.consume(CLOJURE, "clj");
    }
}
