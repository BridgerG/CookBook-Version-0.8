# CookBook-Version-0.8

## This is a Beta Version of a digital Cookbook. 

## I built it so I could Finally close all the tabs I have that only exist to bookmark Recipes. Also to make it easier to locate recipes that I like and allow myself to easily edit them.

## You need all three files to run this code in a java coding application.... but once you do that it is very Intuitive to use.

## 	I am very proud of this code below for the simple reason that it probably saved 200 lines of code by existing

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
