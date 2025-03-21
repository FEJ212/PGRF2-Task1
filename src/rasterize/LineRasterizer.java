package rasterize;

import model.Line;
import model.Vertex;
import raster.Raster;
import raster.ZBuffer;
import transforms.Col;


public abstract class LineRasterizer {
    protected final ZBuffer zBuffer;
    protected Col color;
    //konstruktor bez specifikace barvy
    public LineRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
        this.color = new Col(0xffffff);
    }
    //konstruktor se specifikací barvy
    public LineRasterizer(ZBuffer zBuffer, Col color) {
        this.zBuffer = zBuffer;
        this.color = color;
    }
    //vykreslení čáry pomocí třídy Line (2 body)
    public void drawLine(Line line) {
    }
    //vykreslení čáry pomocí 2 vertexů
    public void drawLine(Vertex a, Vertex b) {
    }
    //nastavení barvy
    public void setColor(Col color) {
        this.color = color;
    }
}
