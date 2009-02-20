package com.bitbakery.clojet.templates;

import com.bitbakery.clojet.ClojureFileType;
import com.bitbakery.clojet.CloJetSupportLoader;
import com.intellij.codeInsight.template.FileTypeBasedContextType;

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
 * ????
 */
public class ClojureTemplateContextType extends FileTypeBasedContextType {

    public ClojureTemplateContextType() {
        super("CLOJURE", "Clojure", CloJetSupportLoader.CLOJURE);
    }
}

