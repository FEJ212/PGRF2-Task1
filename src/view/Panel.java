package view;

import raster.Raster;
import raster.ImageBuffer;
import transforms.Col;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private final Raster<Col> raster;
    //nastavení panelu
    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        raster = new ImageBuffer(width, height);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((ImageBuffer)raster).paint(g);
    }
    //vyčištění rasteru
    public void clear() {
        raster.clear();
    }
    //getter
    public Raster<Col> getRaster() {
        return raster;
    }
}
