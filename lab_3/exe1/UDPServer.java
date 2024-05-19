import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Thread {

    private DatagramSocket socket;
    private byte[] buffer;

    public UDPServer(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.buffer = new byte[256];
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    
        while (true) {
            try {
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("RECEIVED: " + received);
                if(received.equals("login")) {
                    received = "logged in";
                }
                if(received.equals("logout")) {
                    received = "logged out";
                }
                byte[] sendData = new String("echo: "+received).getBytes();
                
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                packet.setData(buffer);                
                socket.send(sendPacket);
                
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
    

    public static void main(String[] args) {
        UDPServer server = new UDPServer(4445);
        server.start();
    }
}
