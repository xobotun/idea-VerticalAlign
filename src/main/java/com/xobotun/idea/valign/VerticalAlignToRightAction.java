package com.xobotun.idea.valign;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VerticalAlignToRightAction extends AnAction {
    private static final String SPACE = " ";

    @Override
    public void actionPerformed(@NotNull final AnActionEvent e) {
        Project project = e.getProject();
        if (project == null)
            return;

        Caret holder = e.getDataContext().getData(CommonDataKeys.CARET);

        if (holder == null) {
            showInfo("No carets found. Do you have a file opened?", project);
            return;
        }

        if (!holder.getEditor().getDocument().isWritable()) {
            showInfo("The current opened document is not writable", project);
            return;
        }

        if (holder.getCaretModel().getCaretCount() <= 1) {
            showInfo("You need more than one caret to use the vertical align feature", project);
            return;
        }

        List<Caret> carets = holder.getCaretModel().getAllCarets();
        final int maxPosition = carets.stream().map(caret -> caret.getVisualPosition().getColumn()).max(Integer::compareTo).orElse(0);

        showInfo(String.format("Found %d carets, moving to the column %d", carets.size(), maxPosition), project);

        WriteCommandAction.runWriteCommandAction(project, () ->
            carets.stream()
                .filter(Caret::isValid)
                .forEach(caret -> {
                    int difference = getPositionDifference(maxPosition, caret);
                    if (difference <= 0) return;

                    caret.getEditor().getDocument().insertString(caret.getOffset(), SPACE.repeat(difference));
                    // Sometimes carets are moved automatically, sometimes – not. Recalculate difference again after spaces were added.
                    caret.moveCaretRelatively(getPositionDifference(maxPosition, caret),0,false,false);
                })
        );
    }

    private void showInfo(String info, Project project) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);

        if (statusBar != null) {
            statusBar.setInfo(info);
        } else {
            Messages.showMessageDialog(project, info, "This should be in the progress bar, but you don't have any. Is ever that possible?!", Messages.getInformationIcon());
        }
    }

    private int getPositionDifference(int maxPosition, Caret caret) {
        return maxPosition - caret.getVisualPosition().getColumn();
    }
}
