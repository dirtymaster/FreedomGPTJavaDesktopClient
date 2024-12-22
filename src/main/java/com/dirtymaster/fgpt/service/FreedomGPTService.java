package com.dirtymaster.fgpt.service;

import dirtymaster.fgpt.client.FreedomGPTTemplate;
import dirtymaster.fgpt.model.Model;

import java.io.IOException;
import java.net.URISyntaxException;

public class FreedomGPTService {
    private final FreedomGPTTemplate freedomGPTTemplate;

    public FreedomGPTService() throws IOException, URISyntaxException {
        this.freedomGPTTemplate = new FreedomGPTTemplate(Model.CLAUDE_3_5_SONNET, ApiKeyService.getKeyFromFile());
    }

    public String getCompletion(String input) {
        return freedomGPTTemplate.getCompletion(input);
    }

    public FreedomGPTTemplate getFreedomGPTTemplate() {
        return freedomGPTTemplate;
    }
}
