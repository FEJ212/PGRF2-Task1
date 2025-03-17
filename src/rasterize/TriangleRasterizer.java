package rasterize;

import model.Line;
import model.Vertex;
import raster.ZBuffer;
import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;
import view.Panel;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;
    private final Panel panel;
    private final LineRasterizer lineRasterizer;
    private Mat4 model, view, projection;
    private Col color;

    public TriangleRasterizer(ZBuffer zBuffer, LineRasterizer lineRasterizer,Panel panel) {
        this.zBuffer = zBuffer;
        this.lineRasterizer = lineRasterizer;
        this.panel = panel;
    }

    public void rasterize(Vertex aOriginal, Vertex bOriginal, Vertex cOriginal) {
        //TODO: dehomogenizace
        Vertex aDehom = aOriginal;
        Vertex bDehom = bOriginal;
        Vertex cDehom = cOriginal;
        //Transformace do okna
        Vec3D vecA = transformaceDoOkna(aDehom.getPosition());
        Vertex a = new Vertex(new Point3D(vecA), aDehom.getColor());

        Vec3D vecB = transformaceDoOkna(bDehom.getPosition());
        Vertex b = new Vertex(new Point3D(vecB), bDehom.getColor());

        Vec3D vecC = transformaceDoOkna(cDehom.getPosition());
        Vertex c = new Vertex(new Point3D(vecC), cDehom.getColor());
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

        int xB = (int) Math.round(b.getPosition().getX());
        int yB = (int) Math.round(b.getPosition().getY());

        int xC = (int) Math.round(c.getPosition().getX());
        int yC = (int) Math.round(c.getPosition().getY());


        // První část trojúhelníku
        // TODO: ořezání
        for (int y = yA; y <= yB; y++) {
            double tAB = (y - yA) / (double) (yB - yA);
            int xAB = (int) Math.round((1 - tAB) * xA + tAB * xB);
            // TODO: při interpolaci používat lerp, všude v projektu
            // TODO: instanci lerp nechceme tady
            // Lerp<Vertex> lerp = new Lerp<>();
            // Vertex ab = lerp.lerp(a, b, tAB);
            // Vertex ab = a.mul(1 - tAB).add(b.mul(tAB));


            double tAC = (y - yA) / (double) (yC - yA);
            int xAC = (int) Math.round((1 - tAC) * xA + tAC * xC);

            // for cyklus od x do x
            // TODO: pozor xAC může být menší než xAB
            // TODO: ořezání
            for (int x = xAB; x <= xAC; x++) {
                // TODo: dopočítat z
                zBuffer.setPixelWithZTest(x, y, 0.5, color);
            }

            // TODO: udělat druhu část trojúhelníku


        }
    }
    private Vec3D transformaceDoOkna(Point3D vec) {
        return new Vec3D(vec)
                .mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(panel.getWidth() / 2f, panel.getHeight() / 2f, 1));
    }
}
