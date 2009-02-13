package com.bitbakery.clojet.config;

import com.bitbakery.clojet.CloJetStrings;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import static java.awt.Cursor.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CloJetConfigPanel {
    private JComponent rootPanel;
    private TextFieldWithBrowseButton clojureHomeField;
    private JLabel clojureUrl;

    public CloJetConfigPanel() {
        clojureHomeField.addBrowseFolderListener(CloJetStrings.message("plugin.name"), CloJetStrings.message("plugin.name"), null,
                new FileChooserDescriptor(false, true, false, false, false, false));

        enableHyperlink(clojureUrl);
    }

    private void enableHyperlink(final JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            private Color textColor = label.getForeground();

            public void mouseEntered(MouseEvent mouseEvent) {
                label.setCursor(getPredefinedCursor(HAND_CURSOR));
                label.setForeground(Color.BLUE);
            }

            public void mouseExited(MouseEvent mouseEvent) {
                label.setForeground(textColor);
                label.setCursor(getDefaultCursor());
            }

            public void mouseClicked(MouseEvent mouseEvent) {
                BrowserUtil.launchBrowser(label.getText());
            }
        });
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
