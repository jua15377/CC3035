import java.net.*;
import java.io.*;


public class TCPServerApp{
    private String hostName;
    private int port;
    private ServerSocket serverSocket;
    private Socket newConnection;
    private int maxConnection = 2;

    public TCPServerApp(String name, int port){
        this.hostName = name;
        this.port = port;
        this.readThreadConfig();
    }

    public void runServer(){
        try{
            SimpleThreadpool threadpool = new SimpleThreadpool(this.maxConnection);
            System.out.println(this.hostName +" is running on port: "+this.port);
            serverSocket = new ServerSocket(port);
            while(true){
                newConnection = serverSocket.accept();
                HttRequestHandler httRequestHandler = new HttRequestHandler(newConnection, this.hostName);
                threadpool.execute(httRequestHandler);


//                serverSocket.close();
            }
        }
        catch (IOException e){
            System.err.println("Error! "+ e);
        }
    }

    private void readThreadConfig(){
        try {
            File file = new File(new File("").getAbsolutePath(), "config.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split("=");
                this.maxConnection = Integer.parseInt(s[s.length-1]);
                System.out.println("Max thread set to: "+ s[s.length-1]);
            }
        }
        catch (Exception e){
            System.err.println(e);
            System.out.println("Not config found.\nMax thread set default to: " + 2);
        }

    }
}
