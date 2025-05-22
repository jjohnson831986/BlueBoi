package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable {
	
	// Screen settings
	final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
	final int SCALE = 3;
	
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
	final int MAX_SCREEN_COL = 16;
	final int MAX_SCREEN_ROW = 12;
	final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768px
	final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576px
	
	// FPS
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	// Player
	Player player = new Player(this, keyH);
	
	// Set players default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4; // pixels per movement
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);
		
		g2.dispose();
	}

}
