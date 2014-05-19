package com.example.object;


import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseSineInOut;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.manager.ResourcesManager;
import com.example.scene.MazeScene;




public  class Player extends AnimatedSprite
{
	
	LoopEntityModifier loop;
	public static ArrayList<Character> pathWayArr ;
	public static int charArrIndex = 0;
	
	float intialPositionX;
	float intialPositionY;

	public Player(float pX, float pY, float pWidth, float pHeight,ITiledTextureRegion pTiledTextR,VertexBufferObjectManager vbom) 
	{
		super(pX, pY, pWidth, pHeight, pTiledTextR,vbom);
		pathWayArr = new ArrayList<Character>();
		intialPositionX = pX;
		intialPositionY = pY;
	}
	
	
	public void moveChar(char direction)
	{
		Path path = null;
		System.out.println("Before Switch"+this.getX()+this.getY());
		switch(direction) 
		{	
			case 'd':
				this.animate(new long[]{400, 400, 400}, 6, 8, true);  // with speed 400 , from pic 6 to 8
				path = new Path(3).to(this.getX(), this.getY()).to(this.getX(), this.getY() - 70);
				break;
			case 'r':
				this.animate(new long[]{200, 600, 200}, 3, 5, true);
				path = new Path(3).to(this.getX(), this.getY()).to(this.getX()+ 70, this.getY());
				System.out.println("After Switch");
				break;
			case 'u':
				this.animate(new long[]{200, 200, 200}, 0, 2, true);
				path = new Path(3).to(this.getX(), this.getY()).to(this.getX(), this.getY() + 70);
				break;
			case 'l':
				this.animate(new long[]{200, 200, 200}, 9, 11, true);
				path = new Path(3).to(this.getX(), this.getY()).to(this.getX() - 70, this.getY());
				break;
		}
		
		
		

		loop = new LoopEntityModifier(new LoopEntityModifier(new PathModifier(2, path, null, new myLoopListener(),EaseSineInOut.getInstance())), 2);
		this.registerEntityModifier(loop);
	}
	
	class myLoopListener implements IPathModifierListener
	{
		
		
		@Override
		public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) 
		{ 
			
			Player.this.unregisterEntityModifier(loop);
			Player.this.unregisterUpdateHandler(pEntity);
			Player.this.stopAnimation();
			
			System.out.println("-------------------------------------------"+charArrIndex);
			if(charArrIndex == MazeScene.wrongStep-1 && MazeScene.solved == false)
			{
				System.out.println("falseeee");
				animateChar(pathWayArr.get(charArrIndex+1));
			}
			else if(charArrIndex < pathWayArr.size() -1 && pathWayArr.get(charArrIndex+1) != null )
			{
				System.out.println("Normaaal");
					moveChar( pathWayArr.get(charArrIndex + 1) );
					charArrIndex ++;
					
				 
			}
			else
			{
//				ResourcesManager.getInstance().engine.runOnUpdateThread(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						MazeScene.removeCoin();
//					}
//				});
				
				charArrIndex = 0;
				pathWayArr.clear();
				System.out.println("lassttt");
			}
		
		}

		@Override
		public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) 
		{
			System.out.println("onPathStarted");
		}

		@Override
		public void onPathWaypointStarted(PathModifier pPathModifier,IEntity pEntity, int pWaypointIndex) 
		{
			
			System.out.println("onPathWaypointStarted");
			//System.out.println(charArrIndex+" "+ (pathWayArr.size()-1)+" "+MazeScene.solved);
		}

		@Override
		public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) 
		{
			System.out.println("onPathFinished");
		}
		
	}

	public void resetPlayer()
	{
		unregisterEntityModifier(loop);
		stopAnimation();
		setX(intialPositionX);
		setY(intialPositionY);
		pathWayArr.clear();
		charArrIndex = 0;
		this.animate(new long[]{400, 400, 400}, 0, 2, false); 
	}
	
	public void animateChar(char direction)
	{
			System.out.println("Before Switch");
			switch(direction) 
			{	
				case 'd':
					this.animate(new long[]{400, 400, 400}, 6, 8, false);  // with speed 400 , from pic 6 to 8
					break;
				case 'r':
					this.animate(new long[]{200, 600, 200}, 3, 5, false);
					System.out.println("After Switch");
					break;
				case 'u':
					this.animate(new long[]{200, 200, 200}, 0, 2, false);
					break;
				case 'l':
					this.animate(new long[]{200, 200, 200}, 9, 11, false);
					break;
			}
			if(MazeScene.wrongStep < MazeScene.level_one_sol.size() && !MazeScene.solved)
			{
				ResourcesManager.getInstance().music.play();
				MazeScene.showHint();
			}
			
	}
	
}