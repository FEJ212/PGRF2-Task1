package shader;

import model.Vertex;
import transforms.Col;
//shader pro linerání interpolaci barev
public class ShaderInterpolar implements Shader{
    @Override
    public Col getColor(Vertex pixel){
        return pixel.getColor();
    }
}
