import java.awt.geom.Rectangle2D;

public class Bullet {
	//instance variables
	private int x,y,velX;
	private boolean isAlive;
	//Constructor
	public Bullet(int x, int y) {
		this.x=58;
		this.y=310;
		this.velX=0;
		this.isAlive=true;
		
	}
	//Boundary rectangle of bullets
	public Rectangle2D getBoundary() 
	{
		Rectangle2D bullet0 = new Rectangle2D.Double(this.getX()+5,this.getY()+5,12*.85,12*.85);
		return bullet0;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive=true;
	}
	//Checks if the bullet is alive or on the screen
	public boolean isAlive(Bullet b) {
		if (b.isAlive)
			return true;
		else return false;
	}
	//Moves the bullet on the screen
	public void move() {
		this.x+=2;
		if (x>1024)
			isAlive = false;
	}
	//Getters and setters
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public int getVelX() {
		return this.velX;
	}
}
