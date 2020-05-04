package com.mmk.lovelettercardgame.api;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mmk.lovelettercardgame.utils.Constants;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class SocketInstanceJava {

    private static SSLContext mySSLContext;

    private static final TrustManager[] trustAllCerts= new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    } };



   private static HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };





    public static Socket getInstance(){
        IO.Options opts = new IO.Options();
        opts.secure=true;
        opts.port=443;
        try {
            mySSLContext = SSLContext.getInstance("TLS");
            mySSLContext.init(null,trustAllCerts,null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        opts.sslContext = mySSLContext;
        opts.hostnameVerifier = myHostnameVerifier;
        try {
            Socket socket = IO.socket(Constants.Companion.getBASE_URL(),opts);
            return socket;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


}
