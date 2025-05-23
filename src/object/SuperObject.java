package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48); // make whole tile default, change in OBJ_ class
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
		
		if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X &&
		   worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X &&
		   worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y &&
		   worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
			
			g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
		}
	}
}
