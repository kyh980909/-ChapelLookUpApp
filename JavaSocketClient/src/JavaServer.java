import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class JavaServer {
    public static void main(String[] args) {
        /*=========================================================================================*/
        /*===================================소켓 서버 부분=========================================*/
        /*=========================================================================================*/
        while (true) {

            String ID;
            String PW;

            SocketClient sockC = new SocketClient();

            try {
                ServerSocket server = new ServerSocket(9001);
                System.out.println("서버 소켓이 만들어졌습니다. 포트 : 9001");


                Socket aSocket = server.accept();
                System.out.println("클라이언트와 연결됨.");

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                Object objID = instream.readObject();
                Object objPW = instream.readObject();
                Object objResult;

                System.out.println("받은 데이터 : " + objID);
                System.out.println("받은 데이터 : " + objPW);
                ID = objID.toString();
                PW = objPW.toString();

                System.out.println("소켓 클라이언트 진입");

                objResult = sockC.GetResult(ID, PW);

                System.out.println(objResult);
                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                outstream.writeObject(objResult);
                outstream.flush();
                System.out.println("보낸 데이터 : " + objResult + " from Server.");


                aSocket.close();
                System.out.println("소켓 닫음.");
                Thread.sleep(5000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class SocketClient {
        private String GetResult(String ID, String PW) {
            /*=========================================================================================*/
            /*===================================소켓 클라이언트 부분====================================*/
            /*=========================================================================================*/

            Socket socket = null;
            BufferedWriter bw = null;
            BufferedReader br = null;
            String ipNumber = "127.0.0.1";
            String result = null;
            int portNumber = 9000;

            try {
                System.out.println("소켓클라이언트 부분");

                System.out.println(ipNumber + "Server 연결 중");

                socket = new Socket(ipNumber, portNumber);

                System.out.println("Server 연결 성공");

                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(System.in));
                bw.write(ID);
                bw.flush();
                bw.write(PW);
                bw.flush();

                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                result = br.readLine();
                System.out.println("Server send message : " + result);
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
            return result;
        }
    }
}
