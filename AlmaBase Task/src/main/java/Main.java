import org.kohsuke.github.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Main {

    public static boolean check = false;

    public static void main(String[] args) throws IOException {

        String org = "";
        int repo= 0 , commit = 0;
        Runnable runnable = new Runnable(){
            public void run(){
                JFrame frame = new DesignGUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runnable);

    }

}
