import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class UDPClient extends Thread {

    private String serverName;
    private int serverPort;
    private byte[] buffer;


    private DatagramSocket socket;
    private InetAddress address;

    public UDPClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;

        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName(serverName);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        this.buffer = new byte[256];
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        try {
            while ((userInput = reader.readLine()) != null) {
                byte[] data = userInput.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, serverPort);
                try {
                    socket.send(packet);
                    packet = new DatagramPacket(buffer, buffer.length, address, serverPort);
                    socket.receive(packet);
                    String recievedPacket = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(recievedPacket);
                    String spl[] = recievedPacket.split(": ");
                    if(spl[1].equals("logged out")){
                        socket.close();
                        Thread.currentThread().interrupt();
                        break;
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UDPClient client = new UDPClient("localhost", 4445);
        client.start();
    }
}
