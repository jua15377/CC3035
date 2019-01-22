import java.io.*;
import java.net.Socket;

public class HttRequestHandler implements Runnable{
    private Socket theSocket;

    public HttRequestHandler(Socket socket){
        this.theSocket = socket;
    }

    @Override
    public void run(){
        try{
            PrintWriter out = new PrintWriter(this.theSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(this.theSocket.getInputStream()));
            BufferedOutputStream dataOut = new BufferedOutputStream(this.theSocket.getOutputStream());
            String inputLine, fileName;

            System.out.println("New request!"+ this.theSocket.toString());
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
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

    public void parse {
        StringTokenizer parse = new StringTokenizer(input);
        String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
        // we get file requested
        fileRequested = parse.nextToken().toLowerCase();

        // we support only GET and HEAD methods, we check
        if (!method.equals("GET")  &&  !method.equals("HEAD")) {
            if (verbose) {
                System.out.println("501 Not Implemented : " + method + " method.");
            }

            // we return the not supported file to the client
            File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
            int fileLength = (int) file.length();
            String contentMimeType = "text/html";
            //read content to return to client
            byte[] fileData = readFileData(file, fileLength);

            // we send HTTP Headers with data to client
            out.println("HTTP/1.1 501 Not Implemented");
            out.println("Server: Java HTTP Server from SSaurel : 1.0");
            out.println("Date: " + new Date());
            out.println("Content-type: " + contentMimeType);
            out.println("Content-length: " + fileLength);
            out.println(); // blank line between headers and content, very important !
            out.flush(); // flush character output stream buffer
            // file
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();

        } else {
            // GET or HEAD method
            if (fileRequested.endsWith("/")) {
                fileRequested += DEFAULT_FILE;
            }

            File file = new File(WEB_ROOT, fileRequested);
            int fileLength = (int) file.length();
            String content = getContentType(fileRequested);

            if (method.equals("GET")) { // GET method so we return content
                byte[] fileData = readFileData(file, fileLength);

                // send HTTP Headers
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Java HTTP Server from SSaurel : 1.0");
                out.println("Date: " + new Date());
                out.println("Content-type: " + content);
                out.println("Content-length: " + fileLength);
                out.println(); // blank line between headers and content, very important !
                out.flush(); // flush character output stream buffer

                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            }

            if (verbose) {
                System.out.println("File " + fileRequested + " of type " + content + " returned");
            }

        }
    }
}
