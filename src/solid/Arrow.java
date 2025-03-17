package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Point3D;

public class Arrow extends Solid {

    public Arrow() {
        vertexBuffer.add(new Vertex(new Point3D(-1, 0, 0))); // v0
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 0))); // v1

        vertexBuffer.add(new Vertex(new Point3D(0, 1, 0))); // v2
        vertexBuffer.add(new Vertex(new Point3D(1, 0, 0))); // v3
        vertexBuffer.add(new Vertex(new Point3D(0, -1, 0))); // v4

        // LINES
        indexBuffer.add(0);
        indexBuffer.add(1);

        // TRIANGLES
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(4);

        // Part buffer
        partBuffer.add(new Part(0, 1, TopologyType.LINES));
        partBuffer.add(new Part(2, 1, TopologyType.TRIANGLES));
    }
}
