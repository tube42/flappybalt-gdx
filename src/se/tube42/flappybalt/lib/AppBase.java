package se.tube42.flappybalt.lib;

import java.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import se.tube42.flappybalt.lib.*;

public class AppBase implements ApplicationListener, InputProcessor
{     
    protected SpriteBatch batch;
    protected OrthographicCamera camera;    
    protected List<BaseItem> items;
    private Vector3 touch_tmp = new Vector3();
    
    public static int sw = 200, sh = 200;
    
    public void add(BaseItem item)
    {
    	items.add(item);
    }
    
    public void reset()
    {
        for(BaseItem s : items) s.reset();
    }
    
    // ----------------------------------------------------
    
    @Override
          public void create()
    {
        this.batch = new SpriteBatch();        
        this.camera = new OrthographicCamera();                        
        this.items = new ArrayList<BaseItem>();
        Gdx.input.setInputProcessor(this);
    }
    
    @Override final public void resize(int sw, int sh) 
    {   
    	this.sw = 480 / 3;
    	this.sh = 720 / 3;
        onResize(this.sw, this.sh, sw, sh);
    }
    
    @Override
          public void render()
    {                
        // camera
        batch.setProjectionMatrix(camera.combined);                
        
        // update
        float dt = Math.min(0.2f, Gdx.graphics.getDeltaTime() );        
        onUpdate(dt);
        
        // clean bg
        Gdx.gl.glClearColor( 0, 0, 0, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
        // draw scene
        batch.begin();  
        onDraw(batch);
        batch.end();                   
    }
    
    public void onUpdate(float dt)
    {
    	for(BaseItem s : items) s.update(dt);
    }

    public void onDraw(SpriteBatch sb)
    {
        for(BaseItem s : items) s.draw(sb);      
    }

    public void onResize(int sw, int sh, int rw, int rh)
    {
        camera.setToOrtho(false, sw, sh);
        camera.update();        
    }


    // --------------------------------------------------
    
    @Override
          public void pause() { }
    @Override
          public void resume() { }
    @Override
          public void dispose() { }
    
    
    
    // ---------------------------------------
    
    public boolean mouseMoved(int screenX, int screenY) { return false; }    
    public boolean scrolled(int amount) { return false; }
    public boolean keyTyped(char character) { return false; }
    
    
    public boolean keyDown(int keycode) { 
        return type(keycode, true);
    }
    public boolean keyUp(int keycode) { 
        return type(keycode, false);
    }
    
    public boolean touchUp(int x, int y, int pointer, int button) 
    { 
        return touch_(x, y, false, false);        
    }
    
    public boolean touchDown(int x, int y, int pointer, int button) 
    { 
        return touch_(x, y, true, false);        
    }
    
    public boolean touchDragged(int x, int y, int pointer) 
    { 
        return touch_(x, y, true, true);
    }
    
    private boolean touch_(int x, int y, boolean down, boolean drag)
    {
        // correct the Y axis direction
        touch_tmp.set(x, y, 0);
        camera.unproject(touch_tmp);        
        y = (int)( 0.5f + touch_tmp.y);
        x = (int)( 0.5f + touch_tmp.x);
        
        return touch(x, y, down, drag);
    }
    
    public boolean type(int key, boolean down)
    {
        return false;
    }
    public boolean touch(int x, int y, boolean down, boolean drag)
    {
        return false;
    }
}
