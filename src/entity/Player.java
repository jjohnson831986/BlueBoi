package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int SCREEN_X;
	public final int SCREEN_Y;
	
	int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		// sets player to middle of the screen (player stationary, map moves)
		SCREEN_X = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE/2);
		SCREEN_Y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE/2);
		
		// Collision area
		solidArea = new Rectangle(12,20,20,20); // original sizes (8,16,32,32) (48x48)
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		// starting position based on tile position on map
		worldX = gp.TILE_SIZE * 23;
		worldY = gp.TILE_SIZE * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(keyH.upPressed == true || keyH.downPressed == true ||
			keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			// Check tile collision
			collisionOn = false;
			gp.collisionChecker.checkTile(this);
			
			// Check object collision
			int objIndex = gp.collisionChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// If collision is false, player can move
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed;	break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			// Player image changes every 12 frames, only when a key is pressed
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNumber == 1) {
					spriteNumber = 2;
				}
				else if (spriteNumber == 2) {
					spriteNumber = 1;
				}
				spriteCounter =0;
			}
		}		
	}
	
	public void pickUpObject(int index) {
		if(index != 999) {
			String objectName = gp.obj[index].name;
			
			switch(objectName) {
			case "Key":
				gp.playSFX(1);
				hasKey++;
				gp.obj[index] = null;
				break;
			case "Door":
				if(hasKey > 0) {
					gp.playSFX(3);
					gp.obj[index] = null;
					hasKey--;
				}
				break;
			case "Boots":
				gp.playSFX(2);
				speed += 2;
				gp.obj[index] = null;
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if(spriteNumber == 1) {
				image = up1;
			}
			if(spriteNumber == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNumber == 1) {
				image = down1;
			}
			if(spriteNumber == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNumber == 1) {
				image = left1;
			}
			if(spriteNumber == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNumber == 1) {
				image = right1;
			}
			if(spriteNumber == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, SCREEN_X, SCREEN_Y, gp.TILE_SIZE, gp.TILE_SIZE, null);
	}
}
