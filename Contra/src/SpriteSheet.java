import java.awt.image.*;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

//used to load and manage sprite sheets
public class SpriteSheet {
	private BufferedImage spriteSheet; //stores sprite sheet image

	public SpriteSheet(String fileName) {
	  try {
	    BufferedImage sheet = ImageIO.read(new File(fileName));
	    spriteSheet = sheet;
	  } catch (IOException e) {}
	}
	
	//selects part of the sprite sheet and creates an image based on a subsection
	//at left-top point (x,y) with width w and height h
	public BufferedImage getSprite(int x, int y, int w, int h) {
		BufferedImage sprite = spriteSheet.getSubimage(x, y, w, h);
		return sprite;
	}
}