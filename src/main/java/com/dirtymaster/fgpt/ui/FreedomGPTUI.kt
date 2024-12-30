package com.dirtymaster.fgpt.ui;

import com.dirtymaster.fgpt.service.FreedomGPTService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class FreedomGPTUI {
    private final FreedomGPTService freedomGPTService;

    public FreedomGPTUI(FreedomGPTService freedomGPTService) {
        this.freedomGPTService = freedomGPTService;
    }

    public void run(String... args) {
        JFrame frame = new JFrame("FreedomGPT Chat");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea promptArea = new JTextArea();
        promptArea.setLineWrap(true);
        promptArea.setWrapStyleWord(true);

        JTextArea responseArea = new JTextArea();
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(true);
        responseArea.setEditable(false);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String userMessage = promptArea.getText();
            String completion = freedomGPTService.getCompletion(userMessage);

            responseArea.setText(completion);
            promptArea.setText("");
        });

        JPanel promptPannel = new JPanel(new BorderLayout());
        promptPannel.add(promptArea, BorderLayout.CENTER);
        promptPannel.add(submitButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(promptPannel);
        panel.add(new JScrollPane(responseArea));

        frame.add(panel);
        frame.setVisible(true);
    }
}
