package com.andrii_a.muze.ui.common

interface UiError

class UiErrorWithRetry(
    val reason: UiText,
    val onRetry: () -> Unit = {}
) : UiError