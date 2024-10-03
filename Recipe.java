package theCookbook;

import java.io.Serializable;
import java.util.Date;

public class Recipe implements Serializable{

	String rname = "", theRecipe = "", intstuction = "", picture = "";
	Date date = new Date();
	
	Recipe() {
	}
	
	Recipe(String rname, String theRecipe, String instructions, String picture) {
		
		this.theRecipe = theRecipe;
		this.intstuction = instructions;
		this.picture = picture;
		this.rname = rname;
		
	}
	
	public String getName() {
		
		return rname;
		
	}
	
	public void setName(String rname) {
		
		this.rname = rname;
		
	}
	
	public String getRecipe() {
		
		return theRecipe;
		
	}
	
	public void setRecipe(String theRecipe) {
		
		this.theRecipe = theRecipe;
		
	}
	public String getIntructions() {
		
		return intstuction;
		
	}
	
	public void setIntructions(String intstuction) {
		
		this.intstuction = intstuction;
		
	}
	public String getPicture() {
		
		return picture;
		
	}
	
	public void setPicture(String picture) {
		
		this.picture = picture;
		
	}
	
	public Date getDateCreated() {
		
		return date;
	}
}
