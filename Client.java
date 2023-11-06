import java.net.Socket;
import java.io.*;

public class Client {
 public static void main(String[] args) throws IOException {
 try {
 // Membuat socket client dan terhubung ke server di alamat IP dan port tertentu
 Socket clientSocket = new Socket("localhost", 4999);

 // Mendapatkan output stream untuk mengirim data ke server
OutputStream outputStream = clientSocket.getOutputStream();
OutputStreamWriter writer = new OutputStreamWriter(outputStream);

// Mengirim data ke server
writer.write("Hello, Server!");
writer.flush();

// Menerima data dari server
InputStream inputStream = clientSocket.getInputStream();
InputStreamReader reader = new InputStreamReader(inputStream);
char[] buffer = new char[1024];
int bytesRead = reader.read(buffer);

// Menampilkan data dari server
 System.out.println("Server Response: " + new String(buffer, 0, bytesRead));
 // Menutup socket client
clientSocket.close();
} catch (IOException e) {
 e.printStackTrace();
}
}
 }
