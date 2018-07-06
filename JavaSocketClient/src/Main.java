import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
       Socket socket = null;
       BufferedWriter bw = null;
       BufferedReader br = null;
       String ipNumber = "127.0.0.1";
       int portNumber = 9000;
       String msg = "kyh980909";

       try {

           System.out.println(ipNumber + "Server 연결 중");

           socket = new Socket(ipNumber, portNumber);

           System.out.println("Server 연결 성공");

           bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           br = new BufferedReader(new InputStreamReader(System.in));
           bw.write(msg);
           bw.flush();

           br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           System.out.println("Server send message : " + br.readLine());
           System.out.println("");

       } catch (UnknownHostException e) {
            e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           try {
               br.close();
               bw.close();
               socket.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
}
