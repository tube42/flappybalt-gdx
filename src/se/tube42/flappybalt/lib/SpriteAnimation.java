package se.tube42.flappybalt.lib;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import se.tube42.flappybalt.lib.*;

public class SpriteAnimation
{
    private int []keys;
    private int frame;
    private boolean loop, stopped;
    private float t0, tf;	
    
    public SpriteAnimation(int [] keys, int fps, boolean loop)
    {
        this.keys = keys;
        this.loop = loop;
        this.stopped = true;
        this.frame = 0;
        this.tf = 1f / fps;
    }
    
    public boolean isStopped()
    {
        return stopped;
    }
    
    public int getFrame()
    {
        return frame;
    }
    public int getIndex()
    {
        return keys[frame];
    }
    
    public void start()
    {
        t0 = 0;
        frame = 0;
        stopped = false;
    }
    
    public void stop()
    {
        stopped = true;
    }
    
    public void update(float dt)
    {
        if(!stopped) {
            t0 += dt ;
            if(t0 > tf) {
                t0 -= tf;
                int new_frame = frame + 1;
                if(new_frame >= keys.length) {
                    if(loop) {
                        frame = 0;
                    } else { 
                        frame = 0;
                        stopped = true;
                    }
                } else {
                    frame = new_frame;
                }
            }
        }
    }        
}
