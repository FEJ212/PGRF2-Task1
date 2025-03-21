package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;

public class Arrow extends Solid {
    //šipka
    public Arrow() {
        //vertexy čáry
        vertexBuffer.add(new Vertex(new Point3D(-1, 0, 0), new Col(0xff0000))); // v0
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 0),new Col(0xb00b69))); // v1
        //vertexy trojúhelníku
        vertexBuffer.add(new Vertex(new Point3D(0, 1, 0),new Col(0xff0000), new Vec2D(0.5,0.9))); // v2
        vertexBuffer.add(new Vertex(new Point3D(1, 0, 0),new Col(0x00ff00), new Vec2D(0.9,0.5))); // v3
        vertexBuffer.add(new Vertex(new Point3D(0, -1, 0),new Col(0x0000ff), new Vec2D(0.5,0.1))); // v4
        //čáry
        indexBuffer.add(0);
        indexBuffer.add(1);
        //trojúhelníky
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(4);
        // Part Buffer
        partBuffer.add(new Part(0, 1, TopologyType.LINES));
        partBuffer.add(new Part(2, 1, TopologyType.TRIANGLES));
    }
    //získání identifikace objektu pro aplikaci transformací
    @Override
    public String getIdentifier() {
        return "ARROW";
    }
}
