package raster;

import transforms.Col;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBuffer implements Raster<Col> {

    private final BufferedImage image;
    //konstruktor
    public ImageBuffer(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    //překreslení specifického pixelu
    @Override
    public void setValue(int x, int y, Col value) {
        if(isInRaster(x, y)) {
            image.setRGB(x, y, value.getRGB());
        }
    }
    //získání barvy specifického pixelu
    @Override
    public Col getValue(int x, int y) {
        if(isInRaster(x, y)) {
            return new Col(image.getRGB(x, y)&0xffffff00); //vymazání alpha kanálu
        }
        return null;
    }
    //gettery
    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }
    //vyčištění obrázku
    @Override
    public void clear() {
        Graphics g = image.getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }
    //test, zda je pixel v "okně"
    @Override
    public boolean isInRaster(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return true;
        } else return false;
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public Graphics getGraphics() {
        return image.getGraphics();
    }
}
