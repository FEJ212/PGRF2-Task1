package model;

import transforms.*;

public class Vertex implements Vectorizable<Vertex>{
    private Point3D position;
    private final Col color;
    private final Vec2D uv;
    private double one;
    //Konstruktor pro známou pozici a barvu
    public Vertex(Point3D position, Col color) {
        this.position = position;
        this.color = color;
        this.uv = new Vec2D(0,0);
        this.one = 1;
    }
    //Konstruktor pro známou pozici
    public Vertex(Point3D position) {
        this.position = position;
        this.color = new Col(0xffffff);
        this.uv = new Vec2D(0,0);
        this.one = 1;
    }
    //Konstruktor pro známou pozici, barvu a mapování textury
    public Vertex(Point3D position, Col color, Vec2D uv) {
        this.position = position;
        this.color = color;
        this.uv = uv;
        this.one = 1;
    }
    //konstruktor pro známou pozici, barvu, mapování textury a proměnnou one pro projekční korekci
    public Vertex(Point3D position, Col color, Vec2D uv, double one) {
        this.position = position;
        this.color = color;
        this.uv = uv;
        this.one = one;
    }

    //gettery
    public Point3D getPosition() {
        return position;
    }

    public Col getColor() {
        return color;
    }

    public Vec2D getUV() {
        return uv;
    }

    public double getOne() {
        return one;
    }
    //funkce pro lerp
    @Override
    public Vertex mul(double k) {
        return new Vertex(position.mul(k), color.mul(k), uv.mul(k), one*k);
    }
    @Override
    public Vertex add(Vertex v) {
        return new Vertex(position.add(v.getPosition()), color.add(v.getColor()), uv.add(v.getUV()), one + v.getOne());
    }
}

