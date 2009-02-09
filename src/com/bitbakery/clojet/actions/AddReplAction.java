package com.bitbakery.clojet.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.util.Icons;
import com.bitbakery.clojet.CloJetIcons;

/**
 * Create a new Clojure REPL as a new tab in the ReplToolWindow.
 */
public class AddReplAction extends ClojureAction {
    public AddReplAction() {
        getTemplatePresentation().setIcon(Icons.ADD_ICON);        
    }

    public void actionPerformed(AnActionEvent e) {
        getReplToolWindow(e).createRepl();
    }
}
