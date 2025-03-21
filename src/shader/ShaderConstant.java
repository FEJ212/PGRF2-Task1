package shader;

import model.Vertex;
import transforms.Col;
//shader pro vykreslení konstantní barvy
public class ShaderConstant implements Shader{
    @Override
    public Col getColor(Vertex pixel) {
        return new Col(0xb00b69);
    }
}
