package com.github.wxlbd.switch2windsurf.actions

import com.github.wxlbd.switch2windsurf.settings.AppSettingsState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages

class OpenProjectInWindsurfAction : AnAction() {

    private val LOG = Logger.getInstance(OpenProjectInWindsurfAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val projectPath = project.basePath ?: return

        val settings = AppSettingsState.instance
        val windsurfPath = settings.windsurfPath

        val commandLine = GeneralCommandLine()
        commandLine.exePath = windsurfPath
        commandLine.addParameter(projectPath)

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
        e.presentation.isEnabledAndVisible = e.project != null && e.project?.basePath != null
    }
}
