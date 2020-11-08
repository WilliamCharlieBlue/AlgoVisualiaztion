
import javax.swing.*;
import java.awt.*;

public class Maindemo {

    public static void main(String[] args) {
	// write your code here
        EventQueue.invokeLater(() -> {
            System.out.println("欢迎大家来到看得见的算法：）");
            JFrame frame = new JFrame("Welcome to Java Swing windows");
            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }
}



