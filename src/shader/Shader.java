package shader;

import model.Vertex;
import transforms.Col;
//interface pro shadery
@FunctionalInterface
public interface Shader {
    Col getColor(Vertex pixel);
}
