import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1717);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server.");
            while (true) {
                System.out.println("Enter 3D object name:");
                String shapeType = scanner.nextLine();
                if (shapeType.equalsIgnoreCase("Q")) {
                    objectOutputStream.writeObject(null);
                    System.out.println("Process finished with exit code 0");
                    break;
                }
                Shape shape = null;

                if (shapeType.equalsIgnoreCase("Circle")) {
                    System.out.print("Enter radius: ");
                    double radius = scanner.nextDouble();
                    scanner.nextLine();
                    shape = new Circle(radius);
                } else if (shapeType.equalsIgnoreCase("Rectangle")) {
                    System.out.print("Enter length: ");
                    double length = scanner.nextDouble();
                    System.out.print("Enter width: ");
                    double width = scanner.nextDouble();
                    scanner.nextLine();
                    shape = new Rectangle(length, width);
                } else {
                    System.out.println("Invalid object name.");
                    continue;
                }
                objectOutputStream.writeObject(shape);

                System.out.println(in.readLine());
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}