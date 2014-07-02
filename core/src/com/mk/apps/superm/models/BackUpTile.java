package com.mk.apps.superm.models;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class BackUpTile {
	private int x;
	private int y;
	private Cell cell;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Cell getCell() {
		return cell;
	}
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public BackUpTile(int x, int y, Cell cell) {
		super();
		this.x = x;
		this.y = y;
		this.cell = cell;
	}
	
}
