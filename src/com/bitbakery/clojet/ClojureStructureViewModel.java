package com.bitbakery.clojet;

import com.bitbakery.clojet.psi.Defmacro;
import com.bitbakery.clojet.psi.Defn;
import com.bitbakery.clojet.psi.Fn;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel;
import com.intellij.ide.util.treeView.smartTree.Filter;
import com.intellij.ide.util.treeView.smartTree.Grouper;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.ide.util.treeView.smartTree.ActionPresentation;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: kurtc
 * Date: Feb 11, 2009
 * Time: 1:38:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClojureStructureViewModel extends TextEditorBasedStructureViewModel {
    private PsiFile myFile;

    public ClojureStructureViewModel(final PsiFile file) {
        super(file);
        myFile = file;
    }

    @NotNull
    public StructureViewTreeElement getRoot() {
        return new ClojureStructureViewElement(myFile);
    }

    @NotNull
    public Grouper[] getGroupers() {
        // TODO - Enable grouping based on defs, macs, fns, []s, etc...
        return Grouper.EMPTY_ARRAY;
    }

    @NotNull
    public Sorter[] getSorters() {
        // TODO - Enable sorting based on defs, macs, fns, []s, etc...
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }

    @NotNull
    public Filter[] getFilters() {
        // TODO - Enable filtering based on defs, macs, fns, []s, etc...
        return Filter.EMPTY_ARRAY;
    }

    protected PsiFile getPsiFile() {
        return myFile;
    }

    @NotNull
    protected Class[] getSuitableClasses() {
        return new Class[]{Defn.class, Defmacro.class, Fn.class}; // TODO - Any others...?
    }
}
