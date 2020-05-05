package com.mmk.lovelettercardgame.api

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.mmk.lovelettercardgame.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.http.conn.ssl.SSLSocketFactory
import java.net.URISyntaxException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class SocketInstance {

    companion object {


        fun getInstance(): Socket? {


            var socket: Socket? = null

            try {
                val options = IO.Options()

                socket = IO.socket(Constants.BASE_URL)
                println("Socket initialize")

            } catch (e: URISyntaxException) {
                println("Connection Problem")
                println(e.message)
            }


            return socket

        }
    }


}