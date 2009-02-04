package com.bitbakery.clojet;

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
        return null;  // TODO
    }

    @NotNull
    public String getDefaultExtension() {
        return "clj";
    }

    public Icon getIcon() {
        return null;  // TODO
    }
}
