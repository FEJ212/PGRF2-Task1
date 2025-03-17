package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid{
    public Cube() {
        vertexBuffer.add(new Vertex(new Point3D(-0.5,-0.5,1),new Col(0xff0000)));//O
        vertexBuffer.add(new Vertex(new Point3D(0.5,-0.5,1),new Col(0xffff00)));//1
        vertexBuffer.add(new Vertex(new Point3D(-0.5,0.5,1),new Col(0xff00ff)));//2
        vertexBuffer.add(new Vertex(new Point3D(0.5,0.5,1),new Col(0x00ff00)));//3

        vertexBuffer.add(new Vertex(new Point3D(-0.5,-0.5,0),new Col(0x00ffff)));//4
        vertexBuffer.add(new Vertex(new Point3D(0.5,-0.5,0),new Col(0x0000ff)));//5
        vertexBuffer.add(new Vertex(new Point3D(-0.5,0.5,0),new Col(0xffffff)));//6
        vertexBuffer.add(new Vertex(new Point3D(0.5,0.5,0),new Col(0x000000)));//7
        //Přední strana
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        //Zadní strana
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(6);
        indexBuffer.add(5);
        indexBuffer.add(6);
        indexBuffer.add(7);
        //Levý bok
        indexBuffer.add(0);
        indexBuffer.add(4);
        indexBuffer.add(6);
        indexBuffer.add(0);
        indexBuffer.add(6);
        indexBuffer.add(2);
        //Pravý bok
        indexBuffer.add(1);
        indexBuffer.add(5);
        indexBuffer.add(7);
        indexBuffer.add(1);
        indexBuffer.add(3);
        indexBuffer.add(7);
        //Spodní strana
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(4);
        indexBuffer.add(1);
        indexBuffer.add(4);
        indexBuffer.add(5);
        //Vrchní strana
        indexBuffer.add(2);
        indexBuffer.add(6);
        indexBuffer.add(7);
        indexBuffer.add(2);
        indexBuffer.add(7);
        indexBuffer.add(4);
        //Part buffer
        for (int i = 0; i<indexBuffer.size(); i+=6) {
            partBuffer.add(new Part(i, 2, TopologyType.FAN));
        }
    }
    @Override
    public String getIdentifier() {
        return "CUBE";
    }
}
