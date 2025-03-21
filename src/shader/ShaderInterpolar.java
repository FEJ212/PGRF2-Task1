package shader;

import model.Vertex;
import transforms.Col;

import java.awt.image.BufferedImage;

public class ShaderInterpolar implements Shader{
    @Override
    public Col getColor(Vertex pixel){
        return pixel.getColor();
    }
}
