package com.example.studyreminder

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(64, 64, 64, 64)
        }

        val statusText = TextView(this).apply {
            text = "権限を確認しています..."
            textSize = 18f
        }

        val startButton = Button(this).apply {
            text = "ポップアップをテスト表示"
            setOnClickListener {
                if (checkOverlayPermission()) {
                    startService(Intent(this@MainActivity, OverlayService::class.java))
                }
            }
        }

        layout.addView(statusText)
        layout.addView(startButton)
        setContentView(layout)

        if (!checkOverlayPermission()) {
            statusText.text = "「他のアプリの上に表示」を許可してください。"
            requestOverlayPermission()
        } else {
            statusText.text = "準備完了です！ボタンを押してください。"
        }
    }

    private fun checkOverlayPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(this)
        } else {
            true
        }
    }

    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }
}

