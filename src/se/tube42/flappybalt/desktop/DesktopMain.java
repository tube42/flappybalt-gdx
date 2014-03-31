
package se.tube42.flappybalt.desktop;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.*;

import se.tube42.flappybalt.*;

public class DesktopMain
{
    public static void main(String[] args )
    {
        FlappyBalt app = new FlappyBalt();
        new LwjglApplication( app, "Flappy Balt", 480, 720, true);
    }
}
