package se.tube42.flappybalt.lib;

import java.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class AppBase implements ApplicationListener, InputProcessor
{     
    public static int sw, sh, mw, mh;

    protected SpriteBatch batch;
    protected OrthographicCamera camera;    
    protected List<BaseItem> items;
    protected float speed;
    private Vector3 touch_tmp = new Vector3();

    public void add(BaseItem item)
    {
    	items.add(item);
    }
    
    public void reset()
    {
        speed = 1;
        for(BaseItem s : items) s.reset();
    }
    
    // ----------------------------------------------------
    
    @Override public void create()
    {
        this.batch = new SpriteBatch();        
        this.camera = new OrthographicCamera();                        
        this.items = new ArrayList<BaseItem>();
        Gdx.input.setInputProcessor(this);

        // this will make sure sw/sh are correct from the start
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
    }
    
    @Override final public void resize(int sw, int sh) 
    {
    	AppBase.sw = 480 / 3;
    	AppBase.sh = 720 / 3;
        onResize(AppBase.sw, AppBase.sh, sw, sh);
    }

    @Override public void render()
    {                
        // camera
        batch.setProjectionMatrix(camera.combined);                
        
        // update
        float dt = Math.min(0.2f, Gdx.graphics.getDeltaTime()) * speed;
        onUpdate(dt);
        
        // clean bg
        Gdx.gl.glClearColor( 176/255f, 175/255f, 191/256f, 1f );
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
        int scale = Math.max(1, Math.min( rw / sw, rh / sh));
        
        mw = (rw / scale - sw) / 2;
        mh = (rh / scale - sh) / 2;        

        Gdx.gl.glViewport( mw * scale, mh * scale, rw - 2 * mw * scale , rh - 2 * mh * scale);           
        camera.setToOrtho(false, sw, sh);
        camera.update();
    }

    // ---------------------------------------

    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void dispose() { }

    // ---------------------------------------
    
    public boolean mouseMoved(int screenX, int screenY) { return false; }    
    public boolean scrolled(int amount) { return false; }
    public boolean keyTyped(char character) { return false; }

    public final boolean keyDown(int keycode) 
    { 
        return type(keycode, true);
    }

    public final boolean keyUp(int keycode) 
    { 
        return type(keycode, false);
    }
    
    // ---------------------------------------
    
    public final boolean touchUp(int x, int y, int pointer, int button) 
    { 
        return touch_(x, y, false, false);        
    }
    
    public final boolean touchDown(int x, int y, int pointer, int button) 
    { 
        return touch_(x, y, true, false);        
    }
    
    public final boolean touchDragged(int x, int y, int pointer) 
    { 
        return touch_(x, y, true, true);
    }
    
    // convert touch coordinates from display to world
    private final boolean touch_(int x, int y, boolean down, boolean drag)
    {
        // correct the Y axis direction
        touch_tmp.set(x, y, 0);
        camera.unproject(touch_tmp);        
        y = (int)( 0.5f + touch_tmp.y);
        x = (int)( 0.5f + touch_tmp.x);
        
        return touch(x, y, down, drag);
    }
    
    // ---------------------------------------
    // override these in your game
    
    public boolean type(int key, boolean down)
    {
        return false;
    }
    public boolean touch(int x, int y, boolean down, boolean drag)
    {
        return false;
    }
}
