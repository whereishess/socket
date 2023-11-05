import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<ClientHandlerThread> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Membuat server socket dan mendengarkan koneksi dari client di port tertentu
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) { // Menerima permintaan koneksi dari client
                Socket clientSocket = serverSocket.accept();

                // Membuat thread untuk menangani koneksi dengan client
                ClientHandlerThread clientThread = new ClientHandlerThread(clientSocket);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandlerThread sender) {
        for (ClientHandlerThread client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}

class ClientHandlerThread extends Thread {
    private Socket clientSocket;
    private PrintWriter writer;

    public ClientHandlerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            // Menerima dan mengirim pesan dari dan ke client
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;

            while ((message = reader.readLine()) != null) {
                System.out.println("Received from Client: " + message);
                Main.broadcast(message, this);
            }

            // Menutup socket client
            clientSocket.close();
            Main.clients.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
