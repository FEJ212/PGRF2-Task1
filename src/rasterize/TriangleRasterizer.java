package rasterize;

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

    public TriangleRasterizer(ZBuffer zBuffer, LineRasterizer lineRasterizer,Panel panel) {
        this.zBuffer = zBuffer;
        this.lineRasterizer = lineRasterizer;
        this.panel = panel;
    }

    public void prepare(Vertex aOriginal, Vertex bOriginal, Vertex cOriginal){
        //transformace
        Vertex a = new Vertex(aOriginal.getPosition().mul(model).mul(view).mul(projection), aOriginal.getColor());
        Vertex b = new Vertex(bOriginal.getPosition().mul(model).mul(view).mul(projection), bOriginal.getColor());
        Vertex c = new Vertex(cOriginal.getPosition().mul(model).mul(view).mul(projection), cOriginal.getColor());
        //ořezání vertexů plně mimo raster
        if (a.getX() > a.getW() && b.getX() > b.getW() && c.getX() > c.getW()) return;
        if (a.getX() < -a.getW() && b.getX() < -b.getW() && c.getX() < -c.getW()) return;
        if (a.getY() > a.getW() && b.getY() > b.getW() && c.getY() > c.getW()) return;
        if (a.getY() < -a.getW() && b.getY() < -b.getW() && c.getY() < -c.getW()) return;
        if (a.getZ() > a.getW() && b.getZ() > b.getW() && c.getZ() > c.getW()) return;
        if (a.getZ() < 0 && b.getZ() < 0 && c.getZ() < 0) return;
        //seřazení vrcholů dle Z pro Z ořezání
        if (a.getZ() < b.getZ()) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.getZ() < c.getZ()) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.getZ() < b.getZ()) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        //ořezání dle Z
        if(b.getZ()<0){
            double t1 = (0 - a.getZ()) / (b.getZ() - a.getZ());
            Vertex ab = a.mul(1 - t1).add(b.mul(t1));

            double t2 = -a.getZ() / (c.getZ() - a.getZ());
            Vertex ac = a.mul(1 - t2).add(c.mul(t2));

            rasterize(a, ab, ac);
        } else if (c.getZ() < 0) {
            double t1 = -a.getZ() / (c.getZ() - a.getZ());
            Vertex ac = a.mul(1 - t1).add(c.mul(t1));

            double t2 = -b.getZ() / (c.getZ() - b.getZ());
            Vertex bc = b.mul(1 - t2).add(c.mul(t2));

            rasterize(a, b, bc);
            rasterize(a, ac, bc);
        } else {
            rasterize(a, b, c);
        }

    }

    public void rasterize(Vertex aOriginal, Vertex bOriginal, Vertex cOriginal){
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


    }
    private Vec3D transformaceDoOkna(Point3D vec) {
        return new Vec3D(vec)
                .mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(panel.getWidth() / 2f, panel.getHeight() / 2f, 1));
    }
}
