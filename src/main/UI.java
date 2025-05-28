package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font arial_40, arial_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	
	double playTime;
	DecimalFormat decimalInGameDisplayFormatter = new DecimalFormat("#0.0"); // I like single decimal displayed in game
	DecimalFormat decimalFinalDisplayFormatter = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) { // updates once per second (60 fps as set earlier)
		if(gameFinished == true) {
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			
			String text;
			int textLength;
			int x,y;
			
			text = "You found the treasure!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();			
			x = gp.SCREEN_WIDTH/2 - textLength/2;
			y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE*3);
			g2.drawString(text, x, y);
			
			text = "Your time was: " + decimalFinalDisplayFormatter.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();			
			x = gp.SCREEN_WIDTH/2 - textLength/2;
			y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE*4);
			g2.drawString(text, x, y);
			
			g2.setFont(arial_80B);
			g2.setColor(Color.yellow);
			text = "Congratulations!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();			
			x = gp.SCREEN_WIDTH/2 - textLength/2;
			y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE*2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
		}
		else {
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		g2.drawImage(keyImage, gp.TILE_SIZE/2, gp.TILE_SIZE/2, gp.TILE_SIZE, gp.TILE_SIZE, null);
		// drawString() uses base line of text, not top like draw normally does
		g2.drawString("x " + gp.player.hasKey, 74, 65);
		
		// Time
		playTime += (double)1/60;
		g2.drawString("Time: " + decimalInGameDisplayFormatter.format(playTime), gp.TILE_SIZE*11, 65);
		
		// Message
		if(messageOn == true) {
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.TILE_SIZE/2, gp.TILE_SIZE*5);
			
			messageCounter++;
			if(messageCounter > 60) { // 60 frames (1 second)
				messageCounter = 0;
				messageOn = false;
			}
			}
		}
	}
}
