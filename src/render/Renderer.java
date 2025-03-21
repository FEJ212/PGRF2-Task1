package render;

import model.Part;
import model.Vertex;
import rasterize.LineRasterizer;
import rasterize.TriangleRasterizer;
import solid.Solid;
import transforms.*;
import utils.Lerp;
import view.Panel;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private LineRasterizer lineRasterizer;
    private TriangleRasterizer triangleRasterizer;
    private Mat4 view, projection;
    private Panel panel;
    private Lerp lerp;
    //konstruktor
    public Renderer(LineRasterizer lineRasterizer, TriangleRasterizer triangleRasterizer, Panel panel) {
        this.lineRasterizer = lineRasterizer;
        this.triangleRasterizer = triangleRasterizer;
        this.panel = panel;
        this.view = new Mat4Identity();
        this.projection = new Mat4Identity();
        lerp = new Lerp();
    }
    //metoda pro vykreslení jednoho objektu
    public void renderSolid(Solid solid) {
        //tranformace vrcholů
        List<Integer> iB = solid.getIndexBuffer();
        List<Vertex> transformovaneVrcholy = new ArrayList<>();
        for (int i = 0; i<solid.getVertexBuffer().size(); i++) {
            Vertex transformovano = new Vertex(solid.getVertexBuffer().get(i).getPosition().mul(solid.getModel()).mul(view).mul(projection), solid.getVertexBuffer().get(i).getColor());
            transformovaneVrcholy.add(transformovano);
        }
        //Vykreslení všech partů z Part Bufferu
        for (Part part : solid.getPartBuffer()) {
            switch (part.getType()) {
                //vykreslení pro čáry
                case LINES:
                    int lineStart = part.getStart();
                    for (int i=0; i< part.getCount(); i++){
                        //Získání vrcholů čáry
                        Vertex a = transformovaneVrcholy.get(iB.get(lineStart));
                        Vertex b = transformovaneVrcholy.get(iB.get(lineStart+1));
                        lineStart+=2;
                        //Transformace vrcholů do okna
                        transformaceCaryDoOkna(a,b);
                    }
                    break;
                //vykreslení pro trojúhelníky
                case TRIANGLES:
                    int triangleStart = part.getStart();
                    for(int i = 0; i < part.getCount(); i++){
                        //Získání vertexů trojúhelníku
                        Vertex a = transformovaneVrcholy.get(iB.get(triangleStart));
                        Vertex b = transformovaneVrcholy.get(iB.get(triangleStart+1));
                        Vertex c = transformovaneVrcholy.get(iB.get(triangleStart+2));
                        triangleStart += 3;
                        //Metoda pro oříznutí trojúhelníku
                        clipTriangle(a, b, c);
                    }
                    break;
                //vykreslení primitiva FAN
                case FAN:
                    int fanStart = part.getStart()+1;
                    for(int i=0; i<part.getCount(); i++){
                        //Získání vertexů trojúhelníku
                        Vertex a = transformovaneVrcholy.get(iB.get(part.getStart()));
                        Vertex b = transformovaneVrcholy.get(iB.get(fanStart));
                        Vertex c = transformovaneVrcholy.get(iB.get(fanStart+1));
                        fanStart += 2;
                        //Metoda pro oříznutí trojúhelníku
                        clipTriangle(a, b, c);
                    }
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
        //Nastavení limitu vykreslování do dálky
        float zMin = 0.0F;
        //ořezávání podle Z
        if(b.getPosition().getZ() < zMin) { //varianta 2 bodů mimo prostor
            //lineární interpolace
            double t1 = (0 - a.getPosition().getZ()) / (b.getPosition().getZ() - a.getPosition().getZ());
            Vertex ab = (Vertex)lerp.lerp(b,a,t1);

            double t2 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex ac = (Vertex)lerp.lerp(c,a,t2);
            //transformování vrcholů do obrazovky
            transformaceTrojuhelnikuDoOkna(a, ab, ac);
        }

        if(c.getPosition().getZ() < zMin) { //1 pod mimo prostor
            //lineární interpolace
            double t1 = -a.getPosition().getZ() / (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex ac = (Vertex)lerp.lerp(c,a,t1);

            double t2 = -b.getPosition().getZ() / (c.getPosition().getZ() - b.getPosition().getZ());
            Vertex bc = (Vertex)lerp.lerp(c,b,t2);
            //transformování vrcholů do obrazovky
            transformaceTrojuhelnikuDoOkna(a, b, bc);
            transformaceTrojuhelnikuDoOkna(a, ac, bc);
        }else { //všechny body v prostoru
            //transformování vrcholů do obrazovky
            transformaceTrojuhelnikuDoOkna(a, b, c);
        }
    }
    //Metoda pro dehomogenizaci
    private Vertex dehomogenizace(Vertex a){
        double w = a.getPosition().getW();
        if(w>0){
            a = a.mul(1/w);
        }
        return a;
    }
    //Metoda pro transformaci pozice do okna
    private Vec3D transformaceDoOkna(Point3D vec) {
        return new Vec3D(vec)
                .mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0))
                .mul(new Vec3D(panel.getWidth() / 2f, panel.getHeight() / 2f, 1));
    }
    //Metoda pro dehomogenizaci a transformaci trojúhelníků
    private void transformaceTrojuhelnikuDoOkna(Vertex aOriginal, Vertex bOriginal, Vertex cOriginal){
        //Dehomogenizace
        Vertex a = dehomogenizace(aOriginal);
        Vertex b = dehomogenizace(bOriginal);
        Vertex c = dehomogenizace(cOriginal);
        //Transformování vrcholů do okna
        Vec3D vecA = transformaceDoOkna(a.getPosition());
        Vec3D vecB = transformaceDoOkna(b.getPosition());
        Vec3D vecC = transformaceDoOkna(c.getPosition());
        //Přidání ostatních atributů zpět do vertexu
        Vertex aNew = new Vertex(new Point3D(vecA), a.getColor(), a.getUV(), a.getOne());
        Vertex bNew = new Vertex(new Point3D(vecB), b.getColor(), b.getUV(), b.getOne());
        Vertex cNew = new Vertex(new Point3D(vecC), c.getColor(), c.getUV(), c.getOne());
        //Projekční korekce pomocí atributu one
        Vertex aDone = projekcniKorekce(aNew);
        Vertex bDone = projekcniKorekce(bNew);
        Vertex cDone = projekcniKorekce(cNew);
        //Rasterizace trojúhelníku
        triangleRasterizer.rasterize(aDone,bDone,cDone);
    }
    //Metoda pro dehomogenizaci a transformaci čar
    private void transformaceCaryDoOkna(Vertex aOriginal, Vertex bOriginal){
        //dehomogenizace
        Vertex a = dehomogenizace(aOriginal);
        Vertex b = dehomogenizace(bOriginal);
        //Transformování vrcholů do okna
        Vec3D vecA = transformaceDoOkna(a.getPosition());
        Vec3D vecB = transformaceDoOkna(b.getPosition());
        //Přidání ostatních atributů zpět do vertexu
        Vertex aNew = new Vertex(new Point3D(vecA), a.getColor(), a.getUV(), a.getOne());
        Vertex bNew = new Vertex(new Point3D(vecB), b.getColor(), b.getUV(), b.getOne());
        //Projekční korekce pomocí atributu one
        Vertex aDone = projekcniKorekce(aNew);
        Vertex bDone = projekcniKorekce(bNew);
        //Rasterizace čáry
        lineRasterizer.drawLine(aDone,bDone);
    }
    //Metoda pro projekční korekci
    private Vertex projekcniKorekce(Vertex a){
        return new Vertex(a.getPosition(),a.getColor().mul(1/a.getOne()),a.getUV().mul(1/a.getOne()));
    }
    //Cyklus pro vygenerování všech objektů v ArrayListu Solids
    public void renderSolids(List<Solid> solids){
        for (Solid solid : solids) {
            renderSolid(solid);
        }
    }
    //nastavení matice view pro umožnění pohybu kamery
    public void setView(Mat4 view) {
        this.view = view;
    }
    //nastavení matice projection pro přepínání typů projekce
    public void setProj(Mat4 projection) {
        this.projection = projection;
    }
}
