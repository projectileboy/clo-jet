package com.bitbakery.clojet.config;

import com.bitbakery.clojet.CloJetIcons;
import com.bitbakery.clojet.CloJetStrings;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Persists CloJet configuration settings
 */
@State(name = "CloJetConfiguration",
        storages = {@Storage(id = "main", file = "$APP_CONFIG$/Clo-Jet-settings.xml")})
public class CloJetConfiguration implements ApplicationComponent, Configurable, PersistentStateComponent<CloJetConfiguration> {

    public String clojurePath;

    private volatile CloJetConfigForm form;


    public void initComponent() {
        // Do nothing
    }

    public void disposeComponent() {
        // Do nothing
    }

    @NotNull
    public String getComponentName() {
        return "CloJetConfig";
    }


    @Nls
    public String getDisplayName() {
        return CloJetStrings.message("plugin.name");
    }

    public Icon getIcon() {
        return CloJetIcons.CLOJURE_LARGE_ICON;
    }

    @Nullable
    @NonNls
    public String getHelpTopic() {
        return null;
    }

    public JComponent createComponent() {
        if (form == null) {
            form = new CloJetConfigForm();
            form.setData(this);
        }
        return form.getRootPanel();
    }

    public boolean isModified() {
        return form != null && form.isModified(this);
    }

    public void apply() throws ConfigurationException {
        if (form != null) {
            form.getData(this);
        }
    }

    public void reset() {
        if (form != null) {
            form.setData(this);
        }
    }

    public void disposeUIResources() {
        form = null;
    }

    public CloJetConfiguration getState() {
        return this;
    }

    public void loadState(CloJetConfiguration that) {
        this.clojurePath = that.clojurePath;
    }

    public String getClojurePath() {
        return clojurePath;
    }

    public void setClojurePath(String clojurePath) {
        this.clojurePath = clojurePath;
    }
}

