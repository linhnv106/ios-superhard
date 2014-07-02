package net.dermetfan.tiledMapGame.screens;

import java.util.Iterator;

import net.dermetfan.tiledMapGame.entities.Player;
import net.dermetfan.tiledMapGame.entities.Player.IPlayerListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.mk.apps.superm.screens.CompleteScreen;
import com.mk.apps.superm.screens.MenuScreen;
import com.moribitotech.mtx.game.AbstractGame;

public class Play implements Screen, IPlayerListener {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private TextureAtlas playerAtlas;
	private Player player;

	private int[] background = new int[] { 0 }, foreground = new int[] { 1 };

	private ShapeRenderer sr;
	private Texture bgTexture;
	private Sprite sprite;
	private SpriteBatch batch;
	public BitmapFont font;
	private SpriteBatch levelBatch;
	private int level = 0;
	private AbstractGame game;
	private boolean isCompleted;

	public Play() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Play(AbstractGame game, int level) {
		super();
		this.level = level;
		this.game = game;

	}

	@Override
	public void render(float delta) {
		// Gdx.gl.glClearColor(0, 0, 0, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (player.isComplete()) {
			onLevelComplete();
			return;
		}
		batch.begin();
		batch.draw(sprite, 0, 0);

		batch.end();
		camera.position.set(player.getX() + player.getWidth() / 2, 150, 0);
		// camera.position.x=player.getX() + player.getWidth() / 2;
		camera.update();

		renderer.setView(camera);

		renderer.render(background);
		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();
		// renderer.getSpriteBatch().setProjectionMatrix(camera.combined);
		//
		renderer.render(foreground);
		levelBatch.begin();
		String lv = "LEVEL : " + level;
		font.draw(levelBatch, lv, Gdx.graphics.getWidth() / 2 - 100,
				Gdx.graphics.getHeight() - 50);
		levelBatch.end();

		//
		// renderer.getSpriteBatch().begin();
		// player.draw(renderer.getSpriteBatch());
		// renderer.getSpriteBatch().end();

		// render objects
		// for(MapObject object : map.getLayers().get("objects").getObjects())
		// if(object instanceof RectangleMapObject) {
		// RectangleMapObject rectObject = (RectangleMapObject) object;
		// Rectangle rect = rectObject.getRectangle();
		// if(rectObject.getProperties().containsKey("gid")) { // if it contains
		// the gid key, it's an image object from Tiled
		// int gid = rectObject.getProperties().get("gid", Integer.class);
		// TiledMapTile tile = map.getTileSets().getTile(gid);
		// renderer.getSpriteBatch().begin();
		// renderer.getSpriteBatch().draw(tile.getTextureRegion(), rect.x,
		// rect.y);
		// renderer.getSpriteBatch().end();
		// } else { // otherwise, it's a normal RectangleMapObject
		// sr.begin(ShapeType.Filled);
		// sr.rect(rect.x, rect.y, rect.width, rect.height);
		// sr.end();
		// }
		// } else if(object instanceof CircleMapObject) {
		// Circle circle = ((CircleMapObject) object).getCircle();
		// sr.begin(ShapeType.Filled);
		// sr.circle(circle.x, circle.y, circle.radius);
		// sr.end();
		// } else if(object instanceof EllipseMapObject) {
		// Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
		// sr.begin(ShapeType.Filled);
		// sr.ellipse(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
		// sr.end();
		// } else if(object instanceof PolylineMapObject) {
		// Polyline line = ((PolylineMapObject) object).getPolyline();
		// sr.begin(ShapeType.Line);
		// sr.polyline(line.getTransformedVertices());
		// sr.end();
		// } else if(object instanceof PolygonMapObject) {
		// Polygon poly = ((PolygonMapObject) object).getPolygon();
		// sr.begin(ShapeType.Line);
		// sr.polygon(poly.getTransformedVertices());
		// sr.end();
		// }
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 3f;
		camera.viewportHeight = height / 3f;
	}

	@Override
	public void show() {
		String levelsName = "data/maps/level" + level + ".tmx";
		if (level <= 0 || level > 20) {
			levelsName = "data/maps/level1.tmx";
		}
		map = new TmxMapLoader().load(levelsName);

		renderer = new OrthogonalTiledMapRenderer(map);
		sr = new ShapeRenderer();
		sr.setColor(Color.CYAN);
		Gdx.gl.glLineWidth(0);

		camera = new OrthographicCamera();
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		camera.setToOrtho(false, w * 50 / h, 50);
		playerAtlas = new TextureAtlas("img/player/player.pack");
		Animation still, left, right;
		still = new Animation(1 / 2f, playerAtlas.findRegions("still"));
		left = new Animation(1 / 6f, playerAtlas.findRegions("left"));
		right = new Animation(1 / 6f, playerAtlas.findRegions("right"));
		still.setPlayMode(Animation.PlayMode.LOOP);
		left.setPlayMode(Animation.PlayMode.LOOP);
		right.setPlayMode(Animation.PlayMode.LOOP);

		player = new Player(game, still, map, this);

		player.setPosition(12 * player.getCollisionLayer().getTileWidth(),
				(player.getCollisionLayer().getHeight() - 4)
						* player.getCollisionLayer().getTileHeight());
		batch = new SpriteBatch();
		if (w > 960) {
			bgTexture = new Texture(Gdx.files.internal("bg1136.jpg"));

		} else {

			bgTexture = new Texture(Gdx.files.internal("bg960.jpg"));

		}

		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(bgTexture, 0, 0, w, h);

		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
		levelBatch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("super_mario.fnt"), false);
		font.setColor(Color.WHITE);
		font.setScale(0.75f);
		Gdx.input.setInputProcessor(player);

		// ANIMATED TILES

		// frames
		Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(2);

		// get the frame tiles
		// Iterator<TiledMapTile> tiles =
		// map.getTileSets().getTileSet("tileSet-hd").iterator();
		// while(tiles.hasNext()) {
		// TiledMapTile tile = tiles.next();
		// if(tile.getProperties().containsKey("animation") &&
		// tile.getProperties().get("animation", String.class).equals("moving"))
		// frameTiles.add((StaticTiledMapTile) tile);
		// }
		//
		// // create the animated tile
		// AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(1 / 3f,
		// frameTiles);
		//
		// // background layer
		// TiledMapTileLayer layer = (TiledMapTileLayer)
		// map.getLayers().get("walls");
		//
		// // replace static with animated tile
		// for(int x = 0; x < layer.getWidth(); x++)
		// for(int y = 0; y < layer.getHeight(); y++) {
		// Cell cell = layer.getCell(x, y);
		// if(cell!=null){
		// if(cell.getTile().getProperties().containsKey("animation") &&
		// cell.getTile().getProperties().get("animation",
		// String.class).equals("moving"))
		// cell.setTile(animatedTile);
		// }
		// }
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		sr.dispose();
		playerAtlas.dispose();
	}

	@Override
	public void onLevelComplete() {
		// TODO Auto-generated method stub
		if (!isCompleted) {

			game.setScreen(new CompleteScreen(game, "", level + 1));
		}
	}

}
