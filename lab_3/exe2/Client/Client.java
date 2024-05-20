package Client;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Client extends Thread {
    int serverPort;
    InetAddress serverAddress;

    public Client(int serverPort, InetAddress serverAddress) {
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(serverAddress, serverPort);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String user;
            while(socket.isConnected()){
                user = br.readLine();
                writer.write(user + "\n");
                writer.flush();
                String returnMessage = reader.readLine();
                String statusMessage = reader.readLine();
                System.out.println("RECIEVED:" + returnMessage);
                if(statusMessage.equals("close")){
                    socket.close(); 
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws UnknownHostException {
        Client client = new Client(5000, InetAddress.getLocalHost());
        client.start();
    }
}