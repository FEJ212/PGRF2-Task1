package controller;

import raster.ZBuffer;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerTrivial;
import rasterize.TriangleRasterizer;
import render.Renderer;
import shader.Shader;
import shader.ShaderTexture;
import solid.*;
import transforms.*;
import view.Panel;

import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private Camera camera;
    private Solid arrow, cube, pyramid;
    private int startX, startY;
    private int selectedIndex = -1;
    private String objectId = "";
    private Shader shader;
    private final BufferedImage texture;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.zBuffer = new ZBuffer(panel.getRaster());
        this.lineRasterizer = new LineRasterizerTrivial(zBuffer);
        this.triangleRasterizer = new TriangleRasterizer(zBuffer, shader);
        this.renderer = new Renderer(lineRasterizer, triangleRasterizer, panel);
        try {
            this.texture = ImageIO.read(new File("./res/johnny.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.shader = new ShaderTexture();

        initListeners();
        initScene();

        redraw();
    }

    private void initListeners() {
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth(Math.PI*(e.getX()-startX)/(double)panel.getWidth());
                camera = camera.addZenith(Math.PI*(e.getY()-startY)/(double)panel.getHeight());
                //blokování přetočení kamery
                if(camera.getZenith()>90){
                    camera = camera.withZenith(90);
                }
                if(camera.getZenith()<-90){
                    camera = camera.withZenith(-90);
                }

                startX = e.getX();
                startY = e.getY();
                redraw();
            }

        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Q:
                        camera = camera.up(0.1);
                        redraw();
                        break;
                    case KeyEvent.VK_E:
                        camera = camera.down(0.1);
                        redraw();
                        break;
                    case KeyEvent.VK_W:
                        camera = camera.forward(0.1);
                        redraw();
                        break;
                    case KeyEvent.VK_S:
                        camera = camera.backward(0.1);
                        redraw();
                        break;
                    case KeyEvent.VK_A:
                        camera = camera.left(0.1);
                        redraw();
                        break;
                    case KeyEvent.VK_D:
                        camera = camera.right(0.1);
                        redraw();
                        break;
                    case KeyEvent.VK_P:
                        if (current == perspective) {
                            current = orthogonal;
                        } else {
                            current = perspective;
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        selectedIndex = (selectedIndex + 1) % solids.size();
                        objectId = solids.get(selectedIndex).getIdentifier();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        if (selectedIndex != -1) {
                            selectedIndex = -1;
                            objectId = "";
                        }
                        break;
                    case KeyEvent.VK_T:
                        triangleRasterizer.setShader(shader);
                        break;
                }
                redraw();
                if(objectId.equals("CUBE")){
                    processSolids(cube, e);
                }
            }
        });
    }

    public void initScene() {
        camera = new Camera(new Vec3D(0.5,-5,2.3),Math.toRadians(90),Math.toRadians(-15),10,true);
        perspective = new Mat4PerspRH(Math.PI/4,panel.getHeight()/(float)panel.getWidth(),0.1,20.);
        orthogonal = new Mat4OrthoRH((float)panel.getWidth()/100,(float)panel.getHeight()/100,0.1,20.);

        axis = new Axis();
        arrow = new Arrow();
        arrow.setModel(arrow.decreaseZ());

        cube = new Cube();
        cube.setModel(cube.increaseY());
        cube.setModel(cube.increaseX());
        pyramid = new Pyramid();
        for (int i = 0; i<2; i++){
            pyramid.setModel(pyramid.increaseX());
            pyramid.setModel(pyramid.decreaseY());
        }


        current = perspective;
        solids = new ArrayList<>();
        solids.add(arrow);
        solids.add(cube);
        solids.add(pyramid);
    }

    private void redraw() {
        panel.clear();
        zBuffer.clear();
        renderer.setView(camera.getViewMatrix());
        renderer.setProj(current);
        renderer.renderSolid(axis);
        renderer.renderSolids(solids);


        panel.repaint();
    }

    public void processSolids(Solid solid, KeyEvent keyEvent){
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        solid.setModel(solid.decreaseX());
                        break;
                    case KeyEvent.VK_RIGHT:
                        solid.setModel(solid.increaseX());
                        break;
                    case KeyEvent.VK_UP:
                        solid.setModel(solid.increaseY());
                        break;
                    case KeyEvent.VK_DOWN:
                        solid.setModel(solid.decreaseY());
                        break;
                    case KeyEvent.VK_SHIFT:
                        solid.setModel(solid.increaseZ());
                        break;
                    case KeyEvent.VK_CONTROL:
                        solid.setModel(solid.decreaseZ());
                        break;
                    case KeyEvent.VK_X:
                        solid.setModel(solid.rotateX());
                        break;
                    case KeyEvent.VK_Y:
                        solid.setModel(solid.rotateY());
                        break;
                    case KeyEvent.VK_Z:
                        solid.setModel(solid.rotateZ());
                        break;
                    case KeyEvent.VK_O:
                        solid.setModel(solid.zoomUp());
                        break;
                    case KeyEvent.VK_L:
                        solid.setModel(solid.zoomDown());
                        break;
                }
                redraw();
            }

}
