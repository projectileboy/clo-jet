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

import com.bitbakery.clojet.psi.Defmacro;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import javax.swing.*;
import java.awt.*;

/**
 * Event handler for the "Macroexpand-1" action within a Clojure code editor
 */
public class MacroexpandOneAction extends ClojureAction {

    public void actionPerformed(final AnActionEvent e) {

        final Editor ed = e.getData(DataKeys.EDITOR);
        if (ed == null)
            return;

        Defmacro dm = getCurrentDefmacro(ed, e);
        if (dm != null) {
            getReplToolWindow(e).writeToCurrentRepl(dm.getText(), false);

            // TODO - We should also try to load any other functions or macros that we depend on...?

            String macex = collectMacroexpansions(e, dm);
            if (!StringUtil.isEmptyOrSpaces(macex)) {
                new MacroexpandDialog(macex)
                        .showDialog(this, getCaretPosition(ed));
            }
        }
    }


    private String collectMacroexpansions(AnActionEvent e, Defmacro dm) {
        StringBuffer macroexpansions = new StringBuffer();
        int[] paramCounts = dm.getParameterCounts();

        for (int i = 0; i < paramCounts.length; i++) {

            String macex = getReplToolWindow(e).writeToCurrentRepl(
                    "(macroexpand-1 '(" + dm.getName() + " " +
                            generateDummyParams(paramCounts, i) + "))", false);

            if (macex != null) {
                macroexpansions.append(macex);
                macroexpansions.append("\r\n");
            }
        }

        return macroexpansions.toString();
    }

    private StringBuffer generateDummyParams(int[] paramCounts, int i) {
        StringBuffer params = new StringBuffer();

        for (int j = 0; j < paramCounts[i]; j++) {
            params.append(":param").append(j).append(" ");
        }
        return params;
    }

    private Defmacro getCurrentDefmacro(Editor ed, AnActionEvent e) {
        final PsiFile psiFile = e.getData(DataKeys.PSI_FILE);
        if (psiFile == null)
            return null;

        int offset = ed.getCaretModel().getOffset();
        PsiElement el = psiFile.findElementAt(offset);
        if (el == null)
            return null;

        while (!(el instanceof Defmacro)) {
            el = el.getParent();
            if (el == null) {
                return null;
            }
        }
        return (Defmacro) el;
    }

    private Point getCaretPosition(Editor ed) {
        VisualPosition caret = ed.getCaretModel().getVisualPosition();
        Point pt = ed.visualPositionToXY(caret);
        SwingUtilities.convertPointToScreen(pt, ed.getContentComponent());
        return pt;
    }
}