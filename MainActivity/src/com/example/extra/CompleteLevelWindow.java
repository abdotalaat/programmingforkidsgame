package com.example.extra;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;

import android.R.string;

import com.example.manager.ResourcesManager;
public class CompleteLevelWindow extends Sprite
{
	private TiledSprite star1;
	private TiledSprite star2;
	private TiledSprite star3;
	private Text scoreTxt;
	private Text highestScoreTxt;
	
	
	public enum StarsCount
	{
		ONE,
		TWO,
		THREE
	}
	
	public CompleteLevelWindow(VertexBufferObjectManager pSpriteVertexBufferObject,String score,String highestScore)
	{
		super(0, 0, 650, 500, ResourcesManager.getInstance().complete_window_region, pSpriteVertexBufferObject);
		attachStars(pSpriteVertexBufferObject,score,highestScore);
		
	}
	
	private void attachStars(VertexBufferObjectManager pSpriteVertexBufferObject,String score,String highestScore)
	{
		star1 = new TiledSprite(150, 150, ResourcesManager.getInstance().complete_stars_region, pSpriteVertexBufferObject);
		star2 = new TiledSprite(325, 150, ResourcesManager.getInstance().complete_stars_region, pSpriteVertexBufferObject);
		star3 = new TiledSprite(500, 150, ResourcesManager.getInstance().complete_stars_region, pSpriteVertexBufferObject);
		highestScoreTxt=new Text(500,300,ResourcesManager.getInstance().font,"Score: 123456789",new TextOptions(HorizontalAlign.RIGHT),pSpriteVertexBufferObject);
		scoreTxt=new Text(130,300,ResourcesManager.getInstance().font,"Score: 123456789",new TextOptions(HorizontalAlign.RIGHT),pSpriteVertexBufferObject);
	    scoreTxt.setText("Score:"+score);
	    highestScoreTxt.setText("Best:"+highestScore);
	    attachChild(scoreTxt);
	    attachChild(highestScoreTxt);
		attachChild(star1);
		attachChild(star2);
		attachChild(star3);
	}
	
	/**
	 * Change star`s tile index, depends on stars count.
	 * @param starsCount
	 */
	public void display(StarsCount starsCount, Scene scene, Camera camera)
	{
		// Change stars tile index, based on stars count (1-3)
		switch (starsCount)
		{
			case ONE:
				star1.setCurrentTileIndex(0);
				star2.setCurrentTileIndex(1);
				star3.setCurrentTileIndex(1);
				break;
			case TWO:
				star1.setCurrentTileIndex(0);
				star2.setCurrentTileIndex(0);
				star3.setCurrentTileIndex(1);
				break;
			case THREE:
				star1.setCurrentTileIndex(0);
				star2.setCurrentTileIndex(0);
				star3.setCurrentTileIndex(0);
				break;
		}
		
		
		// Hide HUD
		camera.getHUD().setVisible(false);
		
		// Disable camera chase entity
		camera.setChaseEntity(null);
		
		// Attach our level complete panel in the middle of camera
		setPosition(camera.getCenterX(), camera.getCenterY());
		scene.attachChild(this);
	}
}