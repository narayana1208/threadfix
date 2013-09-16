package com.denimgroup.threadfix.plugin.eclipse.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class AcceptAllTrustFactory implements ProtocolSocketFactory {

	private SSLContext sslContext = null;

	private SSLContext createAcceptAllSSLContext() {
		try {
			AcceptAllTrustManager acceptAllTrustManager = new AcceptAllTrustManager();
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null,
					new AcceptAllTrustManager[] { acceptAllTrustManager },
					null);
			return context;
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

    private SSLContext getSSLContext() {
        if(this.sslContext == null) {
            this.sslContext = createAcceptAllSSLContext();
        }

        return this.sslContext;
    }

    @Override
	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    @Override
	public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort, final HttpConnectionParams params) throws IOException {
        if(params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }

        int timeout = params.getConnectionTimeout();
        SocketFactory socketFactory = getSSLContext().getSocketFactory();

        if(timeout == 0) {
            return socketFactory.createSocket(host, port, localAddress, localPort);
        }

        else {
            Socket socket = socketFactory.createSocket();
            SocketAddress localAddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteAddr = new InetSocketAddress(host, port);
            socket.bind(localAddr);
            socket.connect(remoteAddr, timeout);
            return socket;
        }
    }

    @Override
	public Socket createSocket(String host, int port) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }
    
    private class AcceptAllTrustManager implements X509TrustManager {
        @Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
		public X509Certificate[] getAcceptedIssuers() { return null; }
    }
}