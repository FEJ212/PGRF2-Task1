package rasterize;

import model.Line;
import model.Vertex;
import raster.Raster;
import raster.ImageBuffer;

import java.awt.*;

public class LineRasterizerGraphics extends LineRasterizer {

    public LineRasterizerGraphics(Raster raster) {
        super(raster);
    }

    public LineRasterizerGraphics(Raster raster, int color) {
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {
        Graphics g = ((ImageBuffer)raster).getGraphics();
        g.setColor(new Color(color));
        g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }
    public void drawLine(Vertex a, Vertex b){
        color = a.getColor().getRGB();
        drawLine(new Line((int)Math.round(a.getPosition().getX()),(int)Math.round(a.getPosition().getY()),(int)Math.round(b.getPosition().getX()),(int)Math.round(a.getPosition().getY())));
        System.out.println(color);
    }

}
