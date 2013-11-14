package com.me.OZDI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.me.OZDI.screens.Play;
import com.me.OZDI.screens.fight;

public class OZDI extends Game {

	public static final String Version=" 0.0.0.0.003 Pre-alpha";
	
	Play playScreen;
    public fight fightScreen;

	//public Screen fightScreen;
	@Override
	public void create() {
		 playScreen = new Play(this);
         fightScreen = new fight(this);
         Gdx.input.setInputProcessor(fightScreen);
         setScreen(fightScreen);		
	}

	@Override
	public void dispose() {
	super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
