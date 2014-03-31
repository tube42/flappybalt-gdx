package se.tube42.flappybalt;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import se.tube42.flappybalt.lib.*;


public class Paddle extends SpriteItem
{   
    private static final int SPEED = 480;  
    
    private boolean left;
    
    private float target_y = 0;
    
    public Paddle(int x, boolean left, TextureRegion [] tr)
    {
        super(x, 0, tr);
        this.left = left;
    }
    
    public void reset()
    {
    	super.reset();
        
    	y = -h - AppBase.mh;
    }
    
    public void randomize()
    {
        target_y = 16 + Utils.rnd.nextFloat() * (208 - h);
        if(target_y < y)
            velocity_y = -SPEED;
        else
            velocity_y = SPEED;    	
    }
    
    public void update(float dt)
    {
    	super.update(dt);
        
    	if( ((velocity_y < 0) && (y <= target_y)) ||
            ((velocity_y > 0) && (y >= target_y)) )
        {
            velocity_y = 0;
            y = target_y;
        }
    }
}
