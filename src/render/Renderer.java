package render;

import model.Line;
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
    private Mat4 model, view, projection;
    private Panel panel;

    // TODO: view a proj matice

    public Renderer(LineRasterizer lineRasterizer, TriangleRasterizer triangleRasterizer, Panel panel) {
        this.lineRasterizer = lineRasterizer;
        this.triangleRasterizer = triangleRasterizer;
        this.panel = panel;
    }

    public void renderSolid(Solid solid) {
        // TODO: MVP matice
        //tranformace vrcholů
        for (Vertex v : solid.getVertexBuffer()) {
            v.getPosition().mul(model).mul(view).mul(projection);
        }

        for (Part part : solid.getPartBuffer()) {
            switch (part.getType()) {
                case LINES:
                    //TODO: lines
                    break;
                case TRIANGLES:
                    // TODO: triangles
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

                        //triangleRasterizer.rasterize(a, b, c, new Col(0xff0000));
                    }


                    break;
                // TODO: další primitiva
                default:
                    break;
            }
        }
    }


    // TODo: vymyslet, bude něco vracet nebo rasterizace uvnitř této metody?
    private void clipTriangle(Vertex a, Vertex b, Vertex c) {
        //fast clip
        if (a.getPosition().getX() > a.getPosition().getW() && b.getPosition().getX() > b.getPosition().getW() && c.getPosition().getX() > c.getPosition().getW()) return;
        if (a.getPosition().getX() < -a.getPosition().getW() && b.getPosition().getX() < -b.getPosition().getW() && c.getPosition().getX() < -c.getPosition().getW()) return;
        if (a.getPosition().getY() > a.getPosition().getW() && b.getPosition().getY() > b.getPosition().getW() && c.getPosition().getY() > c.getPosition().getW()) return;
        if (a.getPosition().getY() < -a.getPosition().getW() && b.getPosition().getY() < -b.getPosition().getW() && c.getPosition().getY() < -c.getPosition().getW()) return;
        if (a.getPosition().getZ() > a.getPosition().getW() && b.getPosition().getZ() > b.getPosition().getW() && c.getPosition().getZ() > c.getPosition().getW()) return;
        if (a.getPosition().getZ() < 0 && b.getPosition().getZ() < 0 && c.getPosition().getZ() < 0) return;
        //TODO: zahrnout zMin
        float zMin = 0;
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

        if(b.getPosition().getZ() < zMin) {
            double t1 = (0 - a.getPosition().getZ()) / (b.getPosition().getZ() - a.getPosition().getZ());
            Vertex ab = a.mul(1 - t1).add(b.mul(t1));

            double t2 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex ac = a.mul(1 - t2).add(c.mul(t2));

            triangleRasterizer.rasterize(a, ab, ac);
        }

        if(c.getPosition().getZ() < zMin) {
            double t1 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex ac = a.mul(1 - t1).add(c.mul(t1));

            double t2 = -b.getPosition().getZ() / (c.getPosition().getZ() - b.getPosition().getZ());
            Vertex bc = b.mul(1 - t2).add(c.mul(t2));

            triangleRasterizer.rasterize(a, b, bc);
            triangleRasterizer.rasterize(a, ac, bc);
        }else {
            triangleRasterizer.rasterize(a, b, c);
        }
    }
    private Vec3D transformToScreen(Vec3D point){
        return point.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((panel.getWidth()-1)/2., (panel.getHeight()-1)/2., 1));
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
