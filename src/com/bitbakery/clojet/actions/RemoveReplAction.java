package com.bitbakery.clojet.actions;

/*
 * Copyright (c) Kurt Christensen, 2009
 *
 * Licensed under the Artistic License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

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
