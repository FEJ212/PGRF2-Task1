package render;

import model.Part;
import model.Vertex;
import rasterize.LineRasterizer;
import rasterize.TriangleRasterizer;
import solid.Solid;
import transforms.*;
import view.Panel;

import java.util.List;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private TriangleRasterizer triangleRasterizer;
    private Mat4 view, projection;
    private Panel panel;

    // TODO: view a proj matice

    public Renderer(LineRasterizer lineRasterizer, TriangleRasterizer triangleRasterizer, Panel panel) {
        this.lineRasterizer = lineRasterizer;
        this.triangleRasterizer = triangleRasterizer;
        this.panel = panel;
    }

    public void renderSolid(Solid solid) {
        //tranformace vrcholů
        for (Vertex v : solid.getVertexBuffer()) {
            v.getPosition().mul(solid.getModel()).mul(view).mul(projection);
        }

        for (Part part : solid.getPartBuffer()) {
            switch (part.getType()) {
                case LINES:
                    int lineStart = part.getStart();
                    for (int i=0; i< part.getCount(); i++){
                        int indexA  = lineStart + i;
                        int indexB = lineStart + i + 1;
                        lineStart+=2;
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        //TODO: ořezání
                        transformaceCaryDoOkna(a,b);
                    }
                    break;
                case TRIANGLES:
                    int start = part.getStart();
                    for(int i = 0; i < part.getCount(); i++){
                        int indexA = start;
                        int indexB = start + 1;
                        int indexC = start + 2;
                        start += 3;

                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexC));

                        clipTriangle(a, b, c);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void clipTriangle(Vertex a, Vertex b, Vertex c) {
        //fast clip
        if (a.getPosition().getX() > a.getPosition().getW() && b.getPosition().getX() > b.getPosition().getW() && c.getPosition().getX() > c.getPosition().getW()) return;
        if (a.getPosition().getX() < -a.getPosition().getW() && b.getPosition().getX() < -b.getPosition().getW() && c.getPosition().getX() < -c.getPosition().getW()) return;
        if (a.getPosition().getY() > a.getPosition().getW() && b.getPosition().getY() > b.getPosition().getW() && c.getPosition().getY() > c.getPosition().getW()) return;
        if (a.getPosition().getY() < -a.getPosition().getW() && b.getPosition().getY() < -b.getPosition().getW() && c.getPosition().getY() < -c.getPosition().getW()) return;
        if (a.getPosition().getZ() > a.getPosition().getW() && b.getPosition().getZ() > b.getPosition().getW() && c.getPosition().getZ() > c.getPosition().getW()) return;
        if (a.getPosition().getZ() < 0 && b.getPosition().getZ() < 0 && c.getPosition().getZ() < 0) return;
        // 1. seřadit vrcholy pod z od max po min. A = max
        if (a.getPosition().getZ() < b.getPosition().getZ()) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.getPosition().getZ() < c.getPosition().getZ()) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.getPosition().getZ() < b.getPosition().getZ()) {
            Vertex temp = a;
            a = b;
            b = temp;
        }

        float zMin = 0;

        if(b.getPosition().getZ() < zMin) {
            double t1 = (0 - a.getPosition().getZ()) / (b.getPosition().getZ() - a.getPosition().getZ());
            Vertex ab = a.mul(1 - t1).add(b.mul(t1));

            double t2 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex ac = a.mul(1 - t2).add(c.mul(t2));

            transformaceTrojuhelnikuDoOkna(a, ab, ac);
        }

        if(c.getPosition().getZ() < zMin) {
            double t1 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex ac = a.mul(1 - t1).add(c.mul(t1));

            double t2 = -b.getPosition().getZ() / (c.getPosition().getZ() - b.getPosition().getZ());
            Vertex bc = b.mul(1 - t2).add(c.mul(t2));

            transformaceTrojuhelnikuDoOkna(a, b, bc);
            transformaceTrojuhelnikuDoOkna(a, ac, bc);
        }else {
            transformaceTrojuhelnikuDoOkna(a, b, c);
        }
    }
    private Vec3D transformaceDoOkna(Point3D vec) {
        return new Vec3D(vec)
                .mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(panel.getWidth() / 2f, panel.getHeight() / 2f, 1));
    }
    private void transformaceTrojuhelnikuDoOkna(Vertex a, Vertex b, Vertex c){
        //TODO: dehomogenizace
        Vec3D vecA = transformaceDoOkna(a.getPosition());
        Vertex aDone = new Vertex(new Point3D(vecA), a.getColor());

        Vec3D vecB = transformaceDoOkna(b.getPosition());
        Vertex bDone = new Vertex(new Point3D(vecB), b.getColor());

        Vec3D vecC = transformaceDoOkna(c.getPosition());
        Vertex cDone = new Vertex(new Point3D(vecC), c.getColor());

        triangleRasterizer.rasterize(aDone,bDone,cDone);
    }
    private void transformaceCaryDoOkna(Vertex a, Vertex b){
        //TODO: dehomogenizace
        Vec3D vecA = transformaceDoOkna(a.getPosition());
        Vertex aDone = new Vertex(new Point3D(vecA), a.getColor());

        Vec3D vecB = transformaceDoOkna(b.getPosition());
        Vertex bDone = new Vertex(new Point3D(vecB), b.getColor());

        lineRasterizer.drawLine(aDone,bDone);
    }
    public void renderSolids(List<Solid> solids){
        for (Solid solid : solids) {
            renderSolid(solid);
        }
    }
    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 projection) {
        this.projection = projection;
    }
}
