package se.tube42.flappybalt;

import java.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import se.tube42.flappybalt.lib.*;

public class FlappyBalt extends AppBase
{   
    public Player player;
    public SpriteItem bg, flash;
    public SpriteItem bounceLeft, bounceRight;
    public Paddle paddleLeft, paddleRight;		
    public TextItem scoreDisplay, highscoreDisplay;
    public int score;

    @Override
          public void create()
    {
    	super.create();

        TextureRegion [] trbg = Utils.load("background.png", 1);
        TextureRegion [] trp = Utils.load("paddle.png", 1);
        TextureRegion [] trb = Utils.load("bounce.png", 2);
        TextureRegion [] trdove = Utils.load("dove.png", 3);
        BitmapFont font26 = Utils.loadFont("fonts/nokia26");
        BitmapFont font12 = Utils.loadFont("fonts/nokia12");
        
        add( bg = new SpriteItem(0, 0, trbg) );
        
        add(scoreDisplay = new TextItem(0, 0, font26));
        scoreDisplay.setAlignment(0.5f, 0.35f, -0.5f, 0.5f); // +0.5f, +0.5f);
        scoreDisplay.color = 0x4d4d59FF;
        
        add(highscoreDisplay = new TextItem(0, -16, font12));
        highscoreDisplay.setAlignment(0.5f, 1f, -0.5f, -0.5f); // +0.5f, +0.5f);
        highscoreDisplay.color = 0xFFFFFFFF;
        
        add( bounceLeft = new SpriteItem(1, 17, trb));
        add( bounceRight = new SpriteItem(-5,17, trb) );
        bounceRight.setAlignment(1, 0);
        bounceRight.flip_x = true;
        
        bounceRight.anim = new SpriteAnimation(new int []{ 0, 1}, 8, false);
        bounceLeft.anim = new SpriteAnimation(new int []{ 0, 1}, 8, false);
        
        add( paddleLeft = new Paddle(6, true, trp) );
        add( paddleRight = new Paddle(-15, false, trp) );
        paddleRight.setAlignment(1, 0);
        paddleRight.flip_x = true;
        
        add(player = new Player(50, 50, trdove));  

        add( flash = new SpriteItem(0, 0, Utils.load("rect.png", 1)));

        reset();      
    }

    public void onResize(int w, int h, int a, int b)
    {
        super.onResize(w, h, a, b);

        if(flash != null) {
            flash.w = w;
            flash.h = h;
        }
    }

    
    public void reset()
    {
        super.reset();

        int best_score = loadScore();
        score = 0;
        
        highscoreDisplay.setText(best_score < 1 ? "" : "" + best_score);
        scoreDisplay.setText("");

        flash.alpha = 0;
    }

    public void onUpdate(float dt)
    {
    	super.onUpdate(dt);
        
        // slow down effect when dead
        speed = 1 / (1 * flash.alpha + 1);

        // flash after dead:
        if(flash.alpha > 0) {
            flash.alpha -= dt * 0.8f;

            if(flash.alpha > 0.5f) {
                camera.zoom = 0.98f + Utils.rnd.nextFloat() / 50;
                camera.update();
            }
            if(flash.alpha <= 0) {
                camera.zoom = 1;
                camera.update();

                reset();
            }
            return;
        }

    	float edges = 14;
        
        if((player.y < edges) || (player.y + player.h > sh-edges) || player.overlaps(paddleLeft) || player.overlaps(paddleRight)) {          
            saveScore(score);
            flash.alpha = 1f;
            player.velocity_x = 0;
            player.velocity_y = 0;
        } else if(player.x < 5) {
            player.x = 5;
            player.velocity_x = -player.velocity_x;
            player.flip_x = false;
            score++;
            scoreDisplay.setText("" + score);
            bounceLeft.anim.start();
            paddleRight.randomize();
        } else if(player.x + player.w > sw - 5) {
            
            player.x = sw - player.w - 5;
            player.velocity_x = -player.velocity_x;
            player.flip_x = true;
            score++;
            scoreDisplay.setText("" + score);
            bounceRight.anim.start();
            paddleLeft.randomize();
        }
    }
    
    
    public boolean type(int key, boolean down)
    {
        if(down && key == Keys.SPACE) {
            player.flap();
        }
        return true;
    }
    
    // -----------------------------------------------

    private static Preferences prefs = null;

    private static Preferences getPrefs()
    {
        if(prefs == null)
            prefs = Gdx.app.getPreferences("flappybalt-1");
        return prefs;
    }

    public static int loadScore()
    {
        return getPrefs().getInteger("score", 0);
    }

    public static void saveScore(int score)
    {
        int old = loadScore();
        if(score > old ) {
            getPrefs().putInteger("score", score);
            getPrefs().flush();
        }
    }    
}
