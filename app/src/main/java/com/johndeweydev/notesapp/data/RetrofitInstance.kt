package com.johndeweydev.notesapp.data

import com.johndeweydev.notesapp.api.NotesApi
import com.johndeweydev.notesapp.utils.Constants.Companion.BASE_URL
import com.johndeweydev.notesapp.utils.TrustAllCertificates
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

object RetrofitInstance {

    private val retrofit by lazy {
        // Trust all certificates for now, do not do this in production environment.
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(
            null,
            arrayOf<TrustManager>(TrustAllCertificates()),
            java.security.SecureRandom()
        )

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)

        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, TrustAllCertificates())
            .hostnameVerifier { _, _ -> true } // This disables hostname verification
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val notesApi: NotesApi by lazy {
        retrofit.create(NotesApi::class.java)
    }
}