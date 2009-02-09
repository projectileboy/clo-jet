package com.bitbakery.clojet.config;

import com.bitbakery.clojet.CloJetStrings;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class CloJetConfigForm {
    private JTextField clojurePathTextField;
    private JPanel rootPanel;
    private JButton fileChooserButton;
    private JFileChooser chooser;

    public CloJetConfigForm() {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        fileChooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!StringUtil.isEmptyOrSpaces(clojurePathTextField.getText())) {
                    chooser.setSelectedFile(new File(clojurePathTextField.getText()));
                }

                if (JFileChooser.APPROVE_OPTION == chooser.showDialog(rootPanel, CloJetStrings.message("button.select"))) {
                    clojurePathTextField.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

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
        return !clojurePathTextField.getText().equals(data.getClojurePath());
    }
}
