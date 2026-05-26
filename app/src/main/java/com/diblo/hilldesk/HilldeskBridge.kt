package com.diblo.hilldesk

import android.webkit.JavascriptInterface
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class HilldeskBridge(
    private val onToast: (type: String, title: String, message: String) -> Unit
) {
    @JavascriptInterface
    fun postMessage(json: String) {
        try {
            val element = Json.parseToJsonElement(json)
            val obj = element.jsonObject
            val type = obj["type"]?.jsonPrimitive?.content ?: return
            if (type == "toast") {
                val status = obj["status"]?.jsonPrimitive?.content ?: "info"
                val title = obj["title"]?.jsonPrimitive?.content ?: ""
                val message = obj["message"]?.jsonPrimitive?.content ?: ""
                onToast(status, title, message)
            }
        } catch (_: Exception) {
        }
    }
}
