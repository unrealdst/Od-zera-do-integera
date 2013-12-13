package com.me.OZDI.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor
{
	private Vector2 velocity=new Vector2();
	
	private float speed=1000;
	
	private TiledMapTileLayer collisionLayer;
	
	public Player(Sprite sprite,TiledMapTileLayer collisionLayer)
	{
		super(sprite);
		this.collisionLayer=collisionLayer;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch)
	{
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	private void update(float delta) 
	{
		float oldX=getX(),oldY=getY(),tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		boolean collisionX=false,collisionY=false;
		setX(getX()+velocity.x*delta);	
		if(velocity.x<0)
		{
			//top left
			collisionX=collisionLayer.getCell((int)(getX()/tileWidth),(int)((getY()+getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");
			//middle left
			if(!collisionX)
				collisionX=collisionLayer.getCell((int)(getX()/tileWidth),(int)((getY()+getHeight()/2)/tileHeight)).getTile().getProperties().containsKey("blocked");
			//bottom left
			if(!collisionX)
				collisionX=collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().containsKey("blocked");
		}
		else if(velocity.x>0)
		{
			//top right
			try{collisionX=collisionLayer.getCell((int)((getX()+getWidth())/tileWidth),(int)((getY()+getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");}catch(NullPointerException e){}
			//middle right
			if(!collisionX)
				try{collisionX=collisionLayer.getCell((int)((getX()+getWidth())/tileWidth),(int)((getY()+getHeight()/2)/tileHeight)).getTile().getProperties().containsKey("blocked");}catch(NullPointerException e){}
			//bottom rigtrht
			if(!collisionX)
				try{collisionX=collisionLayer.getCell((int)((getX()+getWidth())/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().containsKey("blocked");}catch(NullPointerException e){}
		}
		//reakcja na kolizje X
		if(collisionX){setX(oldX);velocity.x=0;}
		
		setY(getY()+velocity.y*delta);
		if(velocity.y<0)
		{
			//bottom left
			collisionY=collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().containsKey("blocked");
			//System.out.println("oldX: "+oldX+"x: "+(int)(getX()/tileWidth)+" y: "+(int)(getY()/tileHeight));
			//botom middle
			if(!collisionY)
				{
				try{
				collisionY=collisionLayer.getCell((int)(getX()+getWidth()/2),(int)(getY()/tileHeight)).getTile().getProperties().containsKey("blocked");
				}catch(NullPointerException e){}
				}
			//botom right
			if(!collisionY)
				collisionY=collisionLayer.getCell((int)((getX()+getWidth())/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().containsKey("blocked");
		}
		else if(velocity.y>0)
		{
			//top left
			try{collisionY=collisionLayer.getCell((int)(getX()/tileWidth),(int)((getY()+getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");}catch(NullPointerException e){}
			//top middle
			if(!collisionY)
				{
				try{collisionY=collisionLayer.getCell((int)(getX()+getWidth()/2),(int)((getY()+getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");}catch(NullPointerException e){}
				}
			//top right
			if(!collisionY)
				{
				try{collisionY=collisionLayer.getCell((int)((getX()+getWidth())/tileWidth),(int)((getY()+getHeight())/tileHeight)).getTile().getProperties().containsKey("blocked");}catch(NullPointerException e){}
				}
		}
		//reakcja na kolizje Y
		if(collisionY)
		{
			setY(oldY);
			velocity.y=0;
		}
		velocity.x=0;
		velocity.y=0;
	}
	
	public TiledMapTileLayer getCollisionLayer(){return collisionLayer;}
	public Vector2 getVelocity() {return velocity;}
	public void setVelocity(Vector2 velocity) {this.velocity = velocity;}
	public float getSpeed(){return speed;}
	public void setSpeed(float speed){this.speed = speed;}
	public void setCollisionLayer(TiledMapTileLayer collisionLayer){this.collisionLayer = collisionLayer;}
	
	public boolean getDoor()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		if(collisionLayer.getCell( (int)(getX()/tileWidth),(int)(getY()/tileHeight) ).getTile().getProperties().containsKey("map") )
		{
			return true;
		}
		else
			return false;
	}
	public String getLevel()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		return (String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("map");
	}
	public int getDoorX()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		return Integer.parseInt((String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("x"));
	}
	public int getDoorY()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		return Integer.parseInt((String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("y"));
	}
	
	public boolean getFight()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		if(collisionLayer.getCell( (int)(getX()/tileWidth),(int)(getY()/tileHeight) ).getTile().getProperties().containsKey("fight") )
		{
			return true;
		}
		else
			return false;
	}
	
	public int getMonsterHP()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		return Integer.parseInt((String)collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("MonsterHP"));
	}
	public int getMonsterATC()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		//System.out.println(Integer.parseInt((String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("MonsterATC")));
		return Integer.parseInt((String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("MonsterATC"));
	}
	public int getMonsterEXP()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		return  Integer.parseInt((String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("MonsterEXP"));
	}
	public int getMonsterDEF()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		return  Integer.parseInt((String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("MonsterDEF"));
	}

	public String getMonsterName()
	{
		float tileWidth=collisionLayer.getTileWidth(),tileHeight=collisionLayer.getTileHeight();
		return (String) collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()/tileHeight)).getTile().getProperties().get("MonsterName");
	}
	//TODO wywalic potem
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode)
		{
		case Keys.W:
			velocity.y=speed;
			break;
		case Keys.S:
			velocity.y=-speed;
			break;
		case Keys.A:
			velocity.x=-speed;
			break;
		case Keys.D:
			velocity.x=speed;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) 
	{		
		switch(keycode)
		{
			case Keys.A:
			case Keys.D:
				velocity.x=0;
				break;
			case Keys.W:
			case Keys.S:
				velocity.y=0;
				break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {return false;}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {return false;}

	@Override
	public boolean scrolled(int amount) {return false;}

}
