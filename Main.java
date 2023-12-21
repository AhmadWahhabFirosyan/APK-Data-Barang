import elektronik.Mainscreen;
import elektronik.Registration;
import elektronik.login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Registration();

                new login();
                new Mainscreen();
            }
        });
    }
}