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

import org.jetbrains.annotations.NonNls;

import javax.swing.*;

import com.intellij.openapi.util.IconLoader;

/**
 * Specifies Icon objects for the various Clojure icons used throughout the app
 */
public interface CloJetIcons {
    @NonNls
    final String DATA_PATH = "/icons/";

    final Icon CLOJURE_LARGE_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-128.png");
    final Icon CLOJURE_MODULE_TYPE_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-24.png");
    final Icon CLOJURE_CONFIG_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-32.png");
    final Icon CLOJURE_REPL_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-16.png");
    final Icon CLOJURE_FILE_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-16.png");

    final Icon CLOJURE_DEF_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-16.png");
    final Icon CLOJURE_DEFN_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-defn.png");
    final Icon CLOJURE_DEFMACRO_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-defmacro.png");
}
