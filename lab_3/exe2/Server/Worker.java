package Server;


import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class Worker extends Thread {
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