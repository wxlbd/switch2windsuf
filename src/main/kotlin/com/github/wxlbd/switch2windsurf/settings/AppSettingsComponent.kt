package com.github.wxlbd.switch2windsurf.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class AppSettingsComponent {
    val panel: JPanel
    private val windsurfPathField = TextFieldWithBrowseButton()

    init {
        windsurfPathField.addBrowseFolderListener(
            "Select Windsurf Executable",
            null,
            null,
            FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor()
        )

        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Windsurf executable path:"), windsurfPathField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPreferredFocusedComponent() = windsurfPathField

    var windsurfPath: String
        get() = windsurfPathField.text
        set(newText) {
            windsurfPathField.text = newText
        }
}
