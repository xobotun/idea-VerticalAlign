package com.xobotun.idea.valign.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class SettingsConfigurable implements Configurable {

    private SettingsComponent mySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered in an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Vertical Align";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new SettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        SettingsState settings = SettingsState.getInstance();
        return mySettingsComponent.getUseVisualPosition() != settings.getState().useVisualPosition;
    }

    @Override
    public void apply() {
        SettingsState settings = SettingsState.getInstance();
        settings.useVisualPosition = mySettingsComponent.getUseVisualPosition();
    }

    @Override
    public void reset() {
        SettingsState settings = SettingsState.getInstance();
        mySettingsComponent.setUseVisualPosition(settings.useVisualPosition);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}
