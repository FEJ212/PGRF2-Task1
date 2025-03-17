package solid;

import model.Part;
import model.Vertex;
import transforms.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Solid {
    protected List<Vertex> vertexBuffer = new ArrayList<Vertex>();
    protected List<Integer> indexBuffer = new ArrayList<Integer>();
    protected List<Part> partBuffer = new ArrayList<Part>();

    Mat4 model = new Mat4Identity();
    Col color;

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Part> getPartBuffer() {
        return partBuffer;
    }

    public Col getColor(){
        return color;
    }

    public void setColor(Col color){
        this.color = color;
    }
    public Mat4 increaseX(){
        Mat4Transl mat = new Mat4Transl(new Vec3D(1,0,0));
        return getModel().mul(mat);
    }

    public Mat4 decreaseX(){
        Mat4Transl mat = new Mat4Transl(new Vec3D(-1,0,0));
        return getModel().mul(mat);
    }

    public Mat4 increaseY(){
        Mat4Transl mat = new Mat4Transl(new Vec3D(0,1,0));
        return getModel().mul(mat);
    }

    public Mat4 decreaseY(){
        Mat4Transl mat = new Mat4Transl(new Vec3D(0,-1,0));
        return getModel().mul(mat);
    }

    public Mat4 increaseZ(){
        Mat4Transl mat = new Mat4Transl(new Vec3D(0,0,1));
        return getModel().mul(mat);
    }

    public Mat4 decreaseZ(){
        Mat4Transl mat = new Mat4Transl(new Vec3D(0,0,-1));
        return getModel().mul(mat);
    }

    public Mat4 rotateX(){
        Mat4RotX mat = new Mat4RotX(Math.toRadians(1));
        return getModel().mul(mat);
    }

    public Mat4 rotateY(){
        Mat4RotY mat = new Mat4RotY(Math.toRadians(1));
        return getModel().mul(mat);
    }

    public Mat4 rotateZ(){
        Mat4RotZ mat = new Mat4RotZ(Math.toRadians(1));
        return getModel().mul(mat);
    }

    public Mat4 zoomUp(){
        Mat4Scale mat = new Mat4Scale(1.1);
        return getModel().mul(mat);
    }

    public Mat4 zoomDown(){
        Mat4Scale mat = new Mat4Scale(0.9);
        return getModel().mul(mat);
    }

    public String getIdentifier(){
        return "DEFAULT";
    }
}
