package se.tube42.flappybalt.lib;

import java.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;


public class Utils
{     
    public static Random rnd = new Random();

    public static TextureRegion [] load(String filename, int count)
    {
        final TextureRegion [] ret = new TextureRegion[count];
        final Texture tex = new Texture( Gdx.files.internal(filename));        
        final int tw = tex.getWidth() / count;
        final int th = tex.getHeight();
        
        tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);      
        for(int a = 0; a < count; a++) 
            ret[a] = new TextureRegion(tex, a * tw, 0, tw, th);
        return ret;
    }
    
    public static BitmapFont loadFont(String name)
    {
        BitmapFont ret = new BitmapFont( Gdx.files.internal(name + ".fnt"),
            Gdx.files.internal(name + ".png"), false, true);

        ret.setUseIntegerPositions(true);
        return ret;
    }
    
}
