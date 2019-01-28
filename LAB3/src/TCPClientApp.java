import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClientApp {
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    public TCPClientApp(){}

    public void run(int port){

        try {

            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer="";
            String fromUser;
            Scanner scanner = new Scanner(System.in);


            while (1==1) {
                System.out.println("please write something:");
                System.out.print("Client: ");
                fromUser = scanner.nextLine();
                out.println(fromUser);
                fromServer = in.readLine();
                System.out.println("Server: " + fromServer);
                System.out.println("write exit to...  exit:");
                if (fromUser.equals("exit")){
                    break;
                }
            }
            out.close();
            in.close();
            stdIn.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Error"+e);
            System.exit(1);
        }
    }
}
