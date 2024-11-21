import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1717)) {
            System.out.println("Server is running...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("1 client connected.");

                    while (true) {
                        Shape s = (Shape) objectInputStream.readObject();

                        if (s == null) {
                            System.out.println("Shutting down server...");
                            return;
                        }

                        double area = s.calculateArea();
                        String shapeDetails = s.toString();
                        System.out.println("Client requested the area of a " + shapeDetails + ". Answer is " + area);

                        out.println("Server message:");
                        out.println("Answer is: " + area);
                    }
                } catch (EOFException e) {
                    System.out.println("Client disconnected.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
