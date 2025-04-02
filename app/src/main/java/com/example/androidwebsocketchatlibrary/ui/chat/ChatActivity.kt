package com.example.androidwebsocketchatlibrary.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidwebsocketchatlibrary.databinding.ActivityChatBinding
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var webSocket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initWebSocket()

        binding.btnSend.setOnClickListener {
            val messageToSend = binding.etMessage.text.toString()
            if (messageToSend.isNotBlank()) {
                sendMessage(messageToSend)
                binding.etMessage.text.clear()
            }
        }
    }

    private fun initRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.rvMessages.layoutManager = LinearLayoutManager(this)
        binding.rvMessages.adapter = chatAdapter
    }

    private fun initWebSocket() {
        val client = OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url("wss://echo.websocket.org")
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "Connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocket", "Received text: $text")
                val displayText = when {
                    text == "203 = 0xcb" -> "Соединение активно (ping ответ от сервера)"
                    text.startsWith("Request served by") -> "Добро пожаловать в тестовый чат"
                    else -> text
                }

                runOnUiThread {
                    chatAdapter.submitNewMessage(ChatMessage(displayText, false), binding.rvMessages)
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("WebSocket", "Received bytes: $bytes")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "Error: ${t.message}", t)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                Log.d("WebSocket", "Closing: $code / $reason")
            }
        }

        webSocket = client.newWebSocket(request, listener)
    }

    private fun sendMessage(message: String) {
        chatAdapter.submitNewMessage(ChatMessage(message, true), binding.rvMessages)
        webSocket.send(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket.close(1000, "Activity destroyed")
    }
}
