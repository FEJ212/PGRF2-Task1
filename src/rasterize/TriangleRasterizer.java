package rasterize;

import model.Vertex;
import raster.ZBuffer;
import shader.Shader;
import transforms.Col;
import utils.Lerp;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;
    private Lerp<Vertex> lerp = new Lerp<>();
    private int maxWidth, maxHeight;
    private Shader shader;
    //konstruktor
    public TriangleRasterizer(ZBuffer zBuffer, Shader shader) {
        this.zBuffer = zBuffer;
        this.maxWidth = zBuffer.getWidth();
        this.maxHeight = zBuffer.getHeight();
        this.shader = shader;
    }
    //proces rasterizace
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
        //získání hodnot y pro počáteční interpolaci
        int yA = (int) Math.round(a.getPosition().getY());
        int yB = (int) Math.round(b.getPosition().getY());
        int yC = (int) Math.round(c.getPosition().getY());

        // První část trojúhelníku
        for (int y = Math.max(yA, 1); y <= Math.min(yB, maxHeight); y++) {
            //Lineární interpolace
            double tAB = (y - yA) / (double) (yB - yA);
            Vertex ab = lerp.lerp(a, b, tAB);

            double tAC = (y - yA) / (double) (yC - yA);
            Vertex ac = lerp.lerp(a, c, tAC);
            //seřazení podle X
            if (ab.getPosition().getX() > ac.getPosition().getX()) {
                Vertex temp = ac;
                ac = ab;
                ab = temp;
            }
            // for cyklus od x1 do x2
            interpolaceX(ab, ac, y);
        }
        //druhá část trojúhelníku
        for (int y = Math.max(yB, 1); y <= Math.min(yC, maxHeight); y++) {
            //lineární interpolace
            double tBC = (y - yB) / (double) (yC - yB);
            Vertex bc = lerp.lerp(b, c, tBC);

            double tAC = (y - yA) / (double) (yC - yA);
            Vertex ac = lerp.lerp(a, c, tAC);
            //seřazení podle X
            if (bc.getPosition().getX() > ac.getPosition().getX()) {
                Vertex temp = ac;
                ac = bc;
                bc = temp;
            }
            // for cyklus od x1 do x2
            interpolaceX(bc, ac, y);
        }
    }
    //Funkce pro intepolaci od x1 do x2 (aby nebyla zbytečně psaná dvakrát)
    private void interpolaceX(Vertex a, Vertex b, int y){
        //získání hodnot x
        int x1 = (int) Math.round(a.getPosition().getX());
        int x2 = (int) Math.round(b.getPosition().getX());
        //cyklus od x1 do x2
        for (int x = Math.max(x1,1); x <= Math.min(x2,maxWidth); x++) {
            //lineární interpolace
            double tX = (x - x1) / (double) (x2 - x1);
            Vertex ac = (Vertex) lerp.lerp(a, b, tX);
            //vykreslení se zkontolováním ZBufferu
            zBuffer.setPixelWithZTest(x, y, ac.getPosition().getZ(), shader.getColor(ac));
        }
    }
    //setter shaderu
    public void setShader(Shader shader){
        this.shader = shader;
    }
}
