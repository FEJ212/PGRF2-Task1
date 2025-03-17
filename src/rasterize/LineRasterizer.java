package rasterize;

import model.Line;
import model.Vertex;
import raster.Raster;
import raster.ZBuffer;
import transforms.Col;


public abstract class LineRasterizer {
    protected final ZBuffer zBuffer;
    protected Col color;

    public LineRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
        this.color = new Col(0xffffff);
    }

    public LineRasterizer(ZBuffer zBuffer, Col color) {
        this.zBuffer = zBuffer;
        this.color = color;
    }

    public void drawLine(Line line) {
        System.out.println("Draw line");
    }

    public void drawLine(Vertex a, Vertex b) {

    }

    public void setColor(Col color) {
        this.color = color;
    }
}
