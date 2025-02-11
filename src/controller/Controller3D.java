package controller;

import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller3D {
    private final Panel panel;


    public Controller3D(Panel panel) {
        this.panel = panel;

        initListeners();

        redraw();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
    }

    private void redraw() {
        panel.clear();



        panel.repaint();
    }

}
