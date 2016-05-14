
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rizer implements KeyListener {
	//Instance variables
	private int x, y,velX, velY;
	private boolean isAlive;
	private BufferedImage rizerSprite;
	private ArrayList bullets; //ArrayList to store the bullets
	long startTime = System.nanoTime(), currentTime, timer = 10;
	boolean collisionDetectedWithGround;
	//Constructor
	public Rizer(int x, int y, int width, int height, BufferedImage sprite) {
		setX(x);
		setY(y);
		isAlive = true;
		rizerSprite = sprite;
		bullets = new ArrayList();//initializes the ArrayList of the bullets
	}
	int vX=0; //the variable that keeps track of how far the image has scrolled sideways
	public int getVX() {
		return vX;
	}
	BufferedImage bulletImage;
	
	public void update(Bullet bullet, SpriteSheet bulletSprite, SpriteSheet bossSprite, SpriteSheet charSprite, SpriteSheet levelSprite, Graphics2D graphics, KeyBoard k, Mouse mouse)
	{	
		//Checks if Rizer is walking
		BufferedImage character = charSprite.getSprite(40, 6, 25,40);
		if (k.getRightKey() && !k.rightKeyReleased() && vX>=-6915) { // vX is 6915 at the end of the level;does't allow the background to scroll at that point.
			setVelX(1);
			vX-=1;
			graphics.drawImage(getRizerImage(charSprite),60,500,60,100,null);
			BufferedImage level1 = levelSprite.getSprite(0,20,3456,110);
			graphics.drawImage(level1,vX,0,9000,768,null);
			//RIZER WALKING ANIMATION
		}
		else {
			k.setRightFalse();
			k.setRelRightFalse();
			graphics.drawImage(getLevelImage(levelSprite),vX,0,9000,768,null);
			graphics.drawImage(getRizerImage(charSprite),50,500,60,100,null);
		}
		if (k.getSpaceKey()&&!k.spaceKeyReleased()) {
			this.setVelX((int) 0.2);
			this.setX(this.getX()+this.getVelX());
			this.setVelY(-1);
			this.setY(this.getY()+this.getVelY());
			if (this.getY()<=220)
				graphics.drawImage(character,this.getX(),this.getY(),60,100,null);
		}
		else if (k.spaceKeyReleased()) {
			k.setSpaceFalse();
			this.setVelX(1);
			this.setX(this.getX()+this.getVelX());
			this.setVelY(1);
			this.setY(this.getY()+this.getVelY());
			if (this.getY()<=270)
				graphics.drawImage(character,this.getX(),this.getY(),60,100,null);
			else {
				this.setVelX(0);
				this.setVelY(0);
				this.setY(270);
				graphics.drawImage(character,200,440,60,100,null);
			}
		}
		//Checks if the bullet is fired
		if (mouse.mouseClicked()&&mouse.mouseReleased) {
			fire();
			mouse.setMouseRelFalse();
		}
	}
	//Adds the bullet to the screen
	public void fire() {
		Bullet z = new Bullet(70,500);
		bullets.add(z);
	}
	public BufferedImage getLevelImage(SpriteSheet levelSprite) {
		BufferedImage levelImage = levelSprite.getSprite(0,20,3456,110);
		return levelImage;
	}
	public BufferedImage getRizerImage(SpriteSheet charSprite) {
		BufferedImage rizerImage = charSprite.getSprite(40,6,25,40);
		return rizerImage;
	}
	public BufferedImage getBulletImage(SpriteSheet bulletSprite) {
		BufferedImage bullet1 = bulletSprite.getSprite(3, 2, 12, 10);
		return bullet1;
	}
	//Checks the status of the bullet if it is alive or not
	public boolean isAlive() {
		if (isAlive)
			return true;
		else 
			return false;
	}
	public void declareRizerDead() {
		isAlive = false;
	}
	public void render(Graphics2D graphics) 
	{
		graphics.drawImage(rizerSprite,20,20,null);
	}
	//Getters and setters
	public  int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public int getVelX() {
		return this.velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getVelY() {
		return this.velY;
	}
	//Returns the ArrayList that stores all the bullets
	public ArrayList getBullets() {
		return this.bullets;
	}

	public void keyPressed(KeyEvent e) {
		int keyP = e.getKeyCode();
		if (keyP==KeyEvent.VK_SPACE)
			fire();

	}
	public void keyReleased(KeyEvent arg0) {

	}
	public void keyTyped(KeyEvent arg0) {

	}
}