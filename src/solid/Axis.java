package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Axis extends Solid{

    public Axis() {
        vertexBuffer.add(new Vertex(new Point3D(), new Col(255, 255, 255)));
        vertexBuffer.add(new Vertex(new Point3D(10, 0, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(9.9, 0.1, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(9.9, 0, 0.1), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 10, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0.1, 9.9, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 9.9, 0.1), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 10), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0.1, 0, 9.9), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0.1, 9.9), new Col(0, 0, 255)));

        indexBuffer.add(0);
        indexBuffer.add(1);

        indexBuffer.add(1);
        indexBuffer.add(2);

        indexBuffer.add(1);
        indexBuffer.add(3);

        indexBuffer.add(0);
        indexBuffer.add(4);

        indexBuffer.add(4);
        indexBuffer.add(5);

        indexBuffer.add(4);
        indexBuffer.add(6);

        indexBuffer.add(0);
        indexBuffer.add(7);

        indexBuffer.add(7);
        indexBuffer.add(8);

        indexBuffer.add(7);
        indexBuffer.add(9);
//        partBuffer.add(new Part(TopologyType.AXIS, 0, 9));
    }
}