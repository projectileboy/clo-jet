package com.bitbakery.clojet.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;

/**
 * Renames the currently selected REPL in the ReplToolWindow.
 */
public class RenameReplAction extends ClojureAction {
    public RenameReplAction() {
        getTemplatePresentation().setIcon(IconLoader.getIcon("/diff/applyNotConflicts.png"));
    }
    
    public void actionPerformed(AnActionEvent e) {
        getReplToolWindow(e).renameCurrentRepl();
    }
}
