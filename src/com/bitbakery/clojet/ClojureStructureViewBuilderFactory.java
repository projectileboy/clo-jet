package com.bitbakery.clojet;

import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class ClojureStructureViewBuilderFactory implements PsiStructureViewFactory {
    public StructureViewBuilder getStructureViewBuilder(final PsiFile psiFile) {
        return new TreeBasedStructureViewBuilder() {
            @NotNull
            public StructureViewModel createStructureViewModel() {
                return new ClojureStructureViewModel(psiFile);
            }

            public boolean isRootNodeShown() {
                return false;
            }
        };
    }
}
