package io.network.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class BioClient {

    public static void main(String[] args) throws IOException {
        System.out.println("===client is starting===");
        Socket socket = new Socket("127.0.0.1", 8899);

        OutputStream os = socket.getOutputStream();
        PrintStream ps = new PrintStream(os);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("say something: ");
            String msg = scanner.nextLine();
            ps.println(msg);
            ps.flush();
        }

    }
}
