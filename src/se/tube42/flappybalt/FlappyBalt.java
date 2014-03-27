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
    public SpriteItem bg;
    public SpriteItem bounceLeft, bounceRight;
	public Paddle paddleLeft, paddleRight;		
	public TextItem scoreDisplay;
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
        BitmapFont font16 = Utils.loadFont("fonts/nokia16");
        
        add( bg = new SpriteItem(0, 0, trbg) );

        
        add(scoreDisplay = new TextItem(0, 0, font26, 26));
		scoreDisplay.setAlignment(0.5f, 0.3f, -0.5f, 0.5f); // +0.5f, +0.5f);
		scoreDisplay.color = 0x4d4d59FF;
		scoreDisplay.setText("");

		add( bounceLeft = new SpriteItem(1, 17, trb));
		add( bounceRight = new SpriteItem(-5,17, trb) );
		bounceRight.setAlignment(1, 0);
		bounceRight.flip_x = true;

		bounceRight.anim = new SpriteAnimation(new int []{ 0, 1}, 8, false);
		bounceLeft.anim = new SpriteAnimation(new int []{ 0, 1}, 8, false);

		// bounceLeft.addAnimation("flash",[1,0],8,false);
		// bounceRight.addAnimation("flash",[1,0],8,false);
		
		add( paddleLeft = new Paddle(6, true, trp) );
		add( paddleRight = new Paddle(-15, false, trp) );
		paddleRight.setAlignment(1, 0);
		paddleRight.flip_x = true;
		

		add(player = new Player(100, 100, trdove));

        // TweenManager.allowEmptyTweens(true);
    }

	public void reset()
	{
		super.reset();
		score = 0;
	} 
    public void update(float dt)
    {
    	super.update(dt);
    
    	float edges = 14;

		if((player.y < edges) || (player.y + player.h > sh-edges) || player.overlaps(paddleLeft) || player.overlaps(paddleRight)) {
			player.kill();
			reset();
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

	
}
