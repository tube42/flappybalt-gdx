package se.tube42.flappybalt;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import se.tube42.flappybalt.lib.*;

public class Player extends SpriteItem
{
    
    
    public Player(int x, int y, TextureRegion [] tex)
    {
        super(x, y, tex);
        
        anim = new SpriteAnimation(new int []{ 1, 0, 1, 2}, 12, false);
        reset();
    }
    
    public void flap()
    {
        if(acceleration_y == 0){
            acceleration_y = -500;
            velocity_x = 80;
        }
        velocity_y = 240;
        
        anim.start();
    }

    
    public void reset()
    {
        super.reset();
        index = 2;
        flip_x = false;
        x = AppBase.sw / 2;
        y = AppBase.sh / 2;
    }
    
    
}
