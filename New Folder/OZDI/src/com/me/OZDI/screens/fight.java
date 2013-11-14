package com.me.OZDI.screens;

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
	private int Xpocz=0,Ypocz=0,Xkon=0,Ykon=0;
	private Sprite fighter,fighter2;
	private Texture background;
	private SpriteBatch batch;
	OZDI game;
	public fight(OZDI game)
	{
		//System.out.println("aaaaaaaaaaaaa");
		this.game = game;
	}
	
	@Override
	public void render(float delta) 
	{
		
		Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
		//System.out.println("fiiiiiiiiiiiiiight");
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setColor(1, 1, 1, 1);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(fighter, Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2-150);
		batch.draw(fighter2,Gdx.graphics.getWidth()/2+100,Gdx.graphics.getHeight()/2-150);
		batch.end();	
	}

	@Override
	public void resize(int width, int height) {
		//camera.viewportWidth=width;
		//camera.viewportHeight=height;
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(Gdx.input.getInputProcessor());
		batch=new SpriteBatch();
		background = new Texture(Gdx.files.internal("data/bakground.png"));
		fighter=new Sprite(new Texture(Gdx.files.internal("data/fighter.png")));
		fighter2=new Sprite(new Texture(Gdx.files.internal("data/fighter.png")));
		fighter2.flip(true, false);

	}

	private void cos()
	{

			float Xwekt=Xkon-Xpocz;
			float Ywekt=Ykon-Ypocz;
			float skalar=Xwekt*100+Ywekt*0;
			//float buff=(Xwekt*Xwekt)+(Ywekt+Ywekt);
			double longA=Math.sqrt((Xwekt*Xwekt)+(Ywekt*Ywekt));
			double longB=100;
			double cos=(skalar)/(Math.abs(longA)*Math.abs(longB));
			System.out.println("cosinus: "+cos);
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("touchDown screenX: "+screenX);
		System.out.println("touchDown screenY: "+screenY);		
		Xpocz=screenX;
		Ypocz=screenY;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		if(screenX==0||screenY==0)
		{
			return true;
		}
		else
		{
			System.out.println("touchUp screenX: "+screenX);
			System.out.println("touchUp screenY: "+screenY);	
			Xkon=screenX;
			Ykon=screenY;
			cos();
			return true;
		}
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//System.out.println("touchDragged screenX: "+screenX);
		//System.out.println("touchDragged screenY: "+screenY);
		//System.out.println("touchDragged pointer: "+pointer);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
