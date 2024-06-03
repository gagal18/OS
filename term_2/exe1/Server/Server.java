package term_2.exe1.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends Thread {
    private int port;

    Server(int port) {
        this.port = port;
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
            new Worker(client).start();
            System.out.println(client.getInetAddress() + " " + client.getPort());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
        server.start();
    }
}

class Worker extends Thread {
    Socket socket;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        BufferedReader reader = null;
        String line;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ArrayList<String> allowedFiles = new ArrayList<>();
            String request;
            while (true) {
                request = bufferedReader.readLine();                 
                System.out.println(request);
                if(request.equals("listFiles")){
                    File directory = new File("./"); 
                    File[] files = directory.listFiles(); 
                    if (files != null) { 
                        for (File file : files) { 
                            allowedFiles.add(file.getName());
                            System.out.println(file.getName());
                            bufferedWriter.write(file.getName()+"\n"); 
                        } 
                    } 
                    bufferedWriter.write("END\n");
                    bufferedWriter.flush();
                }else{
                    String spl[] = request.split("=");
                    if(spl.length <= 1){
                        bufferedWriter.write(request+"\n");
                        bufferedWriter.write("END\n");
                        bufferedWriter.flush();
                    }else{
                        System.out.println(spl[1]);
                        String fileName = spl[1];
                        if(allowedFiles.contains(fileName)){
                            bufferedWriter.write("downloadFile:start\n");
                            reader = new BufferedReader(new InputStreamReader(new FileInputStream("./"+ fileName)));
                            while ((line = reader.readLine())!=null) {
                                bufferedWriter.write(line+"\n");
                            }
                            bufferedWriter.write("downloadFile:end\n");
                        }else{
                            bufferedWriter.write("file does not exsist\n");
                        }
                        bufferedWriter.write("END\n");
                        bufferedWriter.flush();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

