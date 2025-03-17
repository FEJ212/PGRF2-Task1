package raster;

import transforms.Col;

public class ZBuffer {
    private final Raster<Col> imageBuffer;
    private final Raster<Double> depthBuffer;

    public ZBuffer(Raster<Col> imageBuffer) {
        this.imageBuffer = imageBuffer;
        this.depthBuffer = new DepthBuffer(imageBuffer.getWidth(), imageBuffer.getHeight());
    }
    public void setPixelWithZTest(int x, int y, double z, Col color){
        Double zValue = depthBuffer.getValue(x,y);
        if(zValue != null) {
            if (z < zValue) {
                depthBuffer.setValue(x, y, z);
                imageBuffer.setValue(x, y, color);
            }
        }
    }
    public void clear(){
        depthBuffer.clear();
    }
}
