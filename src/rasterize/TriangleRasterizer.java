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

//        if(b.getPosition().getY() < yMin) { //varianta 2 bodů mimo prostor
//            double t1 = (0 - a.getPosition().getZ()) / (b.getPosition().getZ() - a.getPosition().getZ());
//            Vertex ab = a.mul(1 - t1).add(b.mul(t1));
//
//            double t2 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
//            Vertex ac = a.mul(1 - t2).add(c.mul(t2));
//
//            vykreleni(a, ab, ac);
//        }
//
//        if(c.getPosition().getY() < yMin) { //1 pod mimo prostor
//            double t1 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
//            Vertex ac = a.mul(1 - t1).add(c.mul(t1));
//
//            double t2 = -b.getPosition().getZ() / (c.getPosition().getZ() - b.getPosition().getZ());
//            Vertex bc = b.mul(1 - t2).add(c.mul(t2));
//
//            vykreleni(a, b, bc);
//            vykreleni(a, ac, bc);
//        }else { //všechny body v prostoru
//            vykreleni(a, b, c);
//        }

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
    private void vykreleni(Vertex a, Vertex b, Vertex c){
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
    private void interpolaceX(Vertex a, Vertex b, int y){
        for (int x = (int) Math.round(a.getPosition().getX()); x <= b.getPosition().getX(); x++) {
            double tX = (x - a.getPosition().getX()) / (double) (b.getPosition().getX() - a.getPosition().getX());
            Vertex ac = lerp.lerp(a, b, tX);
            zBuffer.setPixelWithZTest(x, y, ac.getZ(), ac.getColor());
        }

    }
}
