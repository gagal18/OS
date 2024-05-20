import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
    int serverPort;
    String serverName;
    InetAddress serverAddress;

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(this.serverName), this.serverPort);
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
        String serverName = System.getenv("SERVER_NAME");
	    String serverPort = System.getenv("SERVER_PORT");

        if (serverPort == null) {
            throw new RuntimeException("Server port should be defined as ENV {SERVER_PORT}.");
        }
        Client client = new Client(serverName, Integer.parseInt(serverPort));
        client.start();

    }
}