package net.dermetfan.tiledMapGame.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mk.apps.superm.models.BackUpTile;
import com.mk.apps.superm.screens.CompleteScreen;
import com.mk.apps.superm.screens.MenuScreen;
import com.moribitotech.mtx.game.AbstractGame;

public class Player extends Sprite implements InputProcessor {

	/** the movement velocity */
	private Vector2 velocity = new Vector2();

	private float speed = 60 * 2, gravity = 60 * 1.8f, animationTime = 0, increment;

	private boolean canJump;

	private Animation still, left, right;
	private TiledMapTileLayer collisionLayer;

	private String blockedKey = "grounded";
	private String deadKey="dead";
	private String helpKey="help";
	private String goneKey="gone";
	private String goalKey="goal";
	private Texture koalaTexture;
	private Animation stand;
	private Animation walk;
	private Animation jump;
	private Animation dead;
	private long waitTime;
	boolean started=false;
	private int level=0;
	private int deads;
	TiledMap map;
	Sound jumpSound;
	Sound hitSound;
	Sound winSound;
	long delayTime=0;
	List<BackUpTile> backUpTiles;
	AbstractGame game;
	boolean isComplete;
	IPlayerListener listener;
	public Player(AbstractGame game,Animation still, TiledMap map,IPlayerListener listener) {
		super(still.getKeyFrame(0));
//		 Gdx.input.setCatchBackKey(true);
		this.listener=listener;
		this.game=game;
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/sound/jump.mp3"));
		hitSound=Gdx.audio.newSound(Gdx.files.internal("data/sound/hit.mp3"));
		winSound=Gdx.audio.newSound(Gdx.files.internal("data/sound/win.mp3"));
		this.still = still;
		this.left = left;
		this.right = right;
		this.map=map;
		this.collisionLayer = (TiledMapTileLayer) map.getLayers().get(1);
		backUpTiles= new ArrayList<BackUpTile>();
		Texture textureWalk=new Texture("data/character2.png");
		TextureRegion[][] regionsWalk = TextureRegion.split(textureWalk,32, 32);
		
		walk = new Animation(1/8f, regionsWalk[2][2],regionsWalk[2][1],regionsWalk[2][0]);
//		walk = new Animation(1/16f, regionsWalk[2],regionsWalk[3],regionsWalk[4],regionsWalk[5],regionsWalk[6],regionsWalk[7],regionsWalk[0],regionsWalk[1]);
		setSize(regionsWalk[2][0].getRegionWidth()/ 1.5f,regionsWalk[2][0].getRegionHeight()/1.5f);
		jump=new Animation(1/2f, regionsWalk[2][2]);
		stand=new Animation(1/2f, regionsWalk[2][0]);
		dead=new Animation(1/2f, regionsWalk[0][0]);
		walk.setPlayMode(Animation.PlayMode.LOOP);
	}

	@Override
	public void draw(Batch batch) {
		// TODO Auto-generated method stub
		if(!isComplete){
			update(Gdx.graphics.getDeltaTime());
		}
		super.draw(batch);
	}

//	@Override
//	public void draw(SpriteBatch spriteBatch) {
//		if(!isComplete){
//			update(Gdx.graphics.getDeltaTime());
//		}
//		super.draw(spriteBatch);
//	}



	public void update(float delta) {
		// apply gravity
		if(!started){
			this.collisionLayer = (TiledMapTileLayer) map.getLayers().get(1);
			setX(0);
		}
		
		velocity.y -= gravity * delta;
		// clamp velocity
		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < -speed)
			velocity.y = -speed;
		if(velocity.x==0){
//			velocity.x=speed;
		}
		// save old position
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		boolean deadX = false, deadY = false;
		boolean goalX=false,goalY=false;
		// move on x
		setX(getX() + velocity.x * delta);

		// calculate the increment for step in #collidesLeft() and #collidesRight()
		increment = collisionLayer.getTileWidth();
		increment = getWidth() < increment ? getWidth() / 2 : increment / 2;

		if(velocity.x < 0){// going left
			collisionX = collidesLeft(blockedKey);
			deadX = collidesLeft(deadKey);
			collidesLeft(helpKey);
			goalX=collidesLeft(goalKey);
		}else if(velocity.x > 0){ // going right
			collisionX = collidesRight(blockedKey);
			deadX = collidesRight(deadKey);
			collidesRight(helpKey);
			goalX=collidesRight(goalKey);
		}
		// react to x collision
		if(collisionX) {
			setX(oldX);
			velocity.x = 0;
		}
		
		// move on y
		setY(getY() + velocity.y * delta * 5f);

		// calculate the increment for step in #collidesBottom() and #collidesTop()
		increment = collisionLayer.getTileHeight();
		increment = getHeight() < increment ? getHeight() / 2 : increment / 2;

		if(velocity.y < 0){ // going down
			canJump = collisionY = collidesBottom(blockedKey);
			deadY = collidesBottom(deadKey);
			collidesBottom(helpKey);
			goalY=collidesBottom(goalKey);
		}else if(velocity.y > 0){ // going up
			collisionY = collidesTop(blockedKey);
			deadY = collidesTop(deadKey);
			collidesTop(helpKey);
			goalY=collidesTop(goalKey);
		// react to y collision
		}if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		// update animation
		animationTime += delta;
		if(deadX||deadY){
			
			
			if(TimeUtils.millis()-delayTime>1500){
				delayTime=TimeUtils.millis();
				started=false;
			}
			deads++;
			setRegion(dead.getKeyFrame(animationTime));
			velocity.x = 0;			
			hitSound.play();
			backupMap();
		}else if(goalX||goalY){
			setRegion(dead.getKeyFrame(animationTime));
			velocity.x = 0;
			winSound.play();
			
//			if(TimeUtils.millis()-delayTime>1500){
//				delayTime=TimeUtils.millis();
				if(!isComplete){
					isComplete=true;
//					listener.onLevelComplete();
				}
			
//			}
		}else if(velocity.y!=0){
			setRegion(jump.getKeyFrame(animationTime));
		}else if(velocity.x>0){
			setRegion(walk.getKeyFrame(animationTime));
		}
		
		
		if(TimeUtils.millis()-waitTime>300){
			waitTime=TimeUtils.millis();
			animatedCell();
		}
//		setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime) : velocity.x > 0 ? right.getKeyFrame(animationTime) : still.getKeyFrame(animationTime));
	}

	
	private List<Vector2> goneTiles = new ArrayList<Vector2>();
	private List<Vector2> helpTiles = new ArrayList<Vector2>();
	
	
	private List<Vector2> movingTiles = new ArrayList<Vector2>();
	
	private void animatedCell(){
		if(movingTiles.size()<=0){
		for(int x = 0; x < collisionLayer.getWidth(); x++)
		for(int y = 0; y < collisionLayer.getHeight(); y++) {
			Cell currentCell = collisionLayer.getCell(x, y);
			Cell tmp=currentCell;
			if(currentCell!=null){
			
			
			MapProperties properties=currentCell.getTile().getProperties();
			if(properties!=null&&properties.containsKey("moving") ){
				
				Vector2 vector2 = new Vector2(x, y);
				movingTiles.add(vector2);
				
//				if(lastValue.equals("left")&&nextValue.equals("right")){//move to right 1 step
//					//
//					properties.put("lastValue", "right");
//					properties.put("nextValue","right");
//					
//					collisionLayer.setCell((int)(x+collisionLayer.getTileWidth()), y, currentCell);
//					collisionLayer.setCell(x, y, null);
//					
//				}else if(lastValue.equals("right")&&nextValue.equals("right")){
//					properties.put("lastValue", "right");
//					properties.put("nextValue","left");
//					
//					collisionLayer.setCell((int)(x+collisionLayer.getTileWidth()), y, currentCell);
//					collisionLayer.setCell(x, y, null);										
//					
//				}else if(lastValue.equals("right")&&nextValue.equals("left")){
//					properties.put("lastValue", "left");
//					properties.put("nextValue", "left");
//					collisionLayer.setCell((int)(x-collisionLayer.getTileWidth()), y, currentCell);
//					collisionLayer.setCell(x, y, null);
//				}else if(lastValue.equals("left")&&nextValue.equals("left")){
//					properties.put("lastValue", "left");
//					properties.put("nextValue", "right");
//					collisionLayer.setCell((int)(x-collisionLayer.getTileWidth()), y, currentCell);
//					collisionLayer.setCell(x, y, null);
//				}
				
				
				
			}
			}
		}
		}
		 List<Vector2> temps = new ArrayList<Vector2>();

		for(Vector2 vector:movingTiles){
			int X=(int)vector.x;
			int Y=(int)vector.y;
			Cell currentCell2 = collisionLayer.getCell(X,Y );
			if(currentCell2!=null){
				MapProperties properties=currentCell2.getTile().getProperties();
				if(properties!=null&&properties.containsKey("moving") ){
					String value= properties.get("moving", String.class);
					if(value.equalsIgnoreCase("right")){
						properties.put("moving", "left");
						collisionLayer.setCell((int)(X+3), Y, currentCell2);
						collisionLayer.setCell((int)(X+4), Y, currentCell2);
						collisionLayer.setCell((int)(X+4), Y+1, currentCell2);

						collisionLayer.setCell((int)(X+3), Y+1, currentCell2);

						collisionLayer.setCell(X, Y, null);
						collisionLayer.setCell(X+1, Y, null);
						collisionLayer.setCell(X+1, Y+1, null);

						collisionLayer.setCell(X, Y+1, null);

						temps.add(new Vector2(X+3, Y));
					}else{
						properties.put("moving", "right");
						collisionLayer.setCell((int)(X-3), Y, currentCell2);
						collisionLayer.setCell((int)(X-3), Y+1, currentCell2);
						collisionLayer.setCell((int)(X-2), Y, currentCell2);
						collisionLayer.setCell((int)(X-2), Y+1, currentCell2);

						collisionLayer.setCell(X, Y, null);
						collisionLayer.setCell(X, Y+1, null);
						collisionLayer.setCell(X+1, Y, null);
						collisionLayer.setCell(X+1, Y+1, null);

						temps.add(new Vector2(X-3, Y));
					}
				}
			}

		}
		
		movingTiles.clear();
		
		movingTiles.addAll(temps);
		
	}
	private boolean isDeadCell(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(deadKey);
	}
	/**
	 * backup map after dead
	 */
	private void backupMap(){
		if(backUpTiles!=null&&backUpTiles.size()>0){
			for(BackUpTile tile:backUpTiles){
				collisionLayer.setCell(tile.getX(), tile.getY(), tile.getCell());
			}
			backUpTiles.clear();
		}
	}
	private boolean isCellBlocked(float x, float y,String key) {
		boolean result=false;
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		result= cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(key);
		if(result&&key.equalsIgnoreCase(helpKey)){
			String tmp =cell.getTile().getProperties().get(helpKey, String.class);
			int helpPosition=Integer.parseInt(tmp);
			backUpTiles.add(new BackUpTile((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()), cell));
			collisionLayer.setCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()),null);
			for(int x1 = 0; x1 < collisionLayer.getWidth(); x1++)
				for(int y1 = 0; y1 < collisionLayer.getHeight(); y1++) {
					Cell currentCell = collisionLayer.getCell(x1, y1);
					if(currentCell!=null){
					
					
					MapProperties properties=currentCell.getTile().getProperties();
					if(properties!=null&&properties.containsKey(goneKey) ){
						 tmp=properties.get(goneKey, String.class);
						int gonePosition=Integer.parseInt(tmp);
						if(helpPosition==gonePosition){
							backUpTiles.add(new BackUpTile(x1, y1, currentCell));
							collisionLayer.setCell(x1, y1, null);
						}
					}
					}
				}
		}
		return result;
	}

	public boolean collidesRight(String key) {
		for(float step = 0; step <= getHeight(); step += increment)
			if(isCellBlocked(getX() + getWidth(), getY() + step,key))
				return true;
		return false;
	}

	public boolean collidesLeft(String key) {
		for(float step = 0; step <= getHeight(); step += increment)
			if(isCellBlocked(getX(), getY() + step,key))
				return true;
		return false;
	}

	public boolean collidesTop(String key) {
		for(float step = 0; step <= getWidth(); step += increment)
			if(isCellBlocked(getX() + step, getY() + getHeight(),key))
				return true;
		return false;

	}

	public boolean collidesBottom(String key) {
		for(float step = 0; step <= getWidth(); step += increment)
			if(isCellBlocked(getX() + step, getY(),key))
				return true;
		return false;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			if(!started){
				velocity.x = speed;
				started=true;
				velocity.y =0;
				
				return true;
			}
			if(canJump) {
				velocity.y = speed / 2.2f;
				velocity.x = speed;
				canJump = false;
				jumpSound.play();
			}
			break;
		case Keys.A:
			velocity.x = -speed;
			animationTime = 0;
			break;
		case Keys.D:
			velocity.x = speed;
			animationTime = 0;
			break;
		case Keys.BACK:
			game.setScreen(new MenuScreen(game, ""));
			return false;
//			break;
			
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.A:
		case Keys.D:
			velocity.x = 0;
			animationTime = 0;
			break;
		case Keys.BACK:
//			game.setScreen(new MenuScreen(game, ""));
			break;
		
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(!started){
			velocity.x = speed;
			started=true;
			velocity.y =0;
			return true;
		}
		if(canJump) {
			velocity.y = speed / 2.2f;
			velocity.x = speed;
			canJump = false;
			jumpSound.play();
		}
//		animatedCell();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//		velocity.x = 0;
//		animationTime = 0;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDeads() {
		return deads;
	}

	public void setDeads(int deads) {
		this.deads = deads;
	}
	
	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public interface IPlayerListener{
		public void onLevelComplete();
	}
}
