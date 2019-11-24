package com.xobotun.idea.valign;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class VerticalAlignAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        Project project = e.getProject();
        Messages.showMessageDialog(project, "Hello world!", "Greeting", Messages.getInformationIcon());

    }
}
