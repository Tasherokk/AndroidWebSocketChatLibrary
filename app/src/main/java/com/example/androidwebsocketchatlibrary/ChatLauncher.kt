package com.example.androidwebsocketchatlibrary

import android.content.Context
import android.content.Intent
import com.example.androidwebsocketchatlibrary.ui.chat.ChatActivity

object ChatLauncher {
    @JvmStatic
    fun start(context: Context) {
        val intent = Intent(context, ChatActivity::class.java)
        context.startActivity(intent)
    }
}
