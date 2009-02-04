package com.bitbakery.clojet.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.psi.FileViewProvider;
import com.intellij.openapi.fileTypes.FileType;
import com.bitbakery.clojet.ClojureFileType;
import com.bitbakery.clojet.CloJetSupportLoader;
import org.jetbrains.annotations.NotNull;

/**
 * PSI element for an Clojure file
 */
public class ClojureFile extends PsiFileBase {
    public ClojureFile(FileViewProvider viewProvider) {
        super(viewProvider, ClojureFileType.CLOJURE);
    }

    @NotNull
    public FileType getFileType() {
        return CloJetSupportLoader.CLOJURE;
    }
}

