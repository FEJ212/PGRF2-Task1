package shader;

import model.Vertex;
import transforms.Col;

import java.awt.image.BufferedImage;

public class ShaderTexture implements Shader{
    @Override
    public Col getColor(Vertex pixel, BufferedImage texture){
        int x = (int)Math.round(pixel.getUV().getX()*texture.getWidth());
        int y = (int)Math.round(pixel.getUV().getY()*texture.getHeight());
        return new Col(texture.getRGB(x, y));
    }
}
