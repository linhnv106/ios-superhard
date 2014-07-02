package com.mk.apps.superm.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moribitotech.mtx.AbstractActor;

public class TextAniActor extends AbstractActor {
	public TextAniActor(TextureRegion textureRegion, boolean isTextureRegionActive,
			float posX, float posY, float width, float height) {
		super(textureRegion, isTextureRegionActive, posX, posY, width, height);
	}

	public TextAniActor(float posX, float posY, float width, float height) {
		super(posX, posY, width, height);
	}
}
