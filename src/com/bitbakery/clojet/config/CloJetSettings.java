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

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.util.RoamingTypeDisabled;
import com.intellij.util.xmlb.XmlSerializerUtil;

/**
 * Clo-Jet settings
 */
@State(
        name = "CloJetSettings",
        storages = {
                @Storage(
                        id = "clojet_config",
                        file = "$APP_CONFIG$/clojet_config.xml"
                )}
)
public class CloJetSettings implements PersistentStateComponent<CloJetSettings>, RoamingTypeDisabled {

    public String CLOJURE_HOME = "";

    public CloJetSettings getState() {
        return this;
    }

    public void loadState(CloJetSettings that) {
        XmlSerializerUtil.copyBean(that, this);
    }

    public static CloJetSettings getInstance() {
        return ServiceManager.getService(CloJetSettings.class);
    }
}
