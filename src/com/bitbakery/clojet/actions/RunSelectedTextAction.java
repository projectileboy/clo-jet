package com.bitbakery.clojet.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.text.StringUtil;

/**
 * Event handler for the "Run Selection" action within an Arc code editor - runs the currently selected text within the current REPL.
 */
public class RunSelectedTextAction extends ClojureAction {

    public void actionPerformed(final AnActionEvent e) {
        final Editor ed = e.getData(DataKeys.EDITOR);
        if (ed == null) {
            return;
        }
        String text = ed.getSelectionModel().getSelectedText();

        if (StringUtil.isEmptyOrSpaces(text)) {
            return;
        }
        getReplToolWindow(e).writeToCurrentRepl(text);
    }
}

