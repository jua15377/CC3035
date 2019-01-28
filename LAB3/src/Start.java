import java.util.Scanner;

public class Start {

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please give me the port to run:");
        int port = Integer.parseInt(scanner.nextLine());
        System.out.print("Please give me my name:");
        String name = scanner.nextLine();
        TCPServerApp serverAppTCP = new TCPServerApp(name, port);
        serverAppTCP.runServer();



    }

}
