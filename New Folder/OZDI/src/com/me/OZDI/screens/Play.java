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
	private int beforeFightX=5,beforeFightY=10;
	
	private Player player;
	private fight fight;
	
	OZDI game;
	public Play(OZDI game)
	{
		this.game = game;
	}
	
	@Override
	public void render(float delta) 
	{
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
	
	//TODO ogaranac ten syf  
	private void handleInput() 
	{
		int predkosc=100;//predkosc poruszania sie postaci
		if(Gdx.input.isTouched())
		{
			int width=Gdx.graphics.getWidth();
			int height=Gdx.graphics.getHeight();
			int[][]MovePoint=new int[4][2];//4 pkt do ktorych liczona jest odleglosc do dotkniecia
			int[]dlugosc=new int[4];//odlegloosci od dotkniecia do pkt wyznaczonych w MovePoint
			//wspl X					//wspl Y
			MovePoint[0][0]=width/6;	MovePoint[0][1]=height/2;	//lewy
			MovePoint[1][0]=5*width/6;	MovePoint[1][1]=height/2;	//prawy
			MovePoint[2][0]=width/2;	MovePoint[2][1]=height/6;	//gora
			MovePoint[3][0]=width/2;	MovePoint[3][1]=5*height/6;	//dol

			for(int i=0;i<4;i++)
			{
				dlugosc[i]=policzDlugosc(MovePoint[i][0],MovePoint[i][1],Gdx.input.getX(),Gdx.input.getY());
			}
			
			float min=dlugosc[0];
			int minimum=0;//na ktorym miejscu w tablicy dlugosc jest najblirzszy pkt
						  //a to jest odpowiedni element w tablicy MovePoint
			for(int i=0;i<dlugosc.length;i++)
			{
				if(dlugosc[i]<min)
				{
					minimum=i;
					min=dlugosc[i];
				}
			}
			Vector2 vel=new Vector2();
			switch(minimum)
			{
				case 0:
		        	vel.x=-predkosc;
		        	vel.y=0;
		            player.setVelocity(vel); 
		            czekout();
		            cameraSetOnPLayer();
					break;
				case 1:
		        	vel.x=predkosc;
		        	vel.y=0;
		            player.setVelocity(vel); 
		            czekout();
		            cameraSetOnPLayer();
					break;
				case 2:
		        	vel.x=0;
		        	vel.y=predkosc;
		            player.setVelocity(vel); 
		            czekout();
		            cameraSetOnPLayer();
		            break;
				case 3:
		        	vel.x=0;
		        	vel.y=-predkosc;
		            player.setVelocity(vel); 
		            czekout();
		            cameraSetOnPLayer();
					break;
			}
		}
		
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
	}
	/*
	 * metoda wyliczajaca odleglosc pomiedzy dwoma punktami
	 * 
	 * @f,g wspolrzedne pierwszego pkt
	 * @x,y wspolrzene drugiego pkt
	 */
	private int policzDlugosc(int f, int g, int x, int y) 
	{ 
		return  (int) Math.sqrt(((f-x)*(f-x))+((g-y)*(g-y)));
	}

	/*
	 * metoda sprawdza na jakim polu stoi gracz i co z tym zrobic
	 */
	private void czekout() 
	{
		 if(player.getDoor())
         {
			 changeMap(player.getLevel());			
			 setNewLocatione(player.getDoorX(),player.getDoorY());// nadpisanie warstwy kolizji musi byc w tym miejscu
			 													  // zeby nie bylo NullPointera
			 player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
         }
		 //rozpoczacie walki
         if(player.getFight())
         {
        	 fight = new fight(game);
        	 
        	 Gdx.input.setInputProcessor(game.fightScreen);
        	 game.setScreen(game.fightScreen);
        	 
        	 beforeFightX=(int) ((player.getX())/(player.getCollisionLayer().getTileWidth()));
        	 beforeFightY=(int) (player.getY()/player.getCollisionLayer().getTileHeight())-1;
        	 
        	 //ustawienie statow przeciwnika
        	 System.out.println(player.getMonsterATC());
        	 fight.setMonsterAtc(player.getMonsterATC());
        	 //fight.setMonsterDef(player.getMonsterDEF());			//TODO modyfikator obrony
        	 fight.setMonsterHP(player.getMonsterHP());
        	
        	 //ustawienie statow gracza
        	 fight.setPlayerAtc(10);
        	 //fight.setPlayerDef(5);								//TODO modyfikator obrony
        	 fight.setPlayerHP(100);
         }
	}

	@Override
	public void resize(int width, int height) 
	{
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
	public void show() 
	{	
		changeMap("map3.tmx");
		camera= new OrthographicCamera();
		try{player=new Player(new Sprite(new Texture("data/player.png")),(TiledMapTileLayer) map.getLayers().get(0));}catch(NullPointerException e){System.out.println("nullpointer");}
		System.out.println(map.getLayers());
		player.setColor(1, 1, 1, 0.7f);
		setNewLocatione(beforeFightX,beforeFightY);
		Gdx.input.setInputProcessor(player);
	}

	private void changeMap(String mapPath)
	{	
		map=new TmxMapLoader().load("data/"+mapPath);
		renderer=new OrthogonalTiledMapRenderer(map);
	}
	
	private void setNewLocatione(int x,int y){try{player.setPosition((float)x*player.getCollisionLayer().getTileWidth(),(float)y*player.getCollisionLayer().getTileHeight());}catch(NullPointerException e){System.out.println("Null");}}
	@Override
	public void hide() {}

	@Override 	
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() 
	{
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();
	}
	
	public int getMonsterHP()
	{
		return player.getMonsterHP();
	}
}