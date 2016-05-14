import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;


public class Mouse implements MouseListener, MouseMotionListener {
	boolean click = false, mouseReleased;
	Point2D mouseLocation;
	public Mouse() 
	{
		mouseLocation = new Point2D.Double();
	}
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseMoved(MouseEvent e) {
		mouseLocation = e.getPoint();
			
	}
	//getter
	public Point2D getMouseLocation() 
	{
		return mouseLocation;
	}
	public void setMouseRelFalse() {
		mouseReleased = false;
	}
	public boolean mouseClicked()
	{
		if (click)return true;
		else return false;
	}
	public boolean mouseReleased() {
		if (mouseReleased)
			return true;
		else
			return false;
	}
	public void mouseClicked(MouseEvent e) 
	{
		click = true;
	}

	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseReleased(MouseEvent e) 
	{
		mouseReleased = true;
	}
	
}
