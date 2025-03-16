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
}
