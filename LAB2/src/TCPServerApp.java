import javax.activation.DataHandler;
import java.net.*;
import java.io.*;

public class TCPServerApp {
    private String hostIp;
    private int port;
    ServerSocket serverSocket;
    Socket newConnection;

    public TCPServerApp(String ip, int port){
        this.hostIp = ip;
        this.port = port;
    }

    public void runServer(){
        try{
            System.out.println("Server is running on port: "+this.port);
            serverSocket = new ServerSocket(port);
            newConnection = serverSocket.accept();
            PrintWriter out = new PrintWriter(newConnection.getOutputStream(),true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(newConnection.getInputStream()));

            String inputLine, outputLine;
            System.out.println("Waiting  for messages");
            while ((inputLine = in.readLine()) != null) {
                System.out.println("recived: "+ inputLine);
                outputLine = inputLine.toUpperCase();
                System.out.println("converted: " + outputLine);
                out.println(outputLine);

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
}
