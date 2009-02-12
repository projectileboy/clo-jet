package com.bitbakery.clojet.config;

import com.bitbakery.clojet.CloJetStrings;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CloJetConfigPanel {
    private JComponent rootPanel;
    private TextFieldWithBrowseButton clojureHomeField;

    public CloJetConfigPanel() {
        clojureHomeField.addBrowseFolderListener(CloJetStrings.message("plugin.name"), CloJetStrings.message("plugin.name"), null,
                new FileChooserDescriptor(false, true, false, false, false, false));
    }

    public JComponent getPanel() {
        return rootPanel;
    }

    public void load(@NotNull CloJetSettings settings) {
        clojureHomeField.setText(settings.CLOJURE_HOME);
    }

    public boolean isModified(@NotNull CloJetSettings settings) {
        return !settings.CLOJURE_HOME.equals(clojureHomeField.getText());
    }

    public void save(@NotNull CloJetSettings settings) {
        settings.CLOJURE_HOME = clojureHomeField.getText();
    }
}
