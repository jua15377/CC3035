import java.net.*;
import java.io.*;


public class TCPServerApp{
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
            while(true){
                newConnection = serverSocket.accept();
                HttRequestHandler httRequestHandler = new HttRequestHandler(newConnection);
                Thread thread = new Thread(httRequestHandler);
                thread.start();

//                serverSocket.close();
            }
        }
        catch (IOException e){
            System.err.println("Error! "+ e);
        }
    }
}
