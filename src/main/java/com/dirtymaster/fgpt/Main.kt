package com.dirtymaster.fgpt;

import com.dirtymaster.fgpt.service.FreedomGPTService;
import com.dirtymaster.fgpt.ui.FreedomGPTUI;

import java.io.IOException;
import java.net.URISyntaxException;

public class FgptApplication {
    public static void main(String[] args) throws IOException, URISyntaxException {
        FreedomGPTService freedomGPTService = new FreedomGPTService();
        FreedomGPTUI freedomGPTUI = new FreedomGPTUI(freedomGPTService);
        freedomGPTUI.run(args);
    }
}
