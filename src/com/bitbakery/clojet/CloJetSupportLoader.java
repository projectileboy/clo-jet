package com.bitbakery.clojet;

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
