package com.mk.apps.superm.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moribitotech.mtx.AbstractActor;

public class PlayerActor extends AbstractActor {
	public PlayerActor(TextureRegion textureRegion, boolean isTextureRegionActive,
			float posX, float posY, float width, float height) {
		super(textureRegion, isTextureRegionActive, posX, posY, width, height);
	}

	public PlayerActor(float posX, float posY, float width, float height) {
		super(posX, posY, width, height);
	}
}
