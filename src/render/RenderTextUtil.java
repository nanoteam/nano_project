package render;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: artur
 * Date: 05.05.13
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public class RenderTextUtil {
    private static RenderTextUtil renderTextUtil;
    private static MFont font;
    public static RenderTextUtil getInstance(){
        if(renderTextUtil==null){
            renderTextUtil = new RenderTextUtil();
        }
        return renderTextUtil;
    }

    private RenderTextUtil(){
            font = new MFont("res/font2.png",MFont.PNG_EXT);
    }

    public static void init(){

    }
    public void drawText(float x, float y,String text, Color color,float size){
        font.drawString(text, x,y,color,size);
    }
}
