package object_categories;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.Camera;
import game.Env;

public abstract class Overlay {
	static int baseXOffset = 5;
	static int baseYOffset = 13;
	List<int[]> offsets;
	List<String> messages;
	int numOffsets = 0;
	
	public Overlay(){
		this.offsets = new ArrayList<int[]>();
		this.messages = new ArrayList<String>();
	}
	
	// Optional: Set message now
	public void addOffset(String ...message){
		// If at bottom of screen, start new column
		if(baseYOffset > (Env.resHeight)){
			baseXOffset += 250;
			baseYOffset = 13;
			offsets.add(new int[]{baseXOffset, baseYOffset});
			baseYOffset += 20;
		}else {
			offsets.add(new int[]{baseXOffset, baseYOffset});
			baseYOffset += 20;
		}
		
		if(message.length == 0) {
			messages.add("");
		}else {
			messages.add(message[0]);
		}
		
		numOffsets++;
	}
	
	public void setMessage(int index, String message) {
		messages.set(index, message);
	}
	
	public void renderStrings(Graphics g) {
		g.setColor(Color.WHITE);
		for(int i=0; i<offsets.size(); i++) {
			g.drawString(messages.get(i), offsets.get(i)[0], offsets.get(i)[1]);
		}
	}
	
	public abstract void render(Graphics g);
}
