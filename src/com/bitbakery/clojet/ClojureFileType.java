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

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Identifies Clojure source code files based on the file extensions
 */
public class ClojureFileType extends LanguageFileType {

    public static final ClojureLanguage CLOJURE = new ClojureLanguage();

    public ClojureFileType() {
        super(CLOJURE);
    }

    @NotNull
    public String getName() {
        return "Clojure";
    }

    @NotNull
    public String getDescription() {
        return CloJetStrings.message("file.description");
    }

    @NotNull
    public String getDefaultExtension() {
        return "clj";
    }

    public Icon getIcon() {
        return CloJetIcons.CLOJURE_FILE_ICON;
    }
}
