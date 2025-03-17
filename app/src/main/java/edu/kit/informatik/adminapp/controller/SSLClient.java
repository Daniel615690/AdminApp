package edu.kit.informatik.adminapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import edu.kit.informatik.adminapp.model.Hostname;
import edu.kit.informatik.adminapp.model.Milliseconds;
import edu.kit.informatik.adminapp.model.Port;

/**
 * This class provides a client-side SSL connection between two machines,
 * which is established using sockets.
 *
 * @author Daniel Luckey
 * @version 1.0
 */
public class SSLClient implements ClientChannel {
    private static final SocketFactory SOCKET_FACTORY = SSLSocketFactory.getDefault();
    private static final int NO_TIMEOUT = 0;

    private final Hostname hostname;
    private final Port port;
    private final Milliseconds timeout;
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private boolean isClosed;

    /**
     * Creates a new SSLSocketChannel with a timeout.
     *
     * No connection is established.
     * If the timeout is exceeded in {@link #connect()} or {@link #send(String)},
     * a {@link SocketTimeoutException} is thrown. A timeout of 0 is interpreted as an infinite
     * timeout.
     *
     * @param hostname  the hostname of the server
     * @param port      the port of the server
     * @param timeout   the timeout in milliseconds, must be &ge; 0
     */
    public SSLClient(final Hostname hostname, final Port port, final Milliseconds timeout) {
        this.hostname = hostname;
        this.port = port;
        this.timeout = timeout;
        this.isClosed = false;
    }

    /**
     * Creates a new SSLSocketChannel without a timeout.
     *
     * @param hostname  the hostname of the server
     * @param port      the port of the server
     */
    public SSLClient(final Hostname hostname, final Port port) {
        this(hostname, port, new Milliseconds(NO_TIMEOUT));
    }

    /**
     * Establishes a connection to a server.
     * If a connection already exists, a new connection is created,
     * and the old connection is not closed.
     *
     * @throws IOException  if the timeout is exceeded while establishing the connection
     * or another error occurs
     */
    @Override
    public void connect() throws IOException {
        // even if connect() fails, the SSLSocketChannel is not considered closed
        this.isClosed = false;

        this.clientSocket = SOCKET_FACTORY.createSocket();
        this.clientSocket.connect(new InetSocketAddress(
                this.hostname.toString(), this.port.toInt()), this.timeout.asInt());
        this.clientSocket.setSoTimeout(this.timeout.asInt());
        this.writer = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    @Override
    public boolean isConnected() {
        boolean isConnected = true;
        try {
            send(new String());
        } catch (IOException e) {
            isConnected = false;
        }
        return isConnected;
    }

    /**
     * Sends a message to the server and returns the response from the server.
     *
     * @param message   the message
     * @return  the response from the server
     * @throws IOException  if no connection to the server exists,
     *                      if the timeout is exceeded while sending the message,
     *                      or if an error occurs while sending
     */
    @Override
    public String send(final String message) throws IOException {
        if (this.clientSocket == null || this.reader == null || this.writer == null) {
            throw new IOException();
        }

        this.writer.println(message);
        String result = this.reader.readLine();

        if (result == null) {
            // Connection was interrupted
            throw new IOException();
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        if (this.reader != null) this.reader.close();
        if (this.writer != null) this.writer.close();
        if (this.clientSocket != null) this.clientSocket.close();

        this.isClosed = true;
    }

    @Override
    public boolean isClosed() {
        return this.isClosed;
    }
}
