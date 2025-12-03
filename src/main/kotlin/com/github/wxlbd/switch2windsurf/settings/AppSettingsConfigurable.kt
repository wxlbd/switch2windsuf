package com.github.wxlbd.switch2windsurf.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class AppSettingsConfigurable : Configurable {

    private var mySettingsComponent: AppSettingsComponent? = null

    override fun getDisplayName(): String = "Switch2Windsurf"

    override fun getPreferredFocusedComponent(): JComponent? {
        return mySettingsComponent?.getPreferredFocusedComponent()
    }

    override fun createComponent(): JComponent? {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent?.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettingsState.instance
        return mySettingsComponent?.windsurfPath != settings.windsurfPath
    }

    override fun apply() {
        val settings = AppSettingsState.instance
        settings.windsurfPath = mySettingsComponent?.windsurfPath ?: "windsurf"
    }

    override fun reset() {
        val settings = AppSettingsState.instance
        mySettingsComponent?.windsurfPath = settings.windsurfPath
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}
