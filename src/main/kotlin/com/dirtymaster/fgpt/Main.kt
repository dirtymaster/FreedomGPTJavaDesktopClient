package com.dirtymaster.fgpt

import com.dirtymaster.fgpt.service.FreedomGPTService
import com.dirtymaster.fgpt.ui.FreedomGPTUI

fun main() {
    val freedomGPTService = FreedomGPTService()
    val freedomGPTUI = FreedomGPTUI(freedomGPTService)
    freedomGPTUI.run()
}
