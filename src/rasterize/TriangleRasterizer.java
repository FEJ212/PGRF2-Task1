package rasterize;

import model.Line;
import model.Vertex;
import raster.ZBuffer;
import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;
import utils.Lerp;
import view.Panel;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;
    private final Panel panel;
    private final LineRasterizer lineRasterizer;
    private Mat4 model, view, projection;
    private Col color;
    private Lerp<Vertex> lerp = new Lerp<>();

    public TriangleRasterizer(ZBuffer zBuffer, LineRasterizer lineRasterizer,Panel panel) {
        this.zBuffer = zBuffer;
        this.lineRasterizer = lineRasterizer;
        this.panel = panel;
    }

    public void rasterize(Vertex a, Vertex b, Vertex c) {
        //seřazení podle Y
        if (a.getY() > b.getY()) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.getY() > c.getY()) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.getY() > b.getY()) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        //získání vrcholů pro interpolaci
        int xA = (int) Math.round(a.getPosition().getX());
        int yA = (int) Math.round(a.getPosition().getY());
        Col cA = a.getColor();

        int xB = (int) Math.round(b.getPosition().getX());
        int yB = (int) Math.round(b.getPosition().getY());
        Col cB = b.getColor();

        int xC = (int) Math.round(c.getPosition().getX());
        int yC = (int) Math.round(c.getPosition().getY());
        Col cC = c.getColor();

        // První část trojúhelníku
        // TODO: ořezání
        for (int y = yA; y <= yB; y++) {
            double tAB = (y - yA) / (double) (yB - yA);
            Vertex ab = lerp.lerp(a, b, tAB);

            double tAC = (y - yA) / (double) (yC - yA);
            Vertex ac = lerp.lerp(a, c, tAC);

            if(ab.getPosition().getX()>ac.getPosition().getX()) {
                Vertex temp = ac;
                ac = ab;
                ab = temp;
            }

            // TODO: ořezání

            // for cyklus od x do x
            interpolaceX(ab,ac,y);
        }
        //druhá část trojúhelníku
        for (int y = yB; y <= yC; y++) {
            double tBC = (y - yB) / (double) (yC - yB);
            Vertex bc = lerp.lerp(b, c, tBC);

            double tAC = (y - yA) / (double) (yC - yA);
            Vertex ac = lerp.lerp(a, c, tAC);

            if(bc.getPosition().getX()>ac.getPosition().getX()) {
                Vertex temp = ac;
                ac = bc;
                bc = temp;
            }

            // TODO: ořezání

            // for cyklus od x do x
            interpolaceX(bc,ac,y);
        }
    }
    private Vec3D transformaceDoOkna(Point3D vec) {
        return new Vec3D(vec)
                .mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(panel.getWidth() / 2f, panel.getHeight() / 2f, 1));
    }
    private void interpolaceX(Vertex a, Vertex b, int y){
        for (int x = (int) Math.round(a.getPosition().getX()); x <= b.getPosition().getX(); x++) {
            double tX = (x - a.getPosition().getX()) / (double) (b.getPosition().getX() - a.getPosition().getX());
            Vertex ac = lerp.lerp(a, b, tX);
            zBuffer.setPixelWithZTest(x, y, ac.getZ(), ac.getColor());
        }

    }
}
