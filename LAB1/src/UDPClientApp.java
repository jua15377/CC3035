import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPClientApp {

    public UDPClientApp(){}

    public void run(int port){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("please write something");
            System.out.println("Client: ");
            String input = scanner.nextLine();
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData;
            byte[] receiveData = new byte[1024];
            sendData = input.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("Server:" + modifiedSentence);
            clientSocket.close();
        }
        catch (IOException e){
            System.out.println("Error"+ e);
        }
    }
}
