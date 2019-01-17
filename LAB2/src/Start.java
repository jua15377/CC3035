import java.util.Scanner;

public class Start {

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Choose the client or server\n1) Client (TCP)\n2) Server (TCP)\n3) Client (UDP)\n4) Server (udp)");
        input = scanner.nextLine();

        if (input.equals("1")){
            TCPClientApp clientAppTCP = new TCPClientApp();

            System.out.print("Please give me the port number to connect:");
            int port = scanner.nextInt();
            clientAppTCP.run(port);
        }
        else if (input.equals("2")){
            System.out.print("Please give me the port number to use:");
            int port = scanner.nextInt();
            TCPServerApp servverApp = new TCPServerApp("", port);
            servverApp.runServer();
        }
    }

}
