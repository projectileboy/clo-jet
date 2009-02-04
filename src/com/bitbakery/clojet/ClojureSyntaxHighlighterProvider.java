package com.bitbakery.clojet;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

public class ClojureSyntaxHighlighterProvider implements SyntaxHighlighterProvider {
    public SyntaxHighlighter create(FileType fileType, @Nullable Project project, @Nullable VirtualFile file) {
        return new ClojureSyntaxHighlighter();
    }
}
