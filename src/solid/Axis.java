package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;
//Osy X,Y a Z
public class Axis extends Solid{
    public Axis() {
        //Čáry
        vertexBuffer.add(new Vertex(new Point3D(0,0,0), new Col()));//0
        vertexBuffer.add(new Vertex(new Point3D(0.9,0,0), new Col(0xff0000)));//1
        vertexBuffer.add(new Vertex(new Point3D(0,0.9,0), new Col(0x00ff00)));//2
        vertexBuffer.add(new Vertex(new Point3D(0,0,0.9), new Col(0x0000ff)));//3
        //šipka1
        vertexBuffer.add(new Vertex(new Point3D(1,0,0),new Col(0xff0000)));//4
        vertexBuffer.add(new Vertex(new Point3D(0.9,0.1,-0.1),new Col(0xff0000)));//5
        vertexBuffer.add(new Vertex(new Point3D(0.9,-0.1,-0.1),new Col(0xff0000)));//6
        vertexBuffer.add(new Vertex(new Point3D(0.9,-0.1,0.1),new Col(0xff0000)));//7
        vertexBuffer.add(new Vertex(new Point3D(0.9,0.1,0.1),new Col(0xff0000)));//8
        //šipka2
        vertexBuffer.add(new Vertex(new Point3D(0,1,0),new Col(0x00ff00)));//9
        vertexBuffer.add(new Vertex(new Point3D(0.1,0.9,-0.1),new Col(0x00ff00)));//10
        vertexBuffer.add(new Vertex(new Point3D(-0.1,0.9,-0.1),new Col(0x00ff00)));//11
        vertexBuffer.add(new Vertex(new Point3D(-0.1,0.9,0.1),new Col(0x00ff00)));//12
        vertexBuffer.add(new Vertex(new Point3D(0.1,0.9,0.1),new Col(0x00ff00)));//13
        //šipka3
        vertexBuffer.add(new Vertex(new Point3D(0,0,1),new Col(0x0000ff)));//14
        vertexBuffer.add(new Vertex(new Point3D(0.1,-0.1,0.9),new Col(0x0000ff)));//15
        vertexBuffer.add(new Vertex(new Point3D(-0.1,-0.1,0.9),new Col(0x0000ff)));//16
        vertexBuffer.add(new Vertex(new Point3D(-0.1,0.1,0.9),new Col(0x0000ff)));//17
        vertexBuffer.add(new Vertex(new Point3D(0.1,0.1,0.9),new Col(0x0000ff)));//18
        //čáry
        indexBuffer.add(1);//0
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(0);//5
        //FANy pyramid (šipek)
        indexBuffer.add(4);//6
        indexBuffer.add(5);
        indexBuffer.add(6);
        indexBuffer.add(6);
        indexBuffer.add(7);
        indexBuffer.add(7);
        indexBuffer.add(8);
        indexBuffer.add(8);
        indexBuffer.add(5);//14

        indexBuffer.add(9);//15
        indexBuffer.add(10);
        indexBuffer.add(11);
        indexBuffer.add(11);
        indexBuffer.add(12);
        indexBuffer.add(12);
        indexBuffer.add(13);
        indexBuffer.add(13);
        indexBuffer.add(10);//23

        indexBuffer.add(14);//24
        indexBuffer.add(15);
        indexBuffer.add(16);
        indexBuffer.add(16);
        indexBuffer.add(17);
        indexBuffer.add(17);
        indexBuffer.add(18);
        indexBuffer.add(18);
        indexBuffer.add(15);//32
        //Podstavce pyramid (šipek)
        indexBuffer.add(5);//33
        indexBuffer.add(6);
        indexBuffer.add(7);
        indexBuffer.add(7);
        indexBuffer.add(8);
        indexBuffer.add(5);//38

        indexBuffer.add(10);//39
        indexBuffer.add(11);
        indexBuffer.add(12);
        indexBuffer.add(12);
        indexBuffer.add(13);
        indexBuffer.add(10);//44

        indexBuffer.add(15);//45
        indexBuffer.add(16);
        indexBuffer.add(17);
        indexBuffer.add(17);
        indexBuffer.add(18);
        indexBuffer.add(15);//50
        //Part Buffer
        partBuffer.add(new Part(0,3,TopologyType.LINES));
        partBuffer.add(new Part(6,4, TopologyType.FAN));
        partBuffer.add(new Part(15,4, TopologyType.FAN));
        partBuffer.add(new Part(24,4, TopologyType.FAN));
        partBuffer.add(new Part(33,6, TopologyType.TRIANGLES));
        //Identifikátor chybí, jelikož nechci, aby se na osy mohly aplikovat transformace
    }
}