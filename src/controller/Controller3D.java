package controller;

import model.TopologyType;
import model.Vertex;
import raster.ZBuffer;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.TriangleRasterizer;
import render.Renderer;
import solid.Arrow;
import transforms.Col;
import transforms.Point3D;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller3D {
    private final Panel panel;
    private final ZBuffer zBuffer;
    private final TriangleRasterizer triangleRasterizer;
    private final LineRasterizer lineRasterizer;
    private final Renderer renderer;


    public Controller3D(Panel panel) {
        this.panel = panel;
        this.zBuffer = new ZBuffer(panel.getRaster());
        // TODO: pozor, posílá se tam raster místo zbufferu
        this.lineRasterizer = new LineRasterizerGraphics(panel.getRaster());
        this.triangleRasterizer = new TriangleRasterizer(zBuffer, lineRasterizer, panel);;
        this.renderer = new Renderer(lineRasterizer, triangleRasterizer);

        initListeners();

        redraw();
    }

    private void initListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
    }

    private void redraw() {
        panel.clear();

        renderer.renderSolid(new Arrow());

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
