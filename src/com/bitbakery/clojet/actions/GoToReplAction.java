package com.bitbakery.clojet.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Event handler for the "Go To REPL" action within a Clojure code editor
 */
public class GoToReplAction extends ClojureAction {

    public void actionPerformed(final AnActionEvent e) {
        getReplToolWindow(e).requestFocus();
    }
}