package com.bitbakery.clojet;

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
    final Icon CLOJURE_DEFN_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-16.png");
    final Icon CLOJURE_DEFMACRO_ICON = IconLoader.findIcon(DATA_PATH + "clojure-icon-16.png");
}
