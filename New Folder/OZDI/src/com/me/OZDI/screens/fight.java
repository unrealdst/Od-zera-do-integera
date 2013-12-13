package com.me.OZDI.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.OZDI.OZDI;

public class fight implements Screen, InputProcessor
{
	private int Xpocz=0,Ypocz=0,Xkon=0,Ykon=0;//wspolrzedne dotkniec do wyliczania wektorow
	private Sprite fighter,fighter2,backhealthbar,backhealthbar2,healthbar,healthbar2;
	private Texture background;
	private SpriteBatch batch;
	private boolean playerMove=true;
	//statystyki
	private static int playerAtc=10/*,playerDef=8*/,monsterAtc=5/*,monsterDef=3*/,monsterHP,playerHP,monsterMAXHP=10,playerMAXHP=10; 
	OZDI game;
	
	public fight(OZDI game)
	{
		this.game = game;
	}
	
	@Override
	public void render(float delta) 
	{				
		//System.out.println("FPS: "+Gdx.graphics.getFramesPerSecond());
		Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setColor(1, 1, 1, 1);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(fighter, Gdx.graphics.getWidth()*4/10,Gdx.graphics.getHeight()/5);
		batch.draw(fighter2,(Gdx.graphics.getWidth()*6)/10,Gdx.graphics.getHeight()/5);
		batch.draw(backhealthbar,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()*14/15);
		batch.draw(healthbar,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()*14/15);
		batch.draw(backhealthbar2,Gdx.graphics.getWidth()*7/10,Gdx.graphics.getHeight()*14/15);
		batch.draw(healthbar2,Gdx.graphics.getWidth()*7/10,Gdx.graphics.getHeight()*14/15);
		batch.end();
	}
	
	//TODO nie dziala, naprawic
	private void healthRender(String site,int maxHP,int HP)
	{
		if(site.equals("p"))
		{
			/*
			healthbar.rotate90(true);
			healthbar.translateX(100);
			healthbar.setScale(HP/maxHP, 1);
			healthbar.setColor(1, 1, 1, 0.2f);		
			*/
			//healthbar=new Sprite(new Texture(Gdx.files.internal("data/healtbar.png")));
			batch.begin();
			batch.draw(healthbar, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
			batch.end();
		}
		else
		{
			healthbar2.translateX(100);
			healthbar2.setScale(HP/maxHP, 1);
			healthbar2.setColor(1, 1, 1, 0.2f);
			/*healthbar2.
			//healthbar2=new Sprite(new Texture(Gdx.files.internal("data/healtbar.png")));
			batch.begin();
			batch.draw(healthbar2,Gdx.graphics.getWidth()*7/10,Gdx.graphics.getHeight()*14/15,HP/maxHP,20);
			batch.end();*/
		}
	}	
	
	@Override
	public void resize(int width, int height) 
	{
		fighter.setPosition( Gdx.graphics.getWidth()*4/10,Gdx.graphics.getHeight()/5);
		fighter2.setPosition((Gdx.graphics.getWidth()*6)/10,Gdx.graphics.getHeight()/5);	
	}

	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
		batch=new SpriteBatch();
		background = new Texture(Gdx.files.internal("data/bakground.png"));
		fighter=new Sprite(new Texture(Gdx.files.internal("data/fighter.png")));
		fighter2=new Sprite(new Texture(Gdx.files.internal("data/fighter.png")));
		fighter2.flip(true, false);
		backhealthbar=new Sprite(new Texture(Gdx.files.internal("data/backhealtbar.png")));
		backhealthbar2=new Sprite(new Texture(Gdx.files.internal("data/backhealtbar.png")));
		healthbar=new Sprite(new Texture(Gdx.files.internal("data/healtbar.png")));
		healthbar2=new Sprite(new Texture(Gdx.files.internal("data/healtbar.png")));
		backhealthbar2.flip(true, false);
		healthbar2.flip(true, false);		
	}

	/*
	 * Wyliczanie cosinusa miedzy dwoma wektorami
	 * 
	 * Zwraca:
	 * 	1:NW -> SE
	 * 	2:SW -> NE
	 * 	3:W -> E
	 * 	4:E -> W
	 * 	5:NE -> SW
	 * 	6:SE -> NW
	 * 	37:Dla ca³ej reszty(mo¿na podejrzewaæ jakis b³¹d)
	 */
	private int cos()
	{
			float Xwekt=Xkon-Xpocz;
			float Ywekt=Ykon-Ypocz;
			float skalar=Xwekt*100+Ywekt*0;
			double longA=Math.sqrt((Xwekt*Xwekt)+(Ywekt*Ywekt));
			double longB=100;
			double cos=(skalar)/(Math.abs(longA)*Math.abs(longB));
			cos=-cos;
			//System.out.println("cosinus: "+cos);
			
			//ataki
			if((cos<0)&&(cos>-0.8))
			{
				if (Ywekt>0)
				{
					//NW -> SE
					
					return 1;
				}
				else
				{
					//SW -> NE
					return 2;
				}
			}
			if(cos<-0.8)
			{
				//W -> E
				return 3;
			}
			//obrony
			if(cos>0.8)
			{
				//E -> W
				return 4;
			}
			if((cos>0)&&(cos<0.8))
			{
				if(Ywekt>0)
				{
					//NE -> SW
					return 5;
				}
				else
				{
					//SE -> NW
					return 6;
				}
			}
			return 37;
	}
	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void dispose() {System.out.println("dispose");}
	@Override
	public boolean keyDown(int keycode) {return false;}

	@Override
	public boolean keyUp(int keycode) {return false;}

	@Override
	public boolean keyTyped(char character) {return false;}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//System.out.println("touchDown screenX: "+screenX);
		//System.out.println("touchDown screenY: "+screenY);	
		System.out.println("MonsterHP: "+monsterHP+" PlayerHP: "+playerHP);
		Xpocz=screenX;
		Ypocz=screenY;
		return true;
	}
	
	/*
	*losuje z ktorej strony atakuje przeciwnik
	*/
	private int hit()
	{
		Random r=new Random();
		int monsterDef=r.nextInt(3)+4;
		//System.out.println("hit: "+monsterDef);
		return monsterDef;
	}
	/*
	 * losuje skad atakuje przeciwnik
	 */
	private int def()
	{
		Random r=new Random();
		int monsterAtak=r.nextInt(3)+1;
		//System.out.println("def: "+monsterAtak);
		return monsterAtak;	
	}
	/*
	 * wylicza za ile udezyc
	 */
	private int obrazenia(int ataking)
	{
		Random rand=new Random();
		boolean znak=true;
		int randomZnak=rand.nextInt(2);
		System.out.println("randomZnak: "+randomZnak);
		if(randomZnak==0){znak=true;System.out.println("T");}else{znak=false;System.out.println("F");}
		int obrazenia=0;
		if(znak)
		{
			boolean buff=true;
			while(buff)
			{
				try
				{
					obrazenia=ataking+( rand.nextInt(ataking/2)/rand.nextInt(ataking*2)+2 );
					buff=false;
				}
				catch(ArithmeticException e)
				{
					buff=true;
				}
			}
		}
		else
		{
			boolean buff=true;
			while(buff)
			{
				try
				{
					obrazenia=ataking-( rand.nextInt(ataking/2)/rand.nextInt(ataking*2)+2 );
					buff=false;
				}
				catch(ArithmeticException e)
				{
					buff=true;
				}
			}
		}
		System.out.println("obrazenia: "+obrazenia);
		return obrazenia;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		System.out.println("------------------------------");
		if(screenX==0||screenY==0)
		{
			return true;
		}
		else
		{
			//System.out.println("touchUp screenX: "+screenX);
			//System.out.println("touchUp screenY: "+screenY);	
			Xkon=screenX;
			Ykon=screenY;
			int moveType=cos();
			//###Walka###
			
			//Atak
			if(playerMove)
			{				
				switch(moveType)
				{
				case 1:
					if(hit()==6)
					{
						System.out.println("MonsterBlocked");
					}
					else
					{
						monsterHP=monsterHP-(obrazenia(playerAtc));
						healthRender("m", monsterMAXHP,monsterHP);
						System.out.println("MonsterHP: "+monsterHP);
						if(monsterHP<=0){System.out.println("Wygrales");game.setScreen(game.playScreen);}	
					}
					playerMove=false;
					break;
				case 2:
					if(hit()==5)
					{
						System.out.println("MonsterBlocked");
					}					
					else
					{
						monsterHP=monsterHP-(obrazenia(playerAtc));
						healthRender("m", monsterMAXHP,monsterHP);
						System.out.println("MonsterHP: "+monsterHP);
						if(monsterHP<=0){System.out.println("Wygrales");game.setScreen(game.playScreen);}
					}
					playerMove=false;
					break;
				case 3:
					if(hit()==4)
					{
						System.out.println("MonsterBlocked");
					}
					else
					{
						monsterHP=monsterHP-(obrazenia(playerAtc));
						healthRender("m", monsterMAXHP,monsterHP);
						System.out.println("MonsterHP: "+monsterHP);
						if(monsterHP<=0){System.out.println("Wygrales");game.setScreen(game.playScreen);}
					}
					playerMove=false;
					break;
				}
			}
			//obrona
			else
			{
				switch(moveType)
				{
				case 4:
					if(def()==3)
					{
						System.out.println("PlayerBlocked");
					}
					else
					{
						playerHP=playerHP-(obrazenia(monsterAtc));
						
						healthRender("p", playerMAXHP,playerHP);
						System.out.println("PlayerHP: "+playerHP);
						if(playerHP<=0){System.out.println("Przegrales");game.setScreen(game.playScreen);}
					}
					playerMove=true;
					break;
				case 5:
					if(def()==2)
					{
						System.out.println("PlayerBlocked");
					}	
					else
					{
						playerHP=playerHP-(obrazenia(monsterAtc));
						
						healthRender("p", playerMAXHP,playerHP);
						System.out.println("PlayerHP: "+playerHP);
						if(playerHP<=0){System.out.println("Przegrales");game.setScreen(game.playScreen);}
					}
					playerMove=true;
					break;
				case 6:
					if(def()==1)
					{
						System.out.println("PlayerBlocked");
					}					
					else
					{
						playerHP=playerHP-(obrazenia(monsterAtc));
						healthRender("p", playerMAXHP,playerHP);
						System.out.println("PlayerHP: "+playerHP);
						if(playerHP<=0){System.out.println("Przegrales");game.setScreen(game.playScreen);}
					}
					playerMove=true;
					break;
				}
			}		
			return true;
		}
	}


	public void setPlayerAtc(int playerAtc) {this.playerAtc = playerAtc;}

	//public void setPlayerDef(int playerDef) {this.playerDef = playerDef;}

	public void setMonsterAtc(int monsterAtc) {this.monsterAtc = monsterAtc;}

	//public void setMonsterDef(int monsterDef) {this.monsterDef = monsterDef;}

	public int setPlayerHP(int playerHPt) 
	{
		playerHP=playerHPt;
		playerMAXHP=playerHPt;
		return playerHPt;
	}

	public int setMonsterHP(int monsterHPt) 
	{
		monsterHP=monsterHPt;
		monsterMAXHP=monsterHPt;
		return monsterHPt;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {return true;}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {return false;}
	@Override
	public boolean scrolled(int amount) {return false;}
}