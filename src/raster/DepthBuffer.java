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
        if(isInRaster(x, y)) {
            buffer[x][y] = value;
        }
    }

    @Override
    public Double getValue(int x, int y) {
        if(isInRaster(x, y)) {
            return buffer[x][y];
        } else return null;
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

    @Override
    public boolean isInRaster(int x, int y) {
        if (x>=0 && x<width && y>=0 && y<height) {
            return true;
        } else return false;
    }
}
