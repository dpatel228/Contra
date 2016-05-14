import java.awt.Color;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;


public class ObjectLoader {
		private Scanner scanner;
		private Vector<String> objects = new Vector<String>(); //Stores the text file
		private Object[][] objectContainer; //Contains all the objects 
		//Opens the text file
		public void openObjectFile(String fileName) {
			scanner = null;
			File file = new File(fileName);
	    	try {
	    		scanner = new Scanner(file);
	    	} catch (Exception e) {}
	    }
		//Creats the objects
		public void createObjects()	{
			if(!(scanner.equals(null)))
			{
				String textLine = "";
				while(scanner.hasNext()) { 
					textLine = scanner.nextLine(); 
					objects.add(textLine); //Adds each line of the text file to the vector
				}
			} 
			objectContainer = new Object[objects.size()][16]; 
			int x = 300;
			for(int i=0; i<objects.size(); i++)	{ //For loop goes through each letter in the vector and creates a object
				String line = objects.elementAt(i); 
				for(int j=0; j<16; j++)	{
					if (line.charAt(j)=='O')
						objectContainer[i][j] = null;
					else if(line.charAt(j) == 'S') {
						 objectContainer[i][j] = new Object(x,105,62);
						 x+=505;
					}
					else if(line.charAt(j) == 'M') {
						objectContainer[i][j] = new Object(x,175,62);
						x+=575;
					}
					else if(line.charAt(j) == 'B') {
						objectContainer[i][j] = new Object(x,300,62);
						x+=900;
					}
				}
			}
		}
		//returns the object container
		public Object[][] getObjectContainer() {
			return objectContainer;
		}
		public Vector<String> getVector() {
			return objects;
		}
		//Closes the scanner
		public void closeFile()
		{
			if(!scanner.equals(null))
				scanner.close();
		}
		
		
}
