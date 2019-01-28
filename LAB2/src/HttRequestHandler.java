import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class HttRequestHandler implements Runnable{
    private Socket theSocket;
    private String name;

    public HttRequestHandler(Socket socket, String name){
        this.theSocket = socket;
        this.name = name;
    }

    @Override
    public void run(){
        try{
            PrintWriter out = new PrintWriter(this.theSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(this.theSocket.getInputStream()));
            BufferedOutputStream dataOut = new BufferedOutputStream(this.theSocket.getOutputStream());
            String inputLine;

            System.out.println("New request!"+ this.theSocket.toString());
            inputLine = in.readLine();
            System.out.println(inputLine);
            parse(inputLine, out, dataOut);

            System.out.println("User requested to end the communication!");
            out.close();
            in.close();
            closeSocket();
        }
        catch (IOException e){
            System.err.println(e);
        }

    }
    public  void closeSocket(){
        try{
            this.theSocket.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public void parse (String line, PrintWriter out,BufferedOutputStream dataOut){
        StringTokenizer parse = new StringTokenizer(line);
        String method = parse.nextToken().toUpperCase();
        String filename = parse.nextToken().toLowerCase();
        try{
            if (!method.equals("GET")  &&  !method.equals("HEAD")) {
                File file = new File(new File("").getAbsolutePath(), "nosupported.html");
                int fileLength = (int) file.length();
                String contentMimeType = "text/html";
                byte[] fileData = readFile(file, fileLength);

                // we send HTTP Headers with data to client
                out.println("HTTP/1.1 501 Not Implemented");
                out.println("Server: "+this.name);
                out.println("Date: " + new Date());
                out.println("Content-type: " + contentMimeType);
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();
                // file
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();

            }
            else {
                if (filename.equals("/")) {
                    filename += "index.html";
                }

                File file = new File(new File("").getAbsolutePath(), filename);
                int fileLength = (int) file.length();
                if (fileLength == 0){
                    file = new File(new File("").getAbsolutePath(), "NotFound.html");
                    fileLength = (int) file.length();
                }
                String content = getContentType(filename);

                if (method.equals("GET")) {
                    byte[] fileData = readFile(file, fileLength);

                    // send HTTP Headers
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: "+this.name);
                    out.println("Date: " + new Date());
                    out.println("Content-type: " + content);
                    out.println("Content-length: " + fileLength);
                    out.println();
                    out.flush();
                    dataOut.write(fileData, 0, fileLength);
                    dataOut.flush();
                }

            }
        }
        catch (Exception e){
            System.err.println(e);
        }

    }
    private byte[] readFile(File file, int fileLength)  {
        try{
            FileInputStream fileIn = new FileInputStream(file);
            byte[] fileData = new byte[fileLength];
            fileIn.read(fileData);
            return fileData;

        }catch (Exception  e){
            return null;
        }
    }

    private String getContentType(String file) {
        if (file.endsWith(".htm") || file.endsWith(".html")){
            return "text/html";
        }
        else{
            return "text/html";

        }
    }
}
