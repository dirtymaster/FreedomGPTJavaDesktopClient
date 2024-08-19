package com.dirtymaster.fgpt.ui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

import com.dirtymaster.fgpt.config.RequestConfiguration;
import com.dirtymaster.fgpt.model.Message;
import com.dirtymaster.fgpt.service.FreedomGPTService;
import com.dirtymaster.fgpt.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FreedomGPTUI implements CommandLineRunner {
    private final FreedomGPTService gptService;
    private final MessagesService messagesService;
    private final RequestConfiguration requestConfiguration;

    @Autowired
    public FreedomGPTUI(FreedomGPTService gptService, MessagesService messagesService,
                        RequestConfiguration requestConfiguration) {
        this.gptService = gptService;
        this.messagesService = messagesService;
        this.requestConfiguration = requestConfiguration;
    }

    @Override
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

        JTextField hostField = new JTextField(requestConfiguration.getUrl());
        JTextField apiKeyField = new JTextField(requestConfiguration.getApiKey());
        JTextField modelField = new JTextField(requestConfiguration.getModel());
        JTextField maxTokensField = new JTextField(String.valueOf(requestConfiguration.getMaxTokens()));
        JTextField temperatureField = new JTextField(String.valueOf(requestConfiguration.getTemperature()));
        JTextField topKField = new JTextField();
        JTextField topPField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            requestConfiguration.setUrl(hostField.getText());
            requestConfiguration.setApiKey(apiKeyField.getText());
            requestConfiguration.setModel(modelField.getText());
            requestConfiguration.setMaxTokens(Integer.parseInt(maxTokensField.getText()));
            requestConfiguration.setTemperature(new BigDecimal(temperatureField.getText()));
            if (!topKField.getText().isEmpty()) {
                requestConfiguration.setTopK(Integer.parseInt(topKField.getText()));
            }
            if (!topPField.getText().isEmpty()) {
                requestConfiguration.setTopP(new BigDecimal(topPField.getText()));
            }

            String userMessage = promptArea.getText();
            messagesService.addMessage(new Message().role(Message.RoleEnum.USER).content(userMessage));
            gptService.getCompletion();

            responseArea.setText(messagesService.getMessagesFormatted());
            promptArea.setText("");
        });

        JPanel parametersPanel = new JPanel(new GridLayout(7, 2));
        parametersPanel.add(new JLabel("URL:"));
        parametersPanel.add(hostField);
        parametersPanel.add(new JLabel("API Key:"));
        parametersPanel.add(apiKeyField);
        parametersPanel.add(new JLabel("Model:"));
        parametersPanel.add(modelField);
        parametersPanel.add(new JLabel("Max Tokens:"));
        parametersPanel.add(maxTokensField);
        parametersPanel.add(new JLabel("Temperature:"));
        parametersPanel.add(temperatureField);
        parametersPanel.add(new JLabel("Top K:"));
        parametersPanel.add(topKField);
        parametersPanel.add(new JLabel("Top P:"));
        parametersPanel.add(topPField);


        JPanel promptPannel = new JPanel(new BorderLayout());
        promptPannel.add(promptArea, BorderLayout.CENTER);
        promptPannel.add(submitButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(parametersPanel);
        panel.add(promptPannel);
        panel.add(new JScrollPane(responseArea));

        frame.add(panel);
        frame.setVisible(true);
    }
}
