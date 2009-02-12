package com.bitbakery.clojet.config;

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
