<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.studyreminder">

    <!-- 他のアプリの上に重ねて表示する権限 -->
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="StudyReminder"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.StudyReminder"> <!-- テーマ名は環境に合わせてください -->

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- オーバーレイ表示をバックグラウンドで行うサービス -->
        <service
            android:name=".OverlayService"
            android:enabled="true"
            android:exported="false" />
    </application>

</manifest>

