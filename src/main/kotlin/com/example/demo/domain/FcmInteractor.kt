package com.example.demo.domain

import com.example.demo.domain.model.PushNotification
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.WebpushConfig
import com.google.firebase.messaging.WebpushNotification
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class FcmInteractor(
        fcmFilePath: String
) {

    init {
        val path = Paths.get(fcmFilePath)
        try {
            val stream = Files.newInputStream(path)
            val firebaseOptions = FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(stream)).build()
            FirebaseApp.initializeApp(firebaseOptions)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun sendNotification(notification: PushNotification, token: String): String {
        val pushNotification = WebpushNotification.builder()
                .setTitle(notification.title)
                .setBody(notification.text)
                .build()
        val webConfig = WebpushConfig.builder()
                .putHeader("ttl", "3")
                .setNotification(pushNotification)
                .build()
        val message = Message.builder()
                .setToken(token)
                .setWebpushConfig(webConfig)
                .build()
        return FirebaseMessaging.getInstance()
                .sendAsync(message)
                .get()
    }
}