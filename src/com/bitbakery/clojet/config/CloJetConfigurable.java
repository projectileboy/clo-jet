package com.bitbakery.clojet.config;

import com.bitbakery.clojet.CloJetIcons;
import com.bitbakery.clojet.CloJetStrings;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CloJetConfigurable implements Configurable {
    private CloJetConfigPanel panel;

    public String getDisplayName() {
        return CloJetStrings.message("plugin.name");
    }

    @Nullable
    public Icon getIcon() {
        return CloJetIcons.CLOJURE_CONFIG_ICON;
    }

    @Nullable
    public String getHelpTopic() {
        return null;
    }

    public JComponent createComponent() {
        panel = new CloJetConfigPanel();
        panel.load(CloJetSettings.getInstance());
        return panel.getPanel();
    }

    public boolean isModified() {
        return panel.isModified(CloJetSettings.getInstance());
    }

    public void apply() throws ConfigurationException {
        panel.save(CloJetSettings.getInstance());
    }

    public void reset() {
        panel.load(CloJetSettings.getInstance());
    }

    public void disposeUIResources() {
        // ??? panel = null
    }
}
