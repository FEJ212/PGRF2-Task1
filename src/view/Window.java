package view;

import javax.swing.*;

public class Window extends JFrame {

    private final Panel panel;
    //Okno programu
    public Window(int width, int heigth) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("PGRF2 2024/2025");
        setVisible(true);
        //implementace panelu
        panel = new Panel(width, heigth);
        add(panel);
        pack();
        //možnost program ovládat
        panel.setFocusable(true);
        panel.grabFocus();
    }

    public Panel getPanel() {
        return panel;
    }
}
