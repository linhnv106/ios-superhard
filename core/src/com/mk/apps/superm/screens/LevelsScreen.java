package com.mk.apps.superm.screens;

import net.dermetfan.tiledMapGame.screens.Play;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mk.apps.superm.models.PlayerActor;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.game.AbstractGame;
import com.moribitotech.mtx.managers.SettingsManager;
import com.moribitotech.mtx.scene2d.ui.ButtonLevel;
import com.moribitotech.mtx.screen.AbstractScreen;

public class LevelsScreen extends AbstractScreen {

	public LevelsScreen(AbstractGame game, String screenName) {
		super(game, screenName);
		// TODO Auto-generated constructor stub
		setUpElements();
		Gdx.input.setCatchBackKey(true);

	}

	private void setUpElements() {
		Texture bgTexture = new Texture(
				Gdx.files.internal("bg2.png"));

		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(bgTexture, 0, 0, 1280, 720);

		setBackgroundTexture(region);

		BitmapFont font = new BitmapFont(Gdx.files.internal("super_mario.fnt"),
				false);
		font.setColor(Color.WHITE);
		font.setScale(0.75f);
		TextureRegion btnBack1 = new TextureRegion(new Texture(
				"data/testbutton.png"), 0, 0, 192, 75);
		TextureRegion  btnBack2 = new TextureRegion(new Texture(
				"data/button.png"), 0, 0, 192, 75);

		ButtonGame btnBack = MenuCreator.createCustomGameButton(font,
				 btnBack2,  btnBack1);
		btnBack.setSize(192, 75);
		btnBack.setText("BACK", true);
		btnBack.setTextPosXY(40, btnBack.getHeight() / 2 + 15);
		btnBack.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);

				// Toggle the button, and change animation of abstract actor
				getGame().setScreen(new MenuScreen(getGame(), ""));

			}
		});

		btnBack.setPosition(20, Gdx.graphics.getHeight() - 90);
		getStage().addActor(btnBack);

		FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
		Skin skin = new Skin(skinFile);
		Table levelsTable1 = MenuCreator.createTable(true, skin);
		levelsTable1.setPosition(10, 0);
		levelsTable1.addAction(Actions.moveTo(0, 0, 0.7f));
		levelsTable1.top().left().pad(70, 30, 3, 30);

		// Add to stage
		// ######################################################################
		getStage().addActor(levelsTable1);

		// Add levels buttons
		// Normally get this number from textfiles or database
		// ######################################################################
		int numberOfLevels = 20;
		TextureRegion btnAnswer2 = new TextureRegion(new Texture(
				"data/lv2.png"), 0, 0, 100, 100);
		TextureRegion lockedLevel = new TextureRegion(new Texture(
				"data/lock2.png"), 0, 0, 100, 100);
		TextureRegion btnAnswer2Pressed = new TextureRegion(new Texture(
				"data/lv1.png"), 0, 0, 100, 100);
		 final int unlocked=
		 SettingsManager.getIntegerPrefValue("unlocked",0);
//		final int unlocked = 20;
		// Create buttons with a loop
		for (int i = 0; i < numberOfLevels; i++) {
			// 1. Create level button
			final ButtonLevel levelButton;
			if (i <= unlocked) {
				levelButton = new ButtonLevel(new TextureRegionDrawable(
						btnAnswer2), new TextureRegionDrawable(
						btnAnswer2Pressed), font);
				levelButton.setLevelNumber(i + 1, font);
			} else {
				levelButton = new ButtonLevel(new TextureRegionDrawable(
						lockedLevel), new TextureRegionDrawable(lockedLevel),
						font);
			}
			// 2. Set level number

			// 3. Set lock condition (get from database if it is locked or not
			// and lock it)
			// use if/else here to lock or not
			// levelButton.setTextureLocked(Assets.btnBatLocked, true);

			// 4. Set stars or any other achievements (get from database or text
			// files here)
			// I just made a random number of earned stars
			// Random rnd = new Random();
			// levelButton.setLevelStars(Assets.imgStarHolder, Assets.imgStar,
			// 3,
			// rnd.nextInt(3) + 1);

			// 5. Add listener
			// Add button listener to go to a level (gamascreen)
			final int current = i;
			levelButton.addListener(new ActorGestureListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					// super.touchUp(event, x, y, pointer, button);
					if (current <= unlocked) {
						 getGame().setScreen(new Play(getGame(),current+1));

					}
				}
			});

			// 6. Add row after each 5 level button to go down or how many do
			// you need
			if (i % 5 == 0) {
				levelsTable1.row();
			}

			// Add to table
			levelsTable1.add(levelButton).size(96, 96).pad(5, 5, 5, 5)
					.expand();
		}

	}

	@Override
	public void keyBackPressed() {
		// TODO Auto-generated method stub
		// super.keyBackPressed();
		getGame().setScreen(new MenuScreen(getGame(), ""));
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		getStage().dispose();
		super.dispose();
	}

}
