import java.awt.geom.Rectangle2D;

//393 X 62
public class Object {
	private int x,width;
	private final int y = 460, height = 42;
	//Constructor
	public Object(int x,int width, int height) {
		setX(x);
		setWidth(width);
		height=this.height;
	}
	//boundary rectangle of the object
	public Rectangle2D getBoundaryRectangle() {
		return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
	}
	//getters and setters
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return this.y;
	}
	public void setWidth(int width) {
		this.width=width;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
}
