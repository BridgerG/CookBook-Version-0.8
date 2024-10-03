package theCookbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Cookbook implements Serializable{

	ArrayList<Recipe> array = new ArrayList<Recipe>();
	String secname = "";
	int namechange = 0, currentRecipe = -1;
	Date date = new Date();
	
	Cookbook (String secname) {
		
		this.secname = secname;
		
	}
	
	public String getSecName() {
		
		return secname;
		
	}
	
	public void setSecName(String secname) {
		
		this.secname = secname;
		
	}
	
	public void addRecipe() {
		
		Recipe recipe = new Recipe();
		
		array.add(recipe);
	
	}
	public void addRecipe(String rname, String theRecipe, String instructions, String picture) {
		
		Recipe recipe = new Recipe(rname, theRecipe, instructions, picture);
		
		array.add(recipe);
	
	}
	
	public Recipe getRecipe (int i) {

		currentRecipe = i;
		return array.get(i);
		
	}
	
	public int getNumOfRecipe() {
		
		return array.size();
		
	}
	
	public void removeRecipe (int r) {
		
		array.remove(r);

	}
	
	public Date dateCreated () {
		
		return date;
		
	}
	
	public int numberOfRecipes() {
		
		return array.size();
		
	}
	
	
}
