package model;

import transforms.*;

public class Vertex {
    private final Point3D position;
    private final Col color;

    public Vertex(Point3D position, Col color) {
        this.position = position;
        this.color = color;
    }

    public Vertex(Point3D position) {
        this.position = position;
        this.color = new Col(0xffffff);
    }

    public Point3D getPosition() {
        return position;
    }

    public Col getColor() {
        return color;
    }

    public Vertex mul(double k) {
        return new Vertex(position.mul(k), color.mul(k));
    }

    public Vertex add(Vertex v) {
        return new Vertex(position.add(v.getPosition()), color.add(v.getColor()));
    }

    public double getX(){
        return position.getX();
    }
    public double getY(){
        return position.getY();
    }
    public double getZ(){
        return position.getZ();
    }
    public double getW(){
        return position.getW();
    }

}

