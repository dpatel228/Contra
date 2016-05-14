import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyBoard implements KeyListener {
	private boolean key[] = new boolean[5];
	private final int KEY_RIGHT = 0, KEY_SPACE = 1, KEY_DOWN = 2, KEY_UP = 3, KEY_ENTER = 4;
	private boolean keyReleasedRight = false, keyReleasedSpace = false, keyReleasedDown = false, keyReleasedUp;
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int keyPressed = e.getKeyCode();
		if (keyPressed==KeyEvent.VK_RIGHT) 
			key[KEY_RIGHT] = true;
		else if (keyPressed==KeyEvent.VK_SPACE)
			key[KEY_SPACE] = true;
		else if (keyPressed==KeyEvent.VK_DOWN)
			key[KEY_DOWN] = true;
		else if (keyPressed==KeyEvent.VK_UP)
			key[KEY_UP] = true;
		else if (keyPressed==KeyEvent.VK_ENTER)
			key[KEY_ENTER] = true;
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		int keyReleased = e.getKeyCode();
		if (keyReleased==KeyEvent.VK_RIGHT) 
			keyReleasedRight = true;
		else if (keyReleased==KeyEvent.VK_SPACE) 
			keyReleasedSpace = true;
		else if (keyReleased==KeyEvent.VK_DOWN) 
			keyReleasedDown = true;
		else if (keyReleased==KeyEvent.VK_UP) 
			keyReleasedUp = true;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	public void setRightFalse() {
		key[KEY_RIGHT] = false;
	}
	public void setUpFalse() {
		key[KEY_UP] = false;
	}
	public void setSpaceFalse() {
		key[KEY_SPACE] = false;
	}
	public void setDownFalse() {
		key[KEY_DOWN] = false;
	}
	public void setRelSpaceFalse() {
		keyReleasedSpace = false;
	}
	public void setRelDownFalse() {
		keyReleasedDown = false;
	}
	public void setRelRightFalse() {
		keyReleasedRight = false;
	}
	public boolean rightKeyReleased() {
		if (keyReleasedRight)
			return true;
		else
			return false;
	}
	public boolean spaceKeyReleased() {
		if (keyReleasedSpace)
			return true;
		else
			return false;
	}
	public boolean downKeyReleased() {
		if (keyReleasedDown)
			return true;
		else
			return false;
	}public boolean UpKeyReleased() {
		if (keyReleasedUp)
			return true;
		else
			return false;
	}
		
	public boolean[] getKey() {
		return this.key;
	}

	public boolean getRightKey() {
		if (key[KEY_RIGHT])
			return true;
		else
			return false;
	}
	public boolean getSpaceKey() {
		if (key[KEY_SPACE])
			return true;
		else
			return false;
	}
	public boolean getDownKey() {
		if (key[KEY_DOWN])
			return true;
		else
			return false;
	}
	public boolean getUpKey() {
		if (key[KEY_UP])
			return true;
		else
			return false;
	}
public boolean getEnterKey() {
	if (key[KEY_ENTER])
		return true;
	else
		return false;
}
}
