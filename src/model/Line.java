package model;

public class Line {
    private final int x1, y1, x2, y2;
    //konstruktory
    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    //možnost vytvořit objekt Line z informací o 2 vertexech
    public Line(Vertex v1, Vertex v2) {
        this.x1 = (int)Math.round(v1.getPosition().getX());
        this.y1 = (int)Math.round(v1.getPosition().getY());
        this.x2 = (int)Math.round(v2.getPosition().getX());
        this.y2 = (int)Math.round(v2.getPosition().getY());
    }
    //Gettery
    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}
