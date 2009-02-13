package com.bitbakery.clojet.config;

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
