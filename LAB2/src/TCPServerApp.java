import java.net.*;
import java.io.*;
import java.util.*;


public class TCPServerApp implements Runnable{
    private String hostName;
    private int port;
    ServerSocket serverSocket;
    Socket newConnection;

    public TCPServerApp(String name, int port){
        this.hostName = name;
        this.port = port;
    }

    public void runServer(){
        try{
            System.out.println(this.hostName +" is running on port: "+this.port);
            serverSocket = new ServerSocket(port);
            newConnection = serverSocket.accept();
            PrintWriter out = new PrintWriter(newConnection.getOutputStream(),true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(newConnection.getInputStream()));

            String inputLine, outputLine;
            System.out.println("Waiting  for messages");
            System.out.println("New request!");
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            System.out.println("User requested to end the communication!");
            out.close();
            in.close();
            newConnection.close();
            serverSocket.close();
        }
        catch (IOException e){
            System.err.println("Error! "+ e);
        }


    }

    @Override
    public void run(){

    }

}
