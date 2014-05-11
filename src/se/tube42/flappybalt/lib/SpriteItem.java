package se.tube42.flappybalt.lib;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class SpriteItem implements BaseItem
{     
    private static final int MAX_VELOCITY = 30000;
    
    protected TextureRegion [] tex;
    protected float ax, ay;
    public int index, w, h;
    public float x, y, alpha;
    public float acceleration_y, acceleration_x;
    public float velocity_x, velocity_y;
    public SpriteAnimation anim;
    public boolean flip_x, flip_y;
    
    public SpriteItem(int x, int y, TextureRegion [] tex)
    {
        this.x = x;
        this.y = y;
        this.alpha = 1;
        this.tex = tex;
        this.index = 0;
        this.anim = null;
        
        this.w = tex[0].getRegionWidth();
        this.h = tex[0].getRegionHeight();
        this.flip_x = this.flip_y = false;
        setAlignment(0, 0);        
        reset();
    }
    
    public void reset()
    {
        acceleration_y = acceleration_x = 0;
        velocity_x = velocity_y = 0;
        
        if(anim != null)
            anim.stop();
    }
    
    public float getX() { return x + ax * AppBase.sw; }
    public float getY() { return y + ay * AppBase.sh; }
    
    public void setAlignment(float ax, float ay)
    {
        this.ax = ax;
        this.ay = ay;
    }
    
    public void update(float dt)
    {
        // frame animation:
        if(anim != null) {
            if(!anim.isStopped() ) {
                anim.update(dt);
                index = anim.getIndex();
            }
        }        
        
        // motion:
        velocity_x = Math.max( -MAX_VELOCITY, Math.min( +MAX_VELOCITY, velocity_x + (acceleration_x ) * dt));
        velocity_y = Math.max( -MAX_VELOCITY, Math.min( +MAX_VELOCITY, velocity_y + (acceleration_y ) * dt));
        x += velocity_x * dt;
        y += velocity_y * dt;
    }
    
    public void draw(SpriteBatch sb)
    {
        if(alpha > 0.001f) {
            final float x0 = getX();
            final float y0 = getY();            
            final TextureRegion tr = tex[index];
            tr.flip(tr.isFlipX() != flip_x, tr.isFlipY() != flip_y);
            sb.setColor(1, 1, 1, alpha);   
            sb.draw(tr, x0, y0, w, h);
        }
    }
    
    public boolean overlaps(SpriteItem si)
    {
        // X
        final float ax1 = getX();
        final float ax2 = ax1 + w;
        final float bx1 = si.getX();
        final float bx2 = bx1 + si.w;

        // Y
        final float ay1 = getY();
        final float ay2 = ay1 + h;
        final float by1 = si.getY();
        final float by2 = by1 + si.h;

        return (ax1 < bx2 && ax2 > bx1 && ay1 < by2 && ay2 > by1);
    }
}
