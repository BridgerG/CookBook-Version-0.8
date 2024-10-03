# CookBook-Version-0.8

## This is a Beta Version of a digital Cookbook. 

## I built it so I could Finally close all the tabs I have that only exist to bookmark Recipes. Also to make it easier to locate recipes that I like and allow myself to easily edit them.

## You need all three files to run this code in a java coding application.... but once you do that it is very Intuitive to use.

## I am very proud of this code below for the simple reason that it probably saved 200 lines of code by existing

    public void saveProgress () {

		  try { 

			  ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)); 
 
			  oos.writeObject(cookbookArray); 
			  oos.close(); 

			  System.out.println("namesList serialized"); 
	
		  } 
		  catch (IOException ioe) { 
		
			  ioe.printStackTrace(); 
		  }
	  }

### The reasons not to use this code right now are nuemorous with the important parts being, it is still in ruff shape, the UI is ugly as sin, and the code is insanely confusing because I had to drastically change what the project had to do in order to work at the last second. But just wait until I fully finish this beauty, then it will be the best thing on the Market BABY.
