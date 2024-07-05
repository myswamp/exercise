package io.network.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws IOException {
        System.out.println("===server is starting===");
        ServerSocket serverSocket = new ServerSocket(8899);
        System.out.println("===server is blocked to receive requests====");

        while (true) {
            // blocking
            Socket socket = serverSocket.accept();
            System.out.println(socket.getRemoteSocketAddress() + " is online");

            ExecutorService es = Executors.newVirtualThreadPerTaskExecutor();
            es.submit(new SocketHandler(socket));
        }

    }
}

class SocketHandler implements Runnable {

    private final Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println("server received: " + line);
            }
        } catch (IOException e) {
            System.out.println("someone is offline");
        }

    }
}
