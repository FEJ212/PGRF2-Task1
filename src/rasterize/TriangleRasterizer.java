package rasterize;

import model.Line;
import model.Vertex;
import raster.ZBuffer;
import shader.Shader;
import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;
import utils.Lerp;
import view.Panel;

import java.awt.image.BufferedImage;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;
    private Lerp<Vertex> lerp = new Lerp<>();
    private int maxWidth, maxHeight;
    private Shader shader;
    //TODO: opravit one za transformaci do obrazovky
    public TriangleRasterizer(ZBuffer zBuffer, Shader shader) {
        this.zBuffer = zBuffer;
        this.maxWidth = zBuffer.getWidth();
        this.maxHeight = zBuffer.getHeight();
        this.shader = shader;
    }

    public void rasterize(Vertex a, Vertex b, Vertex c) {
        //seřazení podle Y
        if (a.getPosition().getY() > b.getPosition().getY()) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.getPosition().getY() > c.getPosition().getY()) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.getPosition().getY() > b.getPosition().getY()) {
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
        for (int y = Math.max(yA, 1); y <= Math.min(yB, maxHeight); y++) {
            double tAB = (y - yA) / (double) (yB - yA);
            Vertex ab = lerp.lerp(a, b, tAB);

            double tAC = (y - yA) / (double) (yC - yA);
            Vertex ac = lerp.lerp(a, c, tAC);

            if (ab.getPosition().getX() > ac.getPosition().getX()) {
                Vertex temp = ac;
                ac = ab;
                ab = temp;
            }
            // for cyklus od x do x
            interpolaceX(ab, ac, y);
        }
        //druhá část trojúhelníku
        for (int y = Math.max(yB, 1); y <= Math.min(yC, maxHeight); y++) {
            double tBC = (y - yB) / (double) (yC - yB);
            Vertex bc = lerp.lerp(b, c, tBC);

            double tAC = (y - yA) / (double) (yC - yA);
            Vertex ac = lerp.lerp(a, c, tAC);

            if (bc.getPosition().getX() > ac.getPosition().getX()) {
                Vertex temp = ac;
                ac = bc;
                bc = temp;
            }
            // for cyklus od x do x
            interpolaceX(bc, ac, y);
        }
    }
    private void interpolaceX(Vertex a, Vertex b, int y){
        int x1 = (int) Math.round(a.getPosition().getX());
        int x2 = (int) Math.round(b.getPosition().getX());
        for (int x = Math.max(x1,1); x <= Math.min(x2,maxWidth); x++) {
            double tX = (x - x1) / (double) (x2 - x1);
            Vertex ac = (Vertex) lerp.lerp(a, b, tX);
            zBuffer.setPixelWithZTest(x, y, ac.getPosition().getZ(), ac.getColor());
        }

    }
    public void setShader(Shader shader){
        this.shader = shader;
    }
}
