package com.xobotun.idea.valign;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
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

//        FileEditorManager manager = FileEditorManager.getInstance(project);
//        FileEditor editor = manager.getSelectedEditor();

        Caret holder = e.getDataContext().getData(CommonDataKeys.CARET);

        if (holder == null) {
            showInfo("No carets found. Do you have a project open?", project);
            return;
        }

        if (!holder.getEditor().getDocument().isWritable()) {
            showInfo("Current open document is not writable", project);
            return;
        }

        if (holder.getCaretModel().getCaretCount() <= 1) {
            showInfo("You need more than one caret to use vertical align feature", project);
            return;
        }

        List<Caret> carets = holder.getCaretModel().getAllCarets();
        final int maxPosition = carets.stream().map(caret -> caret.getVisualPosition().getColumn()).max(Integer::compareTo).orElse(0);

        showInfo(String.format("Found %d carets, moving to postition: %d", carets.size(), maxPosition), project);

        carets.stream()
              .filter(Caret::isValid)
              .forEach(caret -> {
                  int difference = maxPosition - caret.getVisualPosition().getColumn();
                  caret.getEditor().getDocument().insertString(caret.getOffset(), SPACE.repeat(difference));
              });
    }

    private void showInfo(String info, Project project) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);

        if (statusBar != null) {
            statusBar.setInfo(info);
        } else {
            Messages.showMessageDialog(project, info, "This should be in progress bar, but you don't have any. Is ever that possible?!", Messages.getInformationIcon());
        }
    }
}