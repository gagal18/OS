package term_2.exe1.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            Scanner reader = new Scanner(socket.getInputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            
            while ((userInput = br.readLine()) != null) {
                writer.write(userInput + "\n");
                writer.flush();
                String line;
                while (!(line = reader.nextLine()).equals("END")) {
                    System.out.println(line);
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
