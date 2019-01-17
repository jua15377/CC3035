import java.io.*;
import java.net.*;

public class UDPServerApp {

    public UDPServerApp(){}

    public void run(int port){
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                System.out.println(receivePacket);
                String in = new String(receivePacket.getData());
                System.out.println("client: " + in);
                InetAddress IPAddress = receivePacket.getAddress();
                int sendPort = receivePacket.getPort();
                String out = sentence.toUpperCase();
                sendData = out.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, sendPort);
                serverSocket.send(sendPacket);
            }
        }
        catch (IOException e){
            System.out.println("Error"+e);
        }
    }
}
