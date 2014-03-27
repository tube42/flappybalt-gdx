package se.tube42.flappybalt.lib;

import com.badlogic.gdx.*;
// import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

// import se.tube42.lib.tweeny.*;
// import se.tube42.lib.ks.*;

public class SpriteItem implements BaseItem
{     
	private static final int MAX_VELOCITY = 30000;
	
	protected TextureRegion [] tex;
	protected float ax, ay;
	public int index, w, h;
	public float x, y;

	public float acceleration_y, acceleration_x;
	public float velocity_x, velocity_y;
	public SpriteAnimation anim;
	public boolean flip_x, flip_y;

	public SpriteItem(int x, int y, TextureRegion [] tex)
	{
		this.x = x;
		this.y = y;
		this.tex = tex;
		this.index = 0;
		this.anim = null;

		this.w = tex[0].getRegionWidth();
		this.h = tex[0].getRegionHeight();
		flip_x = flip_y = false;
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
	public float getY() { return  y + ay * AppBase.sh; }

	public void setAlignment(float ax, float ay)
	{
		this.ax = ax;
		this.ay = ay;
	}

	public void update(float dt)
	{

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
		
		/*
		float delta;
		float  velocityDelta;

		velocityDelta = (computeVelocity(dt, velocity_x, acceleration_x,0 ,MAX_VELOCITY) - velocity_x)/2;
		velocity_x += velocityDelta;
		delta = velocity_x * dt;
		velocity_x += velocityDelta;
		x += delta;

		velocityDelta = (computeVelocity(dt, velocity_y, acceleration_y, 0, MAX_VELOCITY) - velocity_y)/2;
		velocity_y += velocityDelta;
		delta = velocity_y * dt;
		velocity_y += velocityDelta;
		y += delta;
		*/
	}


	public static float computeVelocity(float dt, float Velocity, float Acceleration, float Drag, float Max)
	{
		if(Acceleration != 0)
			Velocity += Acceleration * dt;
		if((Velocity != 0) && (Max != 10000))
		{
			if(Velocity > Max)
				Velocity = Max;
			else if(Velocity < -Max)
				Velocity = -Max;
		}
		return Velocity;
	}

	public void draw(SpriteBatch sb)
	{
		final float x0 = getX();
		final float y0 = getY();

	
		TextureRegion tr = tex[index];
		// tr.flip(flip_x, flip_y);
		tr.flip(tr.isFlipX() != flip_x, tr.isFlipY() != flip_y);

		sb.draw(tr, x0, y0);
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
