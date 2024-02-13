package com.xobotun.idea.valign.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.openapi.components.ServiceManager.getService;

@State(
        name = "com.xobotun.idea.valign.settings.SettingsState",
        storages = @Storage("SettingsState.xml")
)
public final class SettingsState implements PersistentStateComponent<SettingsState> {

    public boolean useVisualPosition = false;

    public static SettingsState getInstance() {
        return getService(SettingsState.class);
    }

    @Nullable
    @Override
    public SettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
