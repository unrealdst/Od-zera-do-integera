package com.me.OZDI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.OZDI.OZDI;
import com.me.OZDI.entities.Player;

public class Play implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	OZDI game;
	public Play(OZDI game){
        this.game = game;
	 }
	
	private Player player;
	//private fight fight;
	@Override
	public void render(float delta) {
		handleInput();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		
		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		
		renderer.getSpriteBatch().end();
		
		
	}

	 
	
	private void handleInput() {
		int predkosc=100;
        //
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
        {
        	Vector2 vel=new Vector2();
        	vel.x=-predkosc;
        	vel.y=0;
            player.setVelocity(vel); 
            czekout();
            cameraSetOnPLayer();
                			
             
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
        { 
        	Vector2 vel=new Vector2();
        	vel.x=predkosc;
        	vel.y=0;
            player.setVelocity(vel); 
            czekout();
            cameraSetOnPLayer();             
         
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
        {
                
        	Vector2 vel=new Vector2();
        	vel.x=0;
        	vel.y=-predkosc;
            player.setVelocity(vel); 
            czekout();
            cameraSetOnPLayer();
                        
                
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) 
        {
        	Vector2 vel=new Vector2();
        	vel.x=0;
        	vel.y=predkosc;
            player.setVelocity(vel); 
            czekout();
            cameraSetOnPLayer();
    		
        }	
        //
	}

	private void czekout() 
	{
		 if(player.getDoor())
         {
         	changeMap(player.getLevel());
         	setNewLocatione(player.getDoorX(),player.getDoorY());
         }
         if(player.getFight())
         {
        	 System.out.println("huh");
        	 game.setScreen(game.fightScreen);
         }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		camera.viewportWidth=width/3;
		camera.viewportHeight=height/3;

	}
	
	private void cameraSetOnPLayer()
	{
		camera.position.x=player.getX();
		camera.position.y=player.getY();
		camera.update();     
	}

	@Override
	public void show() {
		//int x=5,y=5;
		changeMap("map3.tmx");
		
		
		camera= new OrthographicCamera();
		System.out.println(map.getLayers());
		try{player=new Player(new Sprite(new Texture("data/player.png")),(TiledMapTileLayer) map.getLayers().get(0));}catch(NullPointerException e){}
		setNewLocatione(5,10);
		
		Gdx.input.setInputProcessor(player);
	}

	
	private void changeMap(String mapPath)
	{		
		
		map= new TmxMapLoader().load("data/"+mapPath);
		renderer = new OrthogonalTiledMapRenderer(map);
		
	}
	
	private void setNewLocatione(int x,int y)
	{
		try{player.setPosition((float)x*player.getCollisionLayer().getTileWidth(),(float)y*player.getCollisionLayer().getTileHeight());}catch(NullPointerException e){System.out.println("Null");}
	}
	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();
	}

}
