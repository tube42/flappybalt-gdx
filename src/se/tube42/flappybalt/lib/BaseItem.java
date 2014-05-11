package se.tube42.flappybalt.lib;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public interface BaseItem
{
    public void update(float dt);
    public void draw(SpriteBatch sb);
    public void reset();
}

