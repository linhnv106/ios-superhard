package com.mk.apps.superm;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.mk.apps.superm.screens.CompleteScreen;
import com.mk.apps.superm.screens.LevelsScreen;
import com.mk.apps.superm.screens.MenuScreen;
import com.moribitotech.mtx.game.AbstractGame;
import com.moribitotech.mtx.settings.AppSettings;

public class MainStarter extends AbstractGame implements ApplicationListener {
	private IActivityRequestHandler iActivityRequestHandler;
	
	public MainStarter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MainStarter(IActivityRequestHandler iActivityRequestHandler) {
		super();
		this.iActivityRequestHandler = iActivityRequestHandler;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		super.create();

		setScreen(new MenuScreen(this, "game screen"));
		if(iActivityRequestHandler!=null){
			iActivityRequestHandler.showAds(true);
		}
//		setScreen(new CompleteScreen(this, "", 3));
	}

	@Override
	public void setUpAppSettings() {
		// TODO Auto-generated method stub
		
		AppSettings.setUp(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1280, 720);

	}
	

	@Override
	public void resume() {
		super.resume();
		
	}

	@Override
	public void setUpAssets() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUpLoadingScreen() {
		// TODO Auto-generated method stub
		
	}

	public interface IActivityRequestHandler {
		   public void showAds(boolean show);
		}

	
}
