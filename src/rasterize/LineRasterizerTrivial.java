package rasterize;

import model.Vertex;
import raster.ZBuffer;
import transforms.Col;
//Algoritmus pro vlastní rasterizaci čáry
public class LineRasterizerTrivial extends LineRasterizer{
    //konstruktor bez specifikování barvy
    public LineRasterizerTrivial(ZBuffer zBuffer) {
        super(zBuffer);
    }
    //konstruktor se specifickou barvou
    public LineRasterizerTrivial(ZBuffer zBuffer, Col color) {
        super(zBuffer, color);
    }
    //rasterizace čáry
    @Override
    public void drawLine(Vertex a, Vertex b) {
        //získání jednotlivých hodnot bodů
        int x1 = (int)Math.round(a.getPosition().getX());
        int y1 = (int)Math.round(a.getPosition().getY());
        double z = a.getPosition().getZ();
        //získání barvy pro čáru
        color = a.getColor();
        int x2 = (int)Math.round(b.getPosition().getX());
        int y2 = (int)Math.round(b.getPosition().getY());
        double z2 = b.getPosition().getZ();
        //vykreslování pro veškeré čáry kromě svislých
        if(x1!=x2) {
            //vypočítání úhlu čáry
            float k = (y1 - y2) / (float) (x1 - x2);
            float q = y1 - k * x1;
            //vykreslování přes hodnotu x
            if (k < 1.0F && k > -1.0F) {
                //zajištění že x1 bude menší než x2, abychom mohli čáru vykreslovat skrz jeden for loop místo dvou
                if (x1 > x2) {
                    int tmp = x2;
                    x2 = x1;
                    x1 = tmp;
                }
                //vykreslovací cyklus
                for (int i = x1; i <= x2; i++) {
                    float y = k * i + q;
                    zBuffer.setPixelWithZTest(i, Math.round(y), z, color);
                }
            //vykreslování přes hodnotu y
            } else {
                //zajištění že y1 bude menší než y2, abychom mohli čáru vykreslovat skrz jeden for loop místo dvou
                if (y1 > y2) {
                    int tmp = y2;
                    y2 = y1;
                    y1 = tmp;
                }
                //vykreslovací cyklus
                for (int i = y1; i <= y2; i++) {
                    float x = (i - q) / k;
                    zBuffer.setPixelWithZTest(Math.round(x), i, z, color);
                }
            }
        //vykreslování svislých čar (zamezení dělení nulou)
        } else {
            //zajištění že y1 bude menší než y2, abychom mohli čáru vykreslovat skrz jeden for loop místo dvou
            if (y1 > y2) {
                int tmp = y2;
                y2 = y1;
                y1 = tmp;
            }
            //vykreslovací cyklus
            for (int i = y1; i <= y2; i++) {
                zBuffer.setPixelWithZTest(x1, i, z, color);
            }
        }

    }
}
