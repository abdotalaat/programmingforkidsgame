package eg.iti.programmingforkids.scene;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import eg.iti.programmingforkids.base.BaseScene;
import eg.iti.programmingforkids.extra.CompleteLevelWindow;
import eg.iti.programmingforkids.extra.CompleteLevelWindow.StarsCount;
import eg.iti.programmingforkids.manager.ResourcesManager;
import eg.iti.programmingforkids.manager.SceneManager;
import eg.iti.programmingforkids.manager.SceneManager.SceneType;
import eg.iti.programmingforkids.object.Player;

public class MazeScene extends BaseScene implements OnClickListener
{

	
	private HUD gameHUD;
	private Text levelText;
	private Text timeText;
	private PhysicsWorld physicsWorld;
	private int timer = 120;
	
	int countOfSteps=0;
	
	ButtonSprite submit,reset ;
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	    
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEVEL_COMPLETE = "levelComplete";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM1 = "block1";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN = "coin";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "myPlayer";
	//arrows
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_UP = "up";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DOWN = "down";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_RIGHT = "right";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEFT = "left";
	//submit
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SUBMIT = "submit";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_RESET = "reset";
	//right: 1  down : 2
		// left: 4   up: 3
		
	private static final int RIGHT = 1;
	private static final int LEFT = 2;
	private static final int UP = 3;
	private static final int DOWN = 4;
	private static final int SUBMIT = 0;
	private static final int RESET = -1;
		
	private CompleteLevelWindow levelCompleteWindow;
	
	private Text gameOverText;
	private boolean gameOverDisplayed = false;
	
	private boolean firstTouch = false;
	private Player player;
	public static boolean solved=false;
	public static int wrongStep=-1;
	String rightStep;
	public static  ArrayList<Character> level_one_sol;
	
	public static Sprite coin;
	
	
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
	    createHUD();
	    createPhysics();
	    createGameOverText();
	    loadSol(1);
	    loadLevel(1);
	    timerCounting();
	    levelCompleteWindow = new CompleteLevelWindow(vbom);
		
	}

	@Override
	public void onBackKeyPressed() 
	{
		SceneManager.getInstance().loadSubMenuScene(engine);	
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeScene() 
	{
		// TODO Auto-generated method stub
		 camera.setHUD(null);
		    camera.setCenter(400, 240);

		
	}
	private void loadSol(int levelNum)
	{
		level_one_sol =new ArrayList<Character>();
		if(levelNum == 1)
		{
			level_one_sol.add('u');
			level_one_sol.add('u');
		}
	}
	private void createBackground()
	{
		setBackground(new Background(Color.BLUE));
	}
	
	private void createHUD()
	{
	    gameHUD = new HUD();
	    
	    levelText = new Text(20, 430, resourcesManager.font, "Score: 123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
	    levelText.setAnchorCenter(0, 0);    
	    levelText.setText("Level: 1");
	    gameHUD.attachChild(levelText);
	    

	    timeText=new Text(300,430,resourcesManager.font,"Time: 123456789",new TextOptions(HorizontalAlign.RIGHT),vbom);
	    timeText.setAnchorCenter(0, 0);
	    timeText.setText("Time: 1000");
	    gameHUD.attachChild(timeText);
	    camera.setHUD(gameHUD);
	}
	

	private void createPhysics()
	{
	    physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false);  //60 steps/ sec
	    registerUpdateHandler(physicsWorld);
	}
	
	
	private void loadLevel(int levelID)
	{
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
		
		final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
		{
			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
			{
				final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
				final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
				
				camera.setBounds(0, 0, width, height); // here we set camera bounds
		        camera.setBoundsEnabled(true);
				return MazeScene.this;
			}
		});
		
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY)
		{
			
			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
			{
				final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
				final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
				final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
				
				final Sprite levelObject;
				
				if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM1))
				{
					levelObject = new Sprite(x, y, resourcesManager.block1_region, vbom);
					PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF).setUserData("platform1");
				} 
				
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN))
				{
					levelObject = new Sprite(x, y, resourcesManager.coin_region, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed) 
						{
							super.onManagedUpdate(pSecondsElapsed);

							if ( player.collidesWith(this))
							{
								
								
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								int t=timer;
								
								if(t >= 80)
									levelCompleteWindow.display(StarsCount.THREE, MazeScene.this, camera);
								else if(t >=40)
									levelCompleteWindow.display(StarsCount.TWO, MazeScene.this, camera);
								else 
									levelCompleteWindow.display(StarsCount.ONE, MazeScene.this, camera);
							}
						}
					};
					coin=levelObject;
					levelObject.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER))
				{
					player = new Player(x , y , 50 , 50 ,ResourcesManager.getInstance().player_region,vbom);
					levelObject = player;
				}
				//arrows **************************************************************************
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_UP))
				{
					ButtonSprite up = new ButtonSprite(x, y, ResourcesManager.getInstance().arrow_btn, vbom , MazeScene.this);
					up.setRotation(270);
					up.setTag(UP);
					MazeScene.this.registerTouchArea(up);
					levelObject = up;
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DOWN))
				{
					ButtonSprite down = new ButtonSprite(x, y, ResourcesManager.getInstance().arrow_btn, vbom , MazeScene.this);
					down.setRotation(90);
					down.setTag(DOWN);
					MazeScene.this.registerTouchArea(down);
					levelObject = down;
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_RIGHT))
				{
					ButtonSprite right = new ButtonSprite(x, y, ResourcesManager.getInstance().arrow_btn, vbom , MazeScene.this);
					right.setTag(RIGHT);
					MazeScene.this.registerTouchArea(right);
					levelObject = right;
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEFT))
				{
					ButtonSprite left = new ButtonSprite(x, y, ResourcesManager.getInstance().arrow_btn, vbom , MazeScene.this);
					left.setRotation(180);
					left.setTag(LEFT);
					MazeScene.this.registerTouchArea(left);
					levelObject = left;
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SUBMIT))
				{
					submit = new ButtonSprite(x, y, ResourcesManager.getInstance().submit_btn, vbom , MazeScene.this);
					submit.setTag(SUBMIT);
					MazeScene.this.registerTouchArea(submit);
					levelObject = submit;
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_RESET))
				{
					 reset = new ButtonSprite(x, y, ResourcesManager.getInstance().reset_btn, vbom , MazeScene.this);
					reset.setTag(RESET);
					MazeScene.this.registerTouchArea(reset);
					levelObject = reset;
					reset.setVisible(false);
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEVEL_COMPLETE))
				{
				    levelObject = new Sprite(x, y, resourcesManager.complete_stars_region, vbom)
			    {
				        
				    };
				    levelObject.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));
				}
				else
				{
					throw new IllegalArgumentException();
				}

				levelObject.setCullingEnabled(true);

				return levelObject;
			}
			
			
		
		});

		levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
	}
	
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,float pTouchAreaLocalY) 
	{
		//System.out.println("button Clicked");
		
		if(pButtonSprite.getTag() == RIGHT)
		{
			Player.pathWayArr.add('r');
			System.out.println("button Right");
			
		}
		else if(pButtonSprite.getTag() == UP)
		{
			System.out.println("upppp");
			Player.pathWayArr.add('u');
			
			//moveChar('u');
		}
		else if(pButtonSprite.getTag() == DOWN)
		{
			System.out.println("button Down");
			Player.pathWayArr.add('d');
			
			//moveChar('d');
		}
		else if(pButtonSprite.getTag() == LEFT)
		{
			System.out.println("button Left");
			Player.pathWayArr.add('l');
			
			//moveChar('l');
		}
		else if(pButtonSprite.getTag() == SUBMIT)
		{
			submit.setVisible(false);
			reset.setVisible(true);
			
			System.out.println("Submit: "+Player.pathWayArr.size());
			
				for (int i = 0; i < Player.pathWayArr.size() ; i++) 
				{
					System.out.println("--> "+Player.pathWayArr.get(i));
					if(i>= level_one_sol.size())
					{
						wrongStep = i ;
						solved=false;
						break;
					}
					else if(Player.pathWayArr.get(i).equals(level_one_sol.get(i)))
					{
						solved=true;
					}
					else 
					{
						wrongStep=i;
						solved=false;
						break;
					}
				}
			
			
			if(Player.pathWayArr.size() != 0)
			{
				
				if(wrongStep == 0 )
					player.animateChar(Player.pathWayArr.get(Player.charArrIndex));
				else
				player.moveChar(Player.pathWayArr.get(Player.charArrIndex));
			}
	}
		else if(pButtonSprite.getTag() == RESET)
		{

			submit.setVisible(true);
			reset.setVisible(false);
			System.out.println("reset pressed");
			player.resetPlayer();
		}
	}

	public static void removeCoin()
	{
		coin.detachSelf();
		coin.dispose();
	}
	
	public static void showHint()
	{
		ResourcesManager.getInstance().activity.runOnUiThread(new Runnable() {
	        public void run() {
	         String w="";
	          if(wrongStep < level_one_sol.size())
	          {
	        	if(Player.pathWayArr.get(wrongStep).equals('u'))
	        		w="Up";
	        	else if(Player.pathWayArr.get(wrongStep).equals('d'))
	        		w="Down";
	        		else if(Player.pathWayArr.get(wrongStep).equals('r'))
		        		w="Right";
	        		else if(Player.pathWayArr.get(wrongStep).equals('l'))
	        		w="Left";
	        	 AlertDialog.Builder alert1 = new AlertDialog.Builder(ResourcesManager.getInstance().activity);
	             alert1.setTitle("Here's a tip");
	             alert1.setMessage("Sure you need to do a "+ w + " turn");
	          
//	             alert1.setNeutralButton("Try again",new OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						Player.charArrIndex = 0;
//						Player.pathWayArr.clear();
//					}
//				});
	             alert1.show();
	          }
	          }
		});

	}

	

	private void decreaseTime(int i)
	{
	    timer -= i;
	    timeText.setText("Time: " + timer);
	}
	
	
	private void createGameOverText()
	{
	    gameOverText = new Text(0, 0, resourcesManager.font, "Game Over!", vbom);
	}

	private void displayGameOverText()
	{
	    camera.setChaseEntity(null);
	    gameOverText.setPosition(camera.getCenterX(), camera.getCenterY());
	    attachChild(gameOverText);
	    gameOverDisplayed = true;
	}
	
	 
	private void timerCounting()
	{
	engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback()
    {                                    
        public void onTimePassed(final TimerHandler pTimerHandler)
        {
            pTimerHandler.reset();
            
            if(timer == 0 && !gameOverDisplayed)
            {
                    displayGameOverText();
                    engine.unregisterUpdateHandler(pTimerHandler);
            }
            else	
            decreaseTime(1);
        }
    }));
	}

}
