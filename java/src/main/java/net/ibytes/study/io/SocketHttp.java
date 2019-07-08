package net.ibytes.study.io;



import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author furong
 * @date 2019/7/7
 */
public class SocketHttp {

    public static void main(String[] args) {
        String host = "www.ibytes.net";
        int port = 80;
        Socket socket =null;
        try {
           socket = new Socket(host, port);

            StringBuilder builder = new StringBuilder();
            builder.append("GET /inde.html HTTT/1.1");
            builder.append("\r\n");


            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(builder.toString().getBytes(StandardCharsets.UTF_8));

            outputStream.flush();


            InputStream inputStream = socket.getInputStream();

            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));

            String line = bf.readLine();

            if(line != null){
                String[] strings = line.split(" ");
                if(strings.length == 3){
                    System.out.println(strings[1]);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {

                }
            }
        }


    }
}
