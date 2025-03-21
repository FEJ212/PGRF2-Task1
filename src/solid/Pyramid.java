package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;
//pyramida
public class Pyramid extends Solid{
    public Pyramid() {
        vertexBuffer.add(new Vertex(new Point3D(0,0,0.5),new Col(0x69b00b)));//0
        vertexBuffer.add(new Vertex(new Point3D(0.5,-0.5,0),new Col(0xb00b69)));//1
        vertexBuffer.add(new Vertex(new Point3D(-0.5,-0.5,0),new Col(0xff00ff)));//2
        vertexBuffer.add(new Vertex(new Point3D(-0.5,0.5,0),new Col(0x00ffff)));//3
        vertexBuffer.add(new Vertex(new Point3D(0.5,0.5,0),new Col(0xffff00)));//4
        //FAN
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(4);
        indexBuffer.add(1);
        //Trojúhelníky
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(1);
        //Part Buffer
        partBuffer.add(new Part(0,4, TopologyType.FAN));
        partBuffer.add(new Part(9,2, TopologyType.TRIANGLES));
    }
    //získání identifikace objektu pro aplikaci transformací
    @Override
    public String getIdentifier() {
        return "PYRAMID";
    }
}
