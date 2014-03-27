package se.tube42.flappybalt.lib;

import com.badlogic.gdx.*;
// import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

// import se.tube42.lib.tweeny.*;
// import se.tube42.lib.ks.*;

public class TextItem implements BaseItem
{     
	private BitmapFont font;
	private float sx, sy, tx, ty;
	private float w, h;
	private String text;

	public int color;
	public int x, y, size;
	

	public TextItem(int x, int y, BitmapFont font, int size)
	{
		this.font = font;
		this.size = size;
		this.x = x;
		this.y = y;
		this.color = 0xFF0000;
	}

	public void reset()
	{
		setText("");
	}

	public void setAlignment(float sx, float sy, float tx, float ty)
	{
		this.sx = sx;
		this.sy = sy;
		this.tx = tx;
		this.ty = ty;
	}

	public void setText(String text)
	{
		this.text = text;
		update_bounds();
	}

    public void update(float dt) { }
	
	public void draw(SpriteBatch sb) 
	{ 
		update_bounds();

		final float x0 = x + AppBase.sw * sx + w * tx;
		final float y0 = y + AppBase.sh * sy + h * ty;
		
		font.setColor( (color >> 24) / 255f,
			((color >> 16) & 0xFF) / 255f,
			((color >>  8) & 0xFF) / 255f,
			(color & 0xFF) / 255f);
			
		font.draw(sb, text, x0, y0);
	}    

	private void update_bounds()
	{
		BitmapFont.TextBounds tb = font.getBounds(text);          
		h = tb.height;
		w = tb.width;		
	}
}
