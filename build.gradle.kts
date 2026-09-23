package com.example.studyreminder

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import kotlinx.coroutines.*

class OverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: TextView
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // ポップアップのデザイン設定
        overlayView = TextView(this).apply {
            text = ""
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#CC000000")) // 半透明の黒
            textSize = 18f
            setPadding(48, 64, 48, 64)
            gravity = Gravity.CENTER
        }

        // 画面上部に配置するためのパラメータ
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        }

        windowManager.addView(overlayView, layoutParams)

        // テスト用のメッセージをタイピング風に表示
        simulateTyping("今は、通勤の途中のようですね。この時間を使って勉強しましょう。")
    }

    private fun simulateTyping(message: String) {
        serviceScope.launch {
            var currentText = ""
            for (char in message) {
                currentText += char
                overlayView.text = currentText
                delay(100) // 1文字ごとの表示スピード（0.1秒）
            }
            delay(5000) // メッセージ完成後、5秒待機
            stopSelf() // サービスを終了してポップアップを消す
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::overlayView.isInitialized) {
            windowManager.removeView(overlayView) // 画面からビューを削除
        }
        serviceScope.cancel()
    }
}

