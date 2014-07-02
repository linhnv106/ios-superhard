package com.mk.apps.superm.screens;

import net.dermetfan.tiledMapGame.screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.mk.apps.superm.models.TextAniActor;
import com.moribitotech.mtx.ButtonGame;
import com.moribitotech.mtx.MenuCreator;
import com.moribitotech.mtx.game.AbstractGame;
import com.moribitotech.mtx.managers.SettingsManager;
import com.moribitotech.mtx.screen.AbstractScreen;

public class CompleteScreen extends AbstractScreen{
	private int unlocked;
	public CompleteScreen(AbstractGame game, String screenName,int unlocked) {
		super(game, screenName);
		// TODO Auto-generated constructor stub
//		 Gdx.input.setCatchBackKey(true);
		this.unlocked=unlocked;
		setUpElements();
	}

	private void setUpElements(){
		int _unlock =SettingsManager.getIntegerPrefValue("unlocked", 1);
		if(_unlock<=unlocked){
			SettingsManager.setIntegerPrefValue("unlocked", unlocked);

		}
		Texture bgTexture = new Texture(Gdx.files.internal("bg2.png"));

		
		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(bgTexture, 0, 0, 1280, 720);

		setBackgroundTexture(region);
	
		FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
		Skin skin = new Skin( skinFile );		 
        Table tableButtons1 = MenuCreator.createTable(false,skin);
        TextureRegion btnAnswer2= new TextureRegion(new Texture("data/testbutton.png"), 0, 0, 192, 75);
        TextureRegion btnAnswer2Pressed= new TextureRegion(new Texture("data/button.png"), 0, 0, 192, 75);
        BitmapFont font = new BitmapFont(Gdx.files.internal("super_mario.fnt"), false);
        font.setColor(Color.WHITE);
        font.setScale(0.5f);
        ButtonGame btnPlay = MenuCreator.createCustomGameButton(font,  btnAnswer2Pressed,btnAnswer2);
        btnPlay.setSize(192, 75);
        btnPlay.setText("CONTINUE", true);
        btnPlay.setTextPosXY(40, btnPlay.getHeight()/2+12);
        btnPlay.addListener(new ActorGestureListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);

				// Toggle the button, and change animation of abstract actor
				getGame().setScreen(new Play(getGame(), unlocked));
			
			}});
		tableButtons1.add(btnPlay).pad(15);
		tableButtons1.setPosition(getStage().getWidth() / 2 - tableButtons1.getWidth() /2, getStage().getHeight()/2-tableButtons1.getHeight()/2-100);
		getStage().addActor(tableButtons1);
		
        Table tableButtons2 = MenuCreator.createTable(false,skin);

		  ButtonGame btnExit = MenuCreator.createCustomGameButton(font,  btnAnswer2Pressed,btnAnswer2);
		  btnExit.setSize(192, 75);
		  btnExit.setText("MAIN", true);
		  btnExit.setTextPosXY(60, btnExit.getHeight()/2+12);
		  btnExit.addListener(new ActorGestureListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					super.touchUp(event, x, y, pointer, button);

					// Toggle the button, and change animation of abstract actor
					getGame().setScreen(new MenuScreen(getGame(), "levels"));
//					Gdx.app.exit();
				
				}});
	        tableButtons2.add(btnExit).pad(15);
	        tableButtons2.setPosition(getStage().getWidth() / 2 - tableButtons2.getWidth() /2, getStage().getHeight()/2-tableButtons1.getHeight()/2-200);

			getStage().addActor(tableButtons2);
			
			
			//text only
//			 Table tableButtons3 = MenuCreator.createTable(false,skin);
		        TextureRegion btnTransparrent= new TextureRegion(new Texture("data/button_transparent.png"), 0, 0, 192, 75);
		        TextureRegion btnTransparrent2= new TextureRegion(new Texture("data/button_transparent.png"), 0, 0, 192, 75);
		        BitmapFont font2 = new BitmapFont(Gdx.files.internal("super_mario.fnt"), false);
		        font2.setColor(Color.ORANGE);
		        font2.setScale(0.75f);
//		        ButtonGame lableCongrat = MenuCreator.createCustomGameButton(font2, btnTransparrent, btnTransparrent);
//		        lableCongrat.setSize(192, 75);
//		        lableCongrat.setText("CONGRATULATION !", true);
//		        lableCongrat.setTextPosXY(40, lableCongrat.getHeight()/2+15);
//		        lableCongrat.addListener(new ActorGestureListener() {
//					@Override
//					public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//						super.touchUp(event, x, y, pointer, button);
//
//						// Toggle the button, and change animation of abstract actor
//						getGame().setScreen(new LevelsScreen(getGame(), "levels"));
//					
//					}});
//		        tableButtons3.add(lableCongrat).pad(15);
//		        tableButtons3.setPosition(getStage().getWidth() / 2 - lableCongrat.getWidth()/2, getStage().getHeight()/2+100);
//				getStage().addActor(tableButtons3);
				
			TextureRegion warning1 = new TextureRegion(new Texture(
					"data/congrat1.png"), 0, 0, 250, 75);
			TextureRegion warning2 = new TextureRegion(new Texture(
					"data/congrat2.png"), 0, 0, 250, 75);
		
			Animation warningAnim = new Animation(1 / 4f, warning1, warning2);

			TextAniActor warnigActor = new TextAniActor(
					getStage().getWidth() / 2 - 125, getStage().getHeight()/2+100, 250, 75);
			
			
			warnigActor.setAnimation(warningAnim, true, true);
			getStage().addActor(warnigActor);
			
		        Table tableButtons4 = MenuCreator.createTable(false,skin);

				  ButtonGame labelUnlocked = MenuCreator.createCustomGameButton(font2, btnTransparrent, btnTransparrent);
				  labelUnlocked.setSize(192, 75);
				  labelUnlocked.setText("LEVEL " +unlocked+" IS UNLOCKED!", true);
				  labelUnlocked.setTextPosXY(45, labelUnlocked.getHeight()/2+15);
				  labelUnlocked.addListener(new ActorGestureListener() {
						@Override
						public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
							super.touchUp(event, x, y, pointer, button);

							// Toggle the button, and change animation of abstract actor
							getGame().setScreen(new MenuScreen(getGame(), "levels"));
//							Gdx.app.exit();
						
						}});
			        tableButtons4.add(labelUnlocked).pad(15);
			        tableButtons4.setPosition(getStage().getWidth() / 2 - labelUnlocked.getWidth() , getStage().getHeight()/2+50);

					getStage().addActor(tableButtons4);
			
			
			
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
//		super.keyBackPressed();
		Gdx.app.exit();
	}
	


}
