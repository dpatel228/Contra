import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	//screen dimensions and variables
	static final int WIDTH = 1024;
	static final int HEIGHT = WIDTH / 4 * 3; //4:3 aspect ratio
	private int GAME_STATE, score, lives;
	private static final int GAME_MENU = 0;
	private static final int GAME_PLAY = 1;
	private static final int GAME_RESET = 3;
	private static final int GAME_INSTRUCTIONS = 2;

	private JFrame frame;
	private Mouse mouse;
	//All the spritesheets used
	private SpriteSheet charSprite, enemySprite, enemyDeadSprite, levelSprite, bulletSprite, bossSprite;
	private Bullet bullet;
	private Rizer rizer;
	private KeyBoard k;
	private Enemy enemy;
	private ObjectLoader objectLoader;
	private Object[][] objectContainer;
	//game updates per second
	static final int UPS = 60;
	//variables for the thread
	private Thread thread;
	private boolean running;
	//used for drawing items to the screen
	private Graphics2D graphics;
	//initialize game objects, load media(pics, music, etc)
	public void init() {
		//initializes the characters
		rizer = new Rizer(100,440,60,100,null);
		enemy = new Enemy(2000,440,60,100,null);
		score=0;
		lives=3;
		//Initializes the objects of the game through the level file. 'S' in the level file means small,'M' means medium and 'B' means big sized object
		objectLoader.openObjectFile("objects.txt");
		//objectLoader.createObjects();
		objectContainer = objectLoader.getObjectContainer();
	}
	//update game objects
	public void update() {
		//Resets the game when the player loses all his lifes.
		if (GAME_STATE==GAME_RESET) 
			resetGame(); //Resets the game 
		else {
			//updates the character, enemy, score, objects, checks for collisions
			rizer.update(bullet, bulletSprite, bossSprite, charSprite,levelSprite, graphics,k,mouse);
			enemy.update(bullet, enemySprite, enemyDeadSprite, graphics,k,rizer);
			//BULLETS
			drawBullets();
			updateObjects();
			drawObjects();
			updateEnemy();
			checkForCollisions();
			drawScore();
			drawLives();
		}
	}
	//checks for object, bullet and physical collisions in the game
	public void checkForCollisions() {
		//checkForObjectCollisions();
		checkForBulletCollisions();
		checkForPhysicalCollisions();

	}
	//Physical collisions
	public void checkForPhysicalCollisions()	{
		ArrayList enemies = enemy.getEnemies();
		//For loop goes through each enemy in the ArrayList to check if he has collided with the character.
		for (int i = 0; i < enemies.size();i++)		{
			Enemy e = (Enemy) enemies.get(i);
			if (e.getX()==50) {
				rizer.declareRizerDead();
			}
		}
	}
	public void checkForBulletCollisions() 	{
		ArrayList bullets = rizer.getBullets();
		ArrayList enemies = enemy.getEnemies();
		//For loop goes through the ArrayList where all the enemies and bullets are stored and checks for collisions
		for (int i = 0; i < bullets.size();i++) {
			Bullet m = (Bullet) bullets.get(i); //Gets the current bullet.
			for (int j = 0; j < enemies.size();j++) {
				Enemy e = (Enemy) enemies.get(j); //Gets the current enemy.
				if (m.getX()>=(e.getX()+(-rizer.getVX()))) { 
					e.setVisibility(false);
					bullets.remove(m); //If the bullet has hit the enemy, it is removed from the vector.
					updateScore();
				}
			}
		}
	}
	//Checks for collisions with objects(blue rectangles on the screen)
/*	public void checkForObjectCollisions() {
		BufferedImage br1 = charSprite.getSprite(200,6,25,40);
		Rectangle2D rizerBoundary = new Rectangle2D.Double(rizer.getX(),rizer.getY(),60,100);
		for (int x=0; x < objectContainer.length;x++) {
			for (int y = 0; y < objectContainer[0].length;y++) {
				//Goes through the 2D array and gets the current object
				Object currentObject = objectContainer[x][y];
				//Gets the x, y, width and height of the current object and makes a blueprint rectangle
				int xPos = currentObject.getX(), yPos = currentObject.getY(), width = currentObject.getWidth(), height = currentObject.getHeight();
				Rectangle2D objectRec = new Rectangle2D.Double(xPos,yPos,width,height);
				//Checks if the character has interesected the object
				if (rizerBoundary.intersects(objectRec)) {
					rizer.setY((int)objectRec.getMinY());
					rizer.collisionDetectedWithGround=true;
					graphics.drawImage(br1,50,(int) objectRec.getMinY()-88,60,100,null);
				}
			}
		}
	}*/
	//updates the enemies
	public void updateEnemy() 	{
		updateRunningEnemy();
		updateArmedEnemy();
	}
	//updates running enemies
	public void updateRunningEnemy() {
		ArrayList enemies = enemy.getEnemies();
		for (int i = 0; i < enemies.size();i++) {
			//For loop goes through each enemy in the ArrayList and updates his position on the screen
			Enemy e = (Enemy) enemies.get(i);
			if (enemy.isVisible(e)) {
				e.setVelX(-1);
				e.setX(e.getX()+e.getVelX());
				graphics.drawImage(enemy.getEnemyImage(),e.getX()+(-rizer.getVX()),500,55,80,null);
			}
			actionPerformedEnemy();
		}
	}
	//Updates armed enemies
	public void updateArmedEnemy() {
		ArrayList enemiesWithGuns = enemy.getArmedEnemies();
		for (int i = 0; i < enemiesWithGuns.size();i++) {
			Enemy e = (Enemy) enemiesWithGuns.get(i);
			if (e.isVisible(e))  {
				graphics.drawImage(enemy.getArmedEnemyImage(),975,500,55,80,null);
				actionPerformedArmedEnemy();
			}
		}
	}
	//Checks if the enemy is visible on the screen and then either moves the enemy or removes it from the ArrayList.
	public void actionPerformedArmedEnemy() {
		ArrayList enemiesWithGuns = enemy.getArmedEnemies();
		for (int i = 0; i < enemiesWithGuns.size();i++) {
			Enemy e = (Enemy) enemiesWithGuns.get(i);
			if (!e.isVisible(e)) {
				enemiesWithGuns.remove(e);
			}
		}
	}
	//Checks if the enemy is visible on the screen and then either moves the enemy or removes it from the ArrayList.
	public void actionPerformedEnemy() {
		ArrayList enemies = enemy.getEnemies();
		for (int i = 0; i < enemies.size();i++)	{
			Enemy e = (Enemy) enemies.get(i);
			if (!e.isVisible(e)) 
				enemies.remove(e);
			else
				e.move();
		}
	}
	//Draws the bullets stored in a ArrayList
	public void drawBullets() 	{
		ArrayList bullets = rizer.getBullets();
		for (int i = 0; i < bullets.size();i++) {
			Bullet m = (Bullet) bullets.get(i);
			m.setVelX(1);
			m.setX(m.getVelX()+m.getX());
			if (m.isAlive(m))
				graphics.drawImage(rizer.getBulletImage(bulletSprite),m.getX(),522,14,15,null);
			actionPerformedBullets();
		}
	}
	//Draws the objects on the screen
	public void drawObjects() {
		for (int x=0; x < objectContainer.length;x++) {
			for (int y = 0; y < objectContainer[0].length;y++) {
				if (objectContainer[x][y]!=null)
					drawObject(x,y);
			}
		}
	}
	//Draws the objects on the screen
	public void drawObject(int x, int y) {
		Object currentObject = objectContainer[x][y];
		int w = currentObject.getWidth();
		int h = currentObject.getHeight();
		Rectangle2D objectRec = new Rectangle2D.Double(currentObject.getX(),460,w,h);
		graphics.setColor(Color.CYAN);
		graphics.fill(objectRec);
	}
	//Updates the position of the objects on the screen.
	public void updateObjects() {
		if (k.getRightKey()) {
			for (int x=0; x < objectContainer.length;x++) {
				for (int y = 0; y < objectContainer[0].length;y++) {
					if (objectContainer[x][y]!=null) {
						Object currentObject = objectContainer[x][y];
						if (k.getRightKey())
							currentObject.setX(currentObject.getX()-1);
						else {
							System.out.println("DONE");
							currentObject.setX(currentObject.getX());
						}
					}
				}
			}
		}
	}
	//Update the score
	public void updateScore() {
		score+=10;
	}
	//Draws the score
	public void drawScore() {
		String scoreString = "Score: ";
		scoreString+=score;
		graphics.setColor(Color.WHITE);
		Font font = new Font("Times New Roman",Font.ITALIC,25);
		graphics.setFont(font);
		graphics.drawString(scoreString,50,25);
	}
	//Resets the game after the player loses
	public void resetGame() {
		resetLevel();
		resetRizer();
		resetEnemies();
		resetScore();
		resetLives();
		drawLoseScreen();
	}
	//updates the game state
	public void updateGameState() {
		GAME_STATE=GAME_PLAY;
	}
	//draw things to the screen
	public void draw() 	{
		if (GAME_STATE==GAME_MENU)
			drawMenu();
		else if (GAME_STATE==GAME_PLAY) {
			drawLevel();
			drawObjects();
			update();
		}
	}
	//Updates the lives
	public void updateLives()	{
		lives--;//Decrements the lives if the player died	
		if (lives==0) //checks if the player lost and then resets the game
			GAME_STATE = GAME_RESET;
	}
	//Draws the # of lives on the screen
	public void drawLives() {
		String livesString = "Lives: ";
		livesString+=lives;
		graphics.setColor(Color.WHITE);
		Font font = new Font("Times New Roman",Font.ITALIC,25);
		graphics.setFont(font);
		graphics.drawString(livesString,200,25);
	}
	//Resets the character
	public void resetRizer() {
		BufferedImage br1 = charSprite.getSprite(200,6,25,40);
	}
	//Resets the enemies
	public void resetEnemies() {
		resetNormalEnemies();
		resetArmedEnemies();
	}
	//Resets the enemies by removing everything from the ArrayList
	public void resetNormalEnemies() {
		ArrayList enemies = enemy.getEnemies();
		enemies.removeAll(enemies);
	}
	//Resets the enemies by removing everything from the ArrayList
	public void resetArmedEnemies() {
		ArrayList armedEnemies = enemy.getArmedEnemies();
		armedEnemies.removeAll(armedEnemies);
	}
	//Resets the level
	public void resetLevel() {
		BufferedImage level1 = levelSprite.getSprite(0,20,3456,110);
		resetVX();
		graphics.drawImage(level1,0,0,9000,768,null);
	}
	//Sets the variable that was used to keep track of how the background has scrolled to 0
	public void resetVX() {
		rizer.vX=0;
	}
	//Resets the score to 0
	public void resetScore() {
		score=0;
	}
	//Resets the lives to 3
	public void resetLives() {
		lives=3;
	}
	//Draws the lose screen when player looses
	public void drawLoseScreen() {
		Rectangle2D rec = new Rectangle2D.Double(200,200,650,100);
		graphics.setColor(Color.CYAN);
		graphics.fill(rec);
		String str = "YOU DIED! CLICK TO TRY AGAIN";
		graphics.setColor(Color.RED);
		Font f = new Font("Comic Sans",Font.BOLD,35);
		graphics.setFont(f);
		graphics.drawString(str,240,265);

		if (mouse.mouseClicked())
			updateGameState();
	}
	//Draws the menu on the screen
	public void drawMenu() {
		File file = new File("background.gif");
		try {
			BufferedImage back = ImageIO.read(file);
			graphics.drawImage(back,0,0,WIDTH,HEIGHT,null);
		}
		catch (Exception e) {}
		String name = "DIP PATEL";
		Font font1 = new Font("Times New Roman", Font.BOLD,20);
		graphics.setFont(font1);
		graphics.setColor(Color.white);
		graphics.drawString(name,850,30);
		String classC = "ICS3U";
		graphics.setColor(Color.white);
		graphics.setFont(font1);
		graphics.drawString(classC,850,50);

		RoundRectangle2D playRec = new RoundRectangle2D.Double(15,50,300,150,75,75);
		graphics.setColor(Color.yellow);
		graphics.fill(playRec);
		String play = "PLAY";
		Font font = new Font("Times New Roman", Font.BOLD,35);
		graphics.setFont(font);
		graphics.setColor(Color.black);
		graphics.drawString(play, 115, 140);
		RoundRectangle2D InstRec = new RoundRectangle2D.Double(15,225,300,150,75,75);
		graphics.setColor(Color.yellow);
		graphics.fill(InstRec);

		String inst = "INSTRUCTIONS";
		Font ff = new Font("Times New Roman", Font.BOLD,35);
		graphics.setFont(ff);
		graphics.setColor(Color.black);
		graphics.drawString(inst, 45, 300);
		graphics.drawString(inst, 45, 300);
		graphics.drawString(play, 115, 140);

		
		if (k.getDownKey()) {
			graphics.setColor(Color.cyan);
			graphics.fill(InstRec);		
			graphics.setColor(Color.black);
			graphics.drawString(inst, 45, 300);
			if (k.getEnterKey()) {
				drawInstructions();
			}
		}
		else if (k.getEnterKey())
			GAME_STATE=GAME_PLAY;
	}
	//Draws instructions on the screen
	public void drawInstructions() {

		Rectangle2D cover = new Rectangle2D.Double(0,0,WIDTH,HEIGHT);
		graphics.setColor(Color.cyan);
		graphics.fill(cover);
		try {
			File f = new File("instructionsText.png");
			BufferedImage bi = ImageIO.read(f);
			graphics.drawImage(bi,150,100,700,500,null);
		} catch (Exception e) {}

		String str = "Press Right key to PLAY!";
		graphics.setColor(Color.GRAY);
		Font font1 = new Font("Times New Roman", Font.BOLD,35);
		graphics.setFont(font1);
		graphics.drawString(str,150,100);
		if (k.getRightKey()&&!k.rightKeyReleased()) {
			GAME_STATE=GAME_PLAY;
		}	
		else 
			k.setRelRightFalse();
	}
	public void drawMenuAgain() {

		String name = "DIP PATEL";
		Font font1 = new Font("Times New Roman", Font.BOLD,20);
		graphics.setFont(font1);
		graphics.setColor(Color.white);
		graphics.drawString(name,850,30);

		String classC = "ICS3U";
		graphics.setColor(Color.white);
		graphics.setFont(font1);
		graphics.drawString(classC,850,50);

		RoundRectangle2D playRec = new RoundRectangle2D.Double(15,50,300,150,75,75);
		graphics.setColor(Color.yellow);
		graphics.fill(playRec);
		String play = "PLAY";
		Font font = new Font("Times New Roman", Font.BOLD,35);
		graphics.setFont(font);
		graphics.setColor(Color.black);
		graphics.drawString(play, 115, 140);
		RoundRectangle2D InstRec = new RoundRectangle2D.Double(15,225,300,150,75,75);
		graphics.setColor(Color.yellow);
		graphics.fill(InstRec);

		String inst = "INSTRUCTIONS";
		Font ff = new Font("Times New Roman", Font.BOLD,35);
		graphics.setFont(ff);
		graphics.setColor(Color.black);
		graphics.drawString(inst, 45, 300);
		graphics.drawString(inst, 45, 300);
		graphics.drawString(play, 115, 140);

		if (k.getDownKey()) {
			graphics.setColor(Color.black);
			graphics.drawString(inst, 45, 300);
			graphics.setColor(Color.cyan);
			graphics.fill(InstRec);		
			if (k.getEnterKey()) {
				drawInstructions();
			}
		}
	}
	//Draws the level
	public void drawLevel()	{
		if (GAME_STATE==GAME_PLAY) {
			BufferedImage level1 = levelSprite.getSprite(0,20,3456,110);
			graphics.drawImage(level1,0,0,9000,768,null);
		}
	}

	//Checks if each bullet in the ArrayList has hit the enemy and then removes it from the ArrayList.
	public void actionPerformedBullets() {
		ArrayList bullets = rizer.getBullets();
		for (int i = 0; i < bullets.size();i++) {
			Bullet m = (Bullet) bullets.get(i);
			if (m.isAlive(m))
				m.move();
			else {
				bullets.remove(m);
			}
		}
	}
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game); //game is a component because it extends Canvas
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);        
		game.start();
	}

	public Game() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		System.out.println(HEIGHT);
		frame = new JFrame();
		mouse = new Mouse();
		objectLoader = new ObjectLoader();
		k = new KeyBoard();
		charSprite = new SpriteSheet("billrizer.png");
		enemySprite = new SpriteSheet("ContraSheet3.gif");
		enemyDeadSprite = new SpriteSheet("enemy.png");
		levelSprite = new SpriteSheet("Level.png");
		bulletSprite = new SpriteSheet("bullet.png");
		bossSprite = new SpriteSheet("ContraSheet6.gif");
		//charWalkingSprite = new SpriteSheet("LanceJumpingR.gif");
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addKeyListener(k);
		//KEYBOARD and MOUSE handling code goes here
	}

	//starts a new thread for the game
	public synchronized void start() {
		thread = new Thread(this, "Game");
		running = true;
		thread.start();
	}

	//main game loop
	public void run() {
		init();
		long startTime = System.nanoTime();
		double ns = 1000000000.0 / UPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		long secondTimer = System.nanoTime();
		while(running) {
			long now = System.nanoTime();
			delta += (now - startTime) / ns;
			startTime = now;
			while(delta >= 1) {
				update();
				delta--;
				updates++;
			}
			objectLoader.closeFile();
			render();
			frames++;

			if(System.nanoTime() - secondTimer > 1000000000) {
				this.frame.setTitle(updates + " ups  ||  " + frames + " fps");
				secondTimer += 1000000000;
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy(); //method from Canvas class

		if(bs == null) {
			createBufferStrategy(3); //creates it only for the first time the loop runs (trip buff)
			return;
		}

		graphics = (Graphics2D)bs.getDrawGraphics();
		draw();
		graphics.dispose();
		bs.show();
	}

	//stops the game thread and quits
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
