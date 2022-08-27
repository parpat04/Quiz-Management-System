import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 * Project 5 - Grademic - Client
 * <p>
 * The Client class for our Grademic Program,
 * Acts as the client - method to begin the program after connecting to a sever.
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */
public class Client {

    public static synchronized void main(String[] args)
            throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 1234);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        FirstMenu firstMenu = new FirstMenu(br, pw);
        // Login login = new Login(br, pw);
        SwingUtilities.invokeLater(firstMenu);
    }
}