package com.mk.apps.superm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.mk.apps.superm.models.TextAniActor;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.game.AbstractGame;
import com.moribitotech.mtx.managers.SettingsManager;
import com.moribitotech.mtx.screen.AbstractScreen;

public class MenuScreen extends AbstractScreen {

	public MenuScreen(AbstractGame game, String screenName) {
		super(game, screenName);
		// TODO Auto-generated constructor stub
		// Gdx.input.setCatchBackKey(true);

		setUpElements();
	}

	private void setUpElements() {

		Texture bgTexture = new Texture(
				Gdx.files.internal("bg2.png"));

		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(bgTexture, 0, 0, 1280, 720);
		Sprite	sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2); 
//		getStage().addActor(sprite);
		Group background = new Group();
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		getStage().addActor(background);
		Image image = new Image(region);
		background.addActor(image);
		
		setBackgroundTexture(region);

		TextureRegion warning1 = new TextureRegion(new Texture(
				"data/warning1.png"), 0, 0, 150, 50);
		TextureRegion warning2 = new TextureRegion(new Texture(
				"data/warning2.png"), 0, 0, 150, 50);
		TextureRegion textWarning1 = new TextureRegion(new Texture(
				"data/keep_calm2.png"), 0, 0, 300, 100);
		TextureRegion textWarning2 = new TextureRegion(new Texture(
				"data/keep_calm1.png"), 0, 0, 300, 100);
		Animation warningAnim = new Animation(1 / 4f, warning1, warning2);
		Animation warningTxtAnim = new Animation(1 / 4f, textWarning1, textWarning2);

		TextAniActor warnigActor = new TextAniActor(
				Gdx.graphics.getWidth() / 2 - 75,
				Gdx.graphics.getHeight()/2 + 200, 150, 50);
		
		
		warnigActor.setAnimation(warningAnim, true, true);
		getStage().addActor(warnigActor);
		
		
		TextAniActor warnigTextActor = new TextAniActor(
				Gdx.graphics.getWidth() / 2 - 150,
				Gdx.graphics.getHeight()/2 + 100, 300, 100);
		warnigTextActor.setAnimation(warningTxtAnim, true, true);

		getStage().addActor(warnigTextActor);
		FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
		Skin skin = new Skin(skinFile);
		Table tableButtons1 = MenuCreator.createTable(false, skin);
		TextureRegion btnAnswer2 = new TextureRegion(new Texture(
				"data/testbutton.png"), 0, 0, 192, 75);
		TextureRegion btnAnswer2Pressed = new TextureRegion(new Texture(
				"data/button.png"), 0, 0, 192, 75);
		BitmapFont font = new BitmapFont(Gdx.files.internal("super_mario.fnt"),
				false);
		font.setColor(Color.WHITE);
		font.setScale(0.75f);
		ButtonGame btnPlay = MenuCreator.createCustomGameButton(font,
				 btnAnswer2Pressed,btnAnswer2);
		btnPlay.setSize(192, 75);
		btnPlay.setText("Play", true);
		btnPlay.setTextPosXY(40, btnPlay.getHeight() / 2 + 15);
		btnPlay.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);

				// Toggle the button, and change animation of abstract actor
				getGame().setScreen(new LevelsScreen(getGame(), "levels"));

			}
		});
		tableButtons1.add(btnPlay).pad(15);
		tableButtons1.setPosition(
				getStage().getWidth() *2/3,
				getStage().getHeight() / 2 - tableButtons1.getHeight() / 2
						);

		getStage().addActor(tableButtons1);

		Table tableButtons2 = MenuCreator.createTable(false, skin);

		ButtonGame btnExit = MenuCreator.createCustomGameButton(font,
				 btnAnswer2Pressed,btnAnswer2);
		btnExit.setSize(192, 75);
		btnExit.setText("Exit", true);
		btnExit.setTextPosXY(40, btnExit.getHeight() / 2 + 15);
		btnExit.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);

				// Toggle the button, and change animation of abstract actor
				// getGame().setScreen(new LevelsScreen(getGame(), "levels"));
				Gdx.app.exit();

			}
		});
		tableButtons2.add(btnExit).pad(15);
		tableButtons2.setPosition(
				getStage().getWidth() *2/3,
				getStage().getHeight() / 2 - tableButtons1.getHeight() / 2
						- 100);

		getStage().addActor(tableButtons2);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		getStage().dispose();
		super.dispose();
	}

	@Override
	public void keyBackPressed() {
		// TODO Auto-generated method stub
		// super.keyBackPressed();
		Gdx.app.exit();
	}

}
