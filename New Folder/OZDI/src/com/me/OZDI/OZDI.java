package com.me.OZDI;

import com.badlogic.gdx.Game;
import com.me.OZDI.screens.Play;
import com.me.OZDI.screens.Skillzzzz;
import com.me.OZDI.screens.fight;

public class OZDI extends Game {

	public static final String Version=" 0.0.0.0.009 Pre-alpha";
	
	public Play playScreen;
    public fight fightScreen;
    public Skillzzzz skillScreen;
    
	@Override
	public void create() {
		skillScreen=new Skillzzzz(this);
		playScreen = new Play(this);
        fightScreen = new fight(this);  
        setScreen(playScreen);	
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
