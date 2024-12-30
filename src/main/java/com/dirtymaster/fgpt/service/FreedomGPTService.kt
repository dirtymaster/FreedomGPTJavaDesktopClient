package com.dirtymaster.fgpt.service

import com.dirtymaster.fgpt.service.ApiKeyService.keyFromFile
import dirtymaster.fgpt.client.FreedomGPTTemplate
import dirtymaster.fgpt.model.Model

class FreedomGPTService {
    private val freedomGPTTemplate: FreedomGPTTemplate =
        FreedomGPTTemplate(Model.claudeMinus3Period5MinusSonnet, keyFromFile)

//    init {
//        this.freedomGPTTemplate.rememberMessages = true
//    }

    fun getCompletion(input: String?): String {
        return freedomGPTTemplate.getCompletion(input)
    }
}
