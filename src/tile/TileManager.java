package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNumber[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10]; // increase if/when more tiles needed
		mapTileNumber = new int [gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
				String line = br.readLine();
				while(col < gp.MAX_WORLD_COL) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col == gp.MAX_WORLD_COL) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		// automate tile drawing process
		while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {
			int tileNumber = mapTileNumber[worldCol][worldRow];
			
			int worldX = worldCol * gp.TILE_SIZE;
			int worldY = worldRow * gp.TILE_SIZE;
			int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
			int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
			
			if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X &&
			   worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X &&
			   worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y &&
			   worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
				
				g2.drawImage(tile[tileNumber].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.MAX_WORLD_COL) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
