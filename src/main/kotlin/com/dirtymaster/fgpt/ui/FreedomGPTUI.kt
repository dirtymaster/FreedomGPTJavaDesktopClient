package com.dirtymaster.fgpt.ui

import com.dirtymaster.fgpt.service.FreedomGPTService
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.*

class FreedomGPTUI(private val freedomGPTService: FreedomGPTService) {
    fun run() {
        val frame = JFrame("FreedomGPT Chat").apply {
            setSize(800, 600)
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        }

        val promptArea = JTextArea().apply {
            lineWrap = true
            wrapStyleWord = true
        }

        val responseArea = JTextArea().apply {
            lineWrap = true
            wrapStyleWord = true
            isEditable = false
        }

        val submitButton = JButton("Submit").apply {
            addActionListener {
                val userMessage = promptArea.text
                val completion = freedomGPTService.getCompletion(userMessage)
                responseArea.text = completion
                promptArea.text = ""
            }
        }

        val promptPanel = JPanel(BorderLayout()).apply {
            add(promptArea, BorderLayout.CENTER)
            add(submitButton, BorderLayout.EAST)
        }

        frame.apply {
            layout = BorderLayout()
            val panel = JPanel(GridLayout(0, 1)).apply {
                add(promptPanel)
                add(JScrollPane(responseArea))
            }
            add(panel)
            isVisible = true
        }
    }
}
