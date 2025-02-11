package raster;

public class DepthBuffer implements Raster<Double>{
    private final double[][] buffer;
    private final int width, height;

    public DepthBuffer(int width, int height) {
        this.buffer=new double[width][height];
        clear();
        this.width = width;
        this.height = height;
    }

    @Override
    public void setValue(int x, int y, Double value) {
        buffer[x][y]=value;
    }

    @Override
    public Double getValue(int x, int y) {
        return buffer[x][y];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void clear() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                buffer[i][j]=1;
            }
        }
    }
}
