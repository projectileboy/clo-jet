package com.bitbakery.clojet.actions;

import com.intellij.ide.FileIconProvider;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.util.Icons;
import com.intellij.util.JavaeeIcons;
import com.intellij.util.VisibilityIcons;
import com.intellij.persistence.PersistenceIcons;
import com.intellij.ui.popup.PopupIcons;

/**
 * Removes the current REPL from the ReplToolWindow.
 */
public class RemoveReplAction extends ClojureAction {
    public RemoveReplAction() {
        getTemplatePresentation().setIcon(IconLoader.getIcon("/actions/cancel.png"));
    }

    public void actionPerformed(AnActionEvent e) {
        getReplToolWindow(e).removeCurrentRepl();
    }
}
