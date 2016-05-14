import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Enemy {
	//Instance variables
	private int x,y,width,height,velX,velY;
	private boolean visible;
	private BufferedImage enemySprite;
	private ArrayList enemies;
	private ArrayList enemiesWithGuns;
	long startTime = System.currentTimeMillis();
	long currentTime, timer = 10;
	//Constructor
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		setX(x);
		setX(y);
		this.width = width;
		this.height = height;
		this.visible=true;
		enemySprite = sprite;
		//ArrayList to store all the enemies
		enemies = new ArrayList();
		enemiesWithGuns = new ArrayList();
	}
	//Makes the boundary rectangle of the enemy
	public Rectangle2D getBoundaryRectangle() {
		Rectangle2D rectangle = new Rectangle2D.Double(this.x,this.y,this.width,this.height);
		return rectangle;
	}
	BufferedImage enemyImage, armedEnemyImage;
	//Updates the enemy
	public void update(Bullet bullet, SpriteSheet enemySprite, SpriteSheet enemyDeadSprite, Graphics2D graphics, KeyBoard k, Rizer rizer) {
		BufferedImage e1 = enemySprite.getSprite(50,46,20,33);
		BufferedImage armedEnemy = enemySprite.getSprite(75,10,25,30);
		enemyImage = e1;
		armedEnemyImage = armedEnemy;
		//Creates new enemies
		if (rizer.getVX()==-300 || rizer.getVX()==-500 || rizer.getVX()==-650 || rizer.getVX()==-750 || rizer.getVX()==-900 || rizer.getVX()==-1200) 
			makeNewEnemy();
			makeNewArmedEnemy();
	}
	public void makeNewArmedEnemy() {
		Enemy e = new Enemy(2000,500,55,80,null);
		enemiesWithGuns.add(e);
	}
	public BufferedImage getEnemyImage() {
		return enemyImage;
	}
	public BufferedImage getArmedEnemyImage() {
		return armedEnemyImage;
	}
	public void makeNewEnemy() 	{
		Enemy e = new Enemy(1024,300,55,80,null);
		enemies.add(e);
	}
	public ArrayList getEnemies() {
		return this.enemies;
	}
	public ArrayList getArmedEnemies() {
		return this.enemiesWithGuns;
	}
	public void setVisibility(boolean v) 
	{
		this.visible=v;
	}
	public boolean isVisible(Enemy e) {
		if (e.visible)
			return true;
		else 
			return false;
	}
	public void move() 
	{
		this.x-=1;
	}
	//Getters and setters
	public void setX(int x)
	{
		this.x = x;		
	}

	public int getX() 
	{
		return this.x;
	}

	public void setY(int y) 
	{
		this.y = y;
	}

	public int getY() 
	{
		return this.y;
	}

	public int getVelX() 
	{
		return this.velX;
	}

	public void setVelX(int velX)
	{
		this.velX = velX;
	}

	public int getVelY() 
	{
		return this.velY;
	}

	public void setVelY(int velY)
	{
		this.velY = velY;
	}
}

