package model;

import transforms.*;

public class Vertex {
    private final Point3D position;
    private final Col color;
    private final Vec3D normal;

    public Vertex(Point3D position, Col color, Vec3D normal) {
        this.position = position;
        this.color = color;
        this.normal = normal;
    }

    public Vertex(Point3D position) {
        this.position = position;
        this.color = new Col(0xffffff);
        this.normal = new Vec3D(0, 0, 0);
    }

    public Point3D getPosition() {
        return position;
    }

    public Col getColor() {
        return color;
    }

    public Vec3D getNormal() {
        return normal;
    }
}

