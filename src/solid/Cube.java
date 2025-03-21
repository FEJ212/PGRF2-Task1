package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;
//kostka
public class Cube extends Solid{
    public Cube() {
        //Přední strana
        vertexBuffer.add(new Vertex(new Point3D(-0.5,-0.5,1),new Col(0xff0000)));//O
        vertexBuffer.add(new Vertex(new Point3D(0.5,-0.5,1),new Col(0xffff00)));//1
        vertexBuffer.add(new Vertex(new Point3D(-0.5,0.5,1),new Col(0xff00ff)));//2
        vertexBuffer.add(new Vertex(new Point3D(0.5,0.5,1),new Col(0x00ff00)));//3
        //Zadní strana
        vertexBuffer.add(new Vertex(new Point3D(-0.5,-0.5,0),new Col(0x00ffff)));//4
        vertexBuffer.add(new Vertex(new Point3D(0.5,-0.5,0),new Col(0x0000ff)));//5
        vertexBuffer.add(new Vertex(new Point3D(-0.5,0.5,0),new Col(0xffffff)));//6
        vertexBuffer.add(new Vertex(new Point3D(0.5,0.5,0),new Col(0xb00b69)));//7
        //FAN1
        indexBuffer.add(0);
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(5);
        indexBuffer.add(1);
        indexBuffer.add(1);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(2);
        indexBuffer.add(2);
        indexBuffer.add(6);
        indexBuffer.add(6);
        indexBuffer.add(4);
        //FAN2
        indexBuffer.add(7);
        indexBuffer.add(5);
        indexBuffer.add(1);
        indexBuffer.add(1);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(2);
        indexBuffer.add(2);
        indexBuffer.add(6);
        indexBuffer.add(6);
        indexBuffer.add(4);
        indexBuffer.add(4);
        indexBuffer.add(5);
        //Part Buffer
        partBuffer.add(new Part(0,6,TopologyType.FAN));
        partBuffer.add(new Part(13,6,TopologyType.FAN));
    }
    //získání identifikace objektu pro aplikaci transformací
    @Override
    public String getIdentifier() {
        return "CUBE";
    }
}
