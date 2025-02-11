package controller;

import raster.ZBuffer;
import transforms.Col;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller3D {
    private final Panel panel;
    private final ZBuffer zBuffer;


    public Controller3D(Panel panel) {
        this.panel = panel;
        this.zBuffer = new ZBuffer(panel.getRaster());

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

        zBuffer.setPixelWithZTest(50, 50, 0.5,  new Col(0xff0000));
        zBuffer.setPixelWithZTest(50, 50, 0.7,  new Col(0x00ff00));


        panel.repaint();
    }

}
