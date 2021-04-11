package com.example.demo.domain

import com.example.demo.domain.model.PushNotification
import com.google.api.core.ApiFuture
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.FileInputStream
import java.io.IOException

class FcmInteractor {

    init {
        try {
            val stream = FileInputStream("src/main/resources/private_key.json")
            val firebaseOptions = FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(stream)).build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(firebaseOptions)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun sendNotification(notification: PushNotification, token: String): ApiFuture<String> {
        val pushNotification = AndroidNotification.builder()
                .setTitle(notification.title)
                .setBody(notification.text)
                .build()
        val androidConfig = AndroidConfig.builder()
                .setNotification(pushNotification)
                .setTtl(60_000)
                .build()
        val message = Message.builder()
                .setToken(token)
                .setAndroidConfig(androidConfig)
                .build()
        return FirebaseMessaging.getInstance()
                .sendAsync(message)
    }
}