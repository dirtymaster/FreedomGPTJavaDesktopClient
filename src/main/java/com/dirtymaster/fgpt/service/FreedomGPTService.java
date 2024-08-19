package com.dirtymaster.fgpt.service;

import com.dirtymaster.fgpt.ApiClient;
import com.dirtymaster.fgpt.ApiException;
import com.dirtymaster.fgpt.api.DefaultApi;
import com.dirtymaster.fgpt.config.RequestConfiguration;
import com.dirtymaster.fgpt.model.ChatCompletionRequest;
import com.dirtymaster.fgpt.model.ChatCompletionResponse;
import com.dirtymaster.fgpt.model.Message;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class FreedomGPTService {
    private final MessagesService messagesService;
    private final RequestConfiguration requestConfiguration;

    private DefaultApi chatApi;

    public FreedomGPTService(MessagesService messagesService, RequestConfiguration requestConfiguration) {
        this.messagesService = messagesService;
        this.requestConfiguration = requestConfiguration;
        if (requestConfiguration.getApiKey() != null) {
            updateApiClient();
        }
    }

    public void getCompletion() {
        if (chatApi == null) {
            updateApiClient();
        }

        ChatCompletionRequest request = new ChatCompletionRequest()
                .model(requestConfiguration.getModel())
                .messages(messagesService.getMessages())
                .stream(false)
                .maxTokens(requestConfiguration.getMaxTokens())
                .temperature(requestConfiguration.getTemperature())
                .topK(requestConfiguration.getTopK())
                .topP(requestConfiguration.getTopP());

        ChatCompletionResponse response;
        try {
            response = chatApi.chatCompletionsPost(request);
        } catch (ApiException e) {
            messagesService.addMessage(new Message().content("ERROR").role(Message.RoleEnum.ASSISTANT));
            return;
        }
        Message responseMessage = response.getChoices().get(0).getMessage();
        messagesService.addMessage(responseMessage);
    }

    private void updateApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(requestConfiguration.getUrl());
        apiClient.addDefaultHeader(AUTHORIZATION, "BEARER " + requestConfiguration.getApiKey());
        this.chatApi = new DefaultApi(apiClient);
    }
}
