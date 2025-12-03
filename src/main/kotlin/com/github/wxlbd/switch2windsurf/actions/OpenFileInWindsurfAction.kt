package com.github.wxlbd.switch2windsurf.actions

import com.github.wxlbd.switch2windsurf.settings.AppSettingsState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages

class OpenFileInWindsurfAction : AnAction() {
    
    private val LOG = Logger.getInstance(OpenFileInWindsurfAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val editor = e.getData(CommonDataKeys.EDITOR)
        
        val settings = AppSettingsState.instance
        val windsurfPath = settings.windsurfPath
        
        val path = virtualFile.path
        
        val commandLine = GeneralCommandLine()
        commandLine.exePath = windsurfPath
        
        if (editor != null) {
            val caretModel = editor.caretModel
            val line = caretModel.logicalPosition.line + 1
            val column = caretModel.logicalPosition.column + 1
            commandLine.addParameter("-g")
            commandLine.addParameter("$path:$line:$column")
        } else {
            commandLine.addParameter(path)
        }
        
        try {
            commandLine.createProcess()
        } catch (ex: Exception) {
            LOG.warn("Failed to open Windsurf", ex)
            Messages.showErrorDialog(
                project,
                "Failed to launch Windsurf. Please check the path in settings.\nError: ${ex.message}",
                "Switch2Windsurf Error"
            )
        }
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = project != null && file != null
    }
}
