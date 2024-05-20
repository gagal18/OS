package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends Thread {
    private int port;
    private static int total;
    private HashMap<Integer, Integer> messagesPerUser;
    static Lock lock;



    Server(int port) {
        this.port = port;
        this.messagesPerUser = new HashMap<>();
        lock = new ReentrantLock();
        this.total = 0;

    }

    @Override
    public void run() {
        System.out.println("Server starting...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server started");
        System.out.println("Waiting for connection...");

        while (true) {
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("new client - creating new worker thread...");
            new Worker(client, messagesPerUser).start();
            System.out.println(client.getInetAddress() + " " + client.getPort());
        }
    }
    public static void incrementTotalMessages() {
        lock.lock();
        total++;
        lock.unlock();
    }
    public static int getTotalMessages() {
        return total;
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
        server.start();
    }
}
