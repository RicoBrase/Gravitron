package de.rico_brase.Gravitron;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GravitronKeyListener implements KeyListener{
	
	protected char[] chars = {'0','1','2','3','4','5','6','7','8','9'}; 
	protected List<Character> allowed;
	
	public GravitronKeyListener(){
		super();
		allowed = new ArrayList<Character>();
		for(char c : chars){
			allowed.add(c);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(allowed.contains(e.getKeyChar())){
			Gravitron.height.setText(Gravitron.height.getText() + e.getKeyChar());
		}
		
		e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()){
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
