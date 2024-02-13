package com.xobotun.idea.valign.settings;

import com.intellij.openapi.ui.JBPopupMenu;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class SettingsComponent {

    private final JPanel myMainPanel;
    private final JBCheckBox useVisualPosition = new JBCheckBox("Use visual position of carets ", false);
    private final JBLabel explanation = new JBLabel("Visual positioning attempts to align carets in a vertical line on screen; logical positioning aligns them by line number. Usually, the result is the same, but visual alignment may behave differently on folded lines and in presence of argument tooltips");

    public SettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addComponent(explanation, 0)
                .addComponent(useVisualPosition, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return useVisualPosition;
    }

    public boolean getUseVisualPosition() {
        return useVisualPosition.isSelected();
    }

    public void setUseVisualPosition(boolean newStatus) {
        useVisualPosition.setSelected(newStatus);
    }

}
