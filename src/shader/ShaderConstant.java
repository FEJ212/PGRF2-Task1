package shader;

import model.Vertex;
import transforms.Col;

import java.awt.image.BufferedImage;

public class ShaderConstant implements Shader{
    @Override
    public Col getColor(Vertex pixel, BufferedImage texture) {
        return new Col(0xb00b69);
    }
}
