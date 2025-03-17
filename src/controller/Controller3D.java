package controller;

import model.TopologyType;
import model.Vertex;
import raster.ZBuffer;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.TriangleRasterizer;
import render.Renderer;
import solid.Arrow;
import solid.Axis;
import solid.Solid;
import transforms.*;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Controller3D {
    private final Panel panel;
    private final ZBuffer zBuffer;
    private final TriangleRasterizer triangleRasterizer;
    private final LineRasterizer lineRasterizer;
    private final Renderer renderer;
    private Mat4 perspective, orthogonal, current;
    private ArrayList<Solid> solids;
    private Axis axis;
    private Mat4 model, projection;
    private Camera camera;
    private Solid arrow;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.zBuffer = new ZBuffer(panel.getRaster());
        // TODO: pozor, posílá se tam raster místo zbufferu
        this.lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
        this.triangleRasterizer = new TriangleRasterizer(zBuffer, lineRasterizer, panel);;
        this.renderer = new Renderer(lineRasterizer, triangleRasterizer, panel);

        initListeners();
        initScene();

        redraw();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
    }

    public void initScene() {
        camera = new Camera(new Vec3D(0.5,-5,2.3),Math.toRadians(90),Math.toRadians(-15),10,true);
        perspective = new Mat4PerspRH(Math.PI/4,panel.getHeight()/(float)panel.getWidth(),0.1,20.);
        orthogonal = new Mat4OrthoRH((float)panel.getWidth()/100,(float)panel.getHeight()/100,0.1,20.);

        axis = new Axis();
        arrow = new Arrow();

        current = perspective;
        solids = new ArrayList<>();
    }

    private void redraw() {
        panel.clear();
        renderer.setView(camera.getViewMatrix());
        renderer.setProj(current);
        renderer.renderSolid(new Arrow());
        renderer.renderSolid(axis);

//        triangleRasterizer.rasterize(
//                new Vertex(new Point3D(400, 0, 0.5)),
//                new Vertex(new Point3D(0, 300, 0.5)),
//                new Vertex(new Point3D(799, 300, 0.5)),
//                new Col(0x00ff00)
//        );
//
//        triangleRasterizer.rasterize(
//                new Vertex(new Point3D(400, 0, 0.3)),
//                new Vertex(new Point3D(0, 300, 0.6)),
//                new Vertex(new Point3D(799, 300, 0.6)),
//                new Col(0xff0000)
//        );

        panel.repaint();
    }

}
