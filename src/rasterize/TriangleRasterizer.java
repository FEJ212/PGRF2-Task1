package rasterize;

import model.Line;
import model.Vertex;
import raster.ZBuffer;
import transforms.Col;
import transforms.Point3D;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;
    private final LineRasterizer lineRasterizer;

    public TriangleRasterizer(ZBuffer zBuffer, LineRasterizer lineRasterizer) {
        this.zBuffer = zBuffer;
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Vertex a, Vertex b, Vertex c){
        lineRasterizer.drawLine(new Line((int)a.getPosition().getX(), (int)a.getPosition().getY(), (int)b.getPosition().getX(), (int)b.getPosition().getY()));
        lineRasterizer.drawLine(new Line((int)b.getPosition().getX(), (int)b.getPosition().getY(), (int)c.getPosition().getX(), (int)c.getPosition().getY()));
        lineRasterizer.drawLine(new Line((int)a.getPosition().getX(), (int)a.getPosition().getY(), (int)c.getPosition().getX(), (int)c.getPosition().getY()));

        //TODO: Transformace do okna obrazovky

        int xA=(int)Math.round(a.getPosition().getX());
        int yA=(int)Math.round(a.getPosition().getY());
        double zA= a.getPosition().getZ();
        Col colA = a.getColor();

        int xB=(int)Math.round(b.getPosition().getX());
        int yB=(int)Math.round(b.getPosition().getY());
        double zB= b.getPosition().getZ();
        Col colB = b.getColor();

        int xC=(int)Math.round(c.getPosition().getX());
        int yC=(int)Math.round(c.getPosition().getY());
        double zC= c.getPosition().getZ();
        Col colC = c.getColor();

        if(yA>yB){
            int tempX = xA;
            int tempY = yA;
            double tempZ = zA;
            Col tempCol = colA;
            xA = xB;
            yA = yB;
            zA = zB;
            colA = colB;
            xB = tempX;
            yB = tempY;
            zB = tempZ;
            colB = tempCol;
        }
        if(yB>yC){
            int tempX = xB;
            int tempY = yB;
            double tempZ = zB;
            Col tempCol = colB;
            xB = xC;
            yB = yC;
            zB = zC;
            colB = colC;
            xC = tempX;
            yC = tempY;
            zC = tempZ;
            colC = tempCol;
        }

        for(int y=yA; y<=yB; y++){
            double tAB = (y-yA)/(double)(yB-yA);
            int xAB = (int)Math.round((1-tAB)*xA+tAB*xB);
            double zAB = (1-tAB)*zA+tAB*zB;

            double tAC = (y-yA)/(double)(yC-yA);
            int xAC = (int)Math.round((1-tAC)*xA+tAC*xC);
            double zAC = (1-tAC)*zA+tAC*zC;

            if (xAB>xAC){
                int tempX = xAB;
                double tempZ = zAB;
                xAB = xAC;
                zAB = zAC;
                xAC = tempX;
                zAC = tempZ;
            }

            for(int x=xAB; x<=xAC; x++){
                double tZ = (x-xAC)/(zAB-zAC);
                double z = (1-tZ)*zA+tZ*zB;
                zBuffer.setPixelWithZTest(x,y,z,colA);
            }
        }
        for(int y=yB; y<=yC; y++){
            double tBC = (y-yB)/(double)(yC-yB);
            int xBC = (int)Math.round((1-tBC)*xB+tBC*xC);
            double zBC = (1-tBC)*zB+tBC*zC;

            double tAC = (y-yA)/(double)(yC-yA);
            int xAC = (int)Math.round((1-tAC)*xA+tAC*xC);
            double zAC = (1-tAC)*zA+tAC*zC;

            if (xBC>xAC){
                int tempX = xBC;
                double tempZ = zBC;
                xBC = xAC;
                zBC = zAC;
                xAC = tempX;
                zAC = tempZ;
            }

            for(int x=xBC; x<=xAC; x++){
                double tZ = (x-xAC)/(zBC-zAC);
                double z = ((1-tZ)*zA+tZ*zB);
                zBuffer.setPixelWithZTest(x,y,z,colA);
            }
        }

    }
}
