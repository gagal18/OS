import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        Server server = new Server(7000);
        server.start();
    }
}


class Worker extends Thread {
    Socket socket;
    private HashMap<Integer, Integer> messagesPerUser;
    public Worker(Socket socket, HashMap<Integer, Integer> messagesPerUser) {
        this.socket = socket;
        this.messagesPerUser = messagesPerUser;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String request;
            while (true) {
                request = bufferedReader.readLine();
                Server.incrementTotalMessages();
                int userPort = socket.getPort();
                if (messagesPerUser.containsKey(userPort)) {
                    int currentCount = messagesPerUser.get(userPort);
                    messagesPerUser.put(userPort, currentCount + 1);
                } else {
                    messagesPerUser.put(userPort, 1);
                    System.out.println("FIRST");
                    if(!request.equals("login")){
                        bufferedWriter.write("Initial message is not login!\n");
                        bufferedWriter.write("close\n");
                        bufferedWriter.flush();
                        socket.close();
                        Thread.currentThread().interrupt();
                        break;
                    }
                }                    
                int currentCount = messagesPerUser.get(userPort);

                System.out.println(currentCount);
                System.out.println(request);
                System.out.println(Server.getTotalMessages());
                if(request.equals("login")){
                    bufferedWriter.write("logged in\n");
                    bufferedWriter.write("open\n");
                    bufferedWriter.flush();
                }else if(request.equals("logout")){
                    bufferedWriter.write("logged out\n");
                    bufferedWriter.write("close\n");
                    bufferedWriter.flush();
                    socket.close();
                    Thread.currentThread().interrupt();
                    break;
                }else{
                    bufferedWriter.write(request + "\n");
                    bufferedWriter.write("open\n");
                    bufferedWriter.flush();
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}