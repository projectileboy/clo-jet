package com.bitbakery.clojet.config;

import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CloJetConfigForm {
    private JTextField clojurePathTextField;
    private JPanel rootPanel;
    private JButton fileChooserButton;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void setData(CloJetConfiguration data) {
        clojurePathTextField.setText(data.clojurePath);
    }

    public void getData(CloJetConfiguration data) {
        data.clojurePath = clojurePathTextField.getText();
    }

    public boolean isModified(CloJetConfiguration data) {
        return clojurePathTextField.getText().equals(data.getClojurePath());
    }
}
