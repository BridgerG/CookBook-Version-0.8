package theCookbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
//import javafx.scene.text.TextField;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TheActualCookBook  extends Application {

	int row = 1, collum = 1;
	Boolean delete = false;
	GridPane grid = new GridPane();
	File file = new File("CookbookStorage.txt");
	
	ArrayList<Cookbook> cookbookArray = null;
	ArrayList<Button> cookbookButtons = new ArrayList<Button>();
	
	public static void main(String[] args) {

		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane pane = new BorderPane();

		//Set up top bar to allow for the creation and deletion of files
	    HBox hBox = new HBox(5); 
	    hBox.setPrefWidth(150);
	    hBox.setAlignment(Pos.TOP_LEFT);
	    hBox.setPadding(new Insets(15, 15, 15, 15));
	    hBox.setStyle("-fx-background-color: white");
	    Button newCookbook = new Button("Make New Chapter");
	    Button deleteCookbook = new Button("Delete Chapter");
	    hBox.getChildren().add(newCookbook);
	    hBox.getChildren().add(deleteCookbook);
		pane.setTop(hBox);
		
		//Setting up grid pane
		pane.setCenter(grid);
		grid.setHgap(40);
		grid.setVgap(40);
		Scene scene = new Scene(pane, 1700, 1000);
		primaryStage.setTitle("Cookbook");
		primaryStage.setScene(scene);
		
		
		//Make the thing that initates if x file exist here
		if (file.exists()) {
			
			ObjectInputStream getstuff = new ObjectInputStream(new FileInputStream(file));
			
			cookbookArray = (ArrayList<Cookbook>) getstuff.readObject();
			
			int i = 0;

			while (cookbookArray.size() > cookbookButtons.size()) {
				
				Button b = new Button(cookbookArray.get(i).secname);
				i++;
				cookbookButtons.add(b);
				cookbookButtons.get(cookbookButtons.size() - 1).setPrefHeight(100);
				cookbookButtons.get(cookbookButtons.size() - 1).setPrefWidth(150);
				
				if (collum < 9) {
				
					grid.add(cookbookButtons.get(cookbookButtons.size() - 1), collum, row, 1, 1);
					collum += 1;
			
				} else {
			
					grid.add(cookbookButtons.get(cookbookButtons.size() - 1), collum, row, 1, 1);
					collum = 1;
					row += 1;
			
				}
				saveProgress();
				
				b.setOnAction(d -> {
					
					GridPane rgrid = new GridPane();
					
					int num = grid.getColumnIndex(b) + (10 *(grid.getRowIndex(b) - 1));
					Cookbook activeCookbook = cookbookArray.get(num - 1);
					
					ArrayList<Button> recipeButtons= new ArrayList<Button>();
					
					int t = -1;
					while(activeCookbook.getNumOfRecipe() > recipeButtons.size()) {
						
						t++;
						Button accessRecipe = new Button(activeCookbook.getRecipe(t).getName());
						int rrow = recipeButtons.size() / 10 + 1, rcollum = recipeButtons.size() % 10 + 1;
						recipeButtons.add(accessRecipe);

						recipeButtons.get(recipeButtons.size() - 1).setPrefHeight(100);
						recipeButtons.get(recipeButtons.size() - 1).setPrefWidth(150);
				
						if (rcollum < 9) {
					
							rgrid.add(recipeButtons.get(recipeButtons.size() - 1), rcollum, rrow, 1, 1);
							rcollum += 1;
				
						} else {
				
							rgrid.add(recipeButtons.get(recipeButtons.size() - 1), rcollum, rrow, 1, 1);
							rcollum = 1;
							rrow += 1;
				
						}
						saveProgress();

						accessRecipe.setOnAction(q -> {
							
							int numButton = rgrid.getColumnIndex(accessRecipe) + (10 *(rgrid.getRowIndex(accessRecipe) - 1));

							Recipe thisRecipe = activeCookbook.getRecipe(numButton - 1);

					
							if (delete == true) {
						
								
								BorderPane deletePane = new BorderPane();
								deletePane.setPadding(new Insets(15, 15, 15, 15));
								Label deleteLabel = new Label("Are you sure you want to delete this recipe; " + thisRecipe.getName());
								deletePane.setTop(deleteLabel);
						
								HBox deleteHBox = new HBox();
								deleteHBox.setAlignment(Pos.CENTER);
								Button deleteYes = new Button("Yes");
								Button deleteNo = new Button("No"); 
								deleteHBox.getChildren().addAll(deleteYes, deleteNo);
								deletePane.setBottom(deleteHBox);
						
								Scene deleteScene = new Scene(deletePane);
								Stage deleteStage = new Stage();
								deleteStage.setScene(deleteScene);
								deleteStage.setResizable(false);
								deleteStage.show();
						
								deleteYes.setOnAction(w -> {
							
									int rcollum2 = rgrid.getColumnIndex(b), rrow2 = rgrid.getRowIndex(b);
									activeCookbook.removeRecipe(numButton - 1);
									rgrid.getChildren().remove(numButton - 1);
									recipeButtons.remove(numButton - 1);
									deleteStage.close();
									delete = false;
									
									if (rcollum2 == 1 && rrow2 > 1) {
										
										rcollum2 = 9;
										rrow2 -= 1;
										
									} else {
										
										rcollum2 -= 1;
									}
									
									saveProgress();
							
								});
	
								deleteNo.setOnAction(w -> {
							
									deleteStage.close();
									saveProgress();

								});
						
					
							} else {
								//Makes the recipe pane for all of its buttons
								BorderPane recipeBorder = new BorderPane();
								StackPane recipeStack = new StackPane();
					
								HBox recipeButtonsHBox = new HBox();
								recipeButtonsHBox.setAlignment(Pos.CENTER_LEFT);
								recipeButtonsHBox.setPadding(new Insets(15, 15, 15, 15));
								Button saveRecipe = new Button("Save");
								recipeButtonsHBox.getChildren().add(saveRecipe);
								
								HBox forthingbelow = new HBox();
								forthingbelow.setStyle("-fx-background-color: white");
								Label recipeName = new Label(thisRecipe.getName());
								recipeName.setFont(new Font("Ariel", 48));
								forthingbelow.setAlignment(Pos.CENTER);
								forthingbelow.getChildren().add(recipeName);
								recipeStack.getChildren().add(forthingbelow);
								recipeStack.getChildren().add(recipeButtonsHBox);
								recipeBorder.setTop(recipeStack);
					
								VBox recipeSection = new VBox();
								Label theWordRecipe = new Label("Recipe");
								theWordRecipe.setFont(new Font("Ariel", 32));
								TextArea recipeText = new TextArea(thisRecipe.getRecipe());
								recipeText.setWrapText(true);
								recipeText.setPrefWidth(850);
								recipeText.setPrefHeight(800);
								recipeSection.setAlignment(Pos.CENTER);
								recipeSection.getChildren().addAll(theWordRecipe, recipeText);
								recipeBorder.setLeft(recipeSection);
					
								VBox instructionSection = new VBox();
								Label theWordInstruction = new Label("Instruction");
								theWordInstruction.setFont(new Font("Ariel", 32));
								TextArea instructions = new TextArea(thisRecipe.getIntructions());
								instructions.setPrefWidth(850);
								instructions.setPrefHeight(800);
								instructionSection.setAlignment(Pos.CENTER);
								instructionSection.getChildren().addAll(theWordInstruction, instructions);
								recipeBorder.setRight(instructionSection);
					
								recipeBorder.setPadding(new Insets(10, 10, 10, 10));
								Scene recipeScene = new Scene(recipeBorder, 1700, 1000);
								Stage recipeStage = new Stage();
								recipeStage.setScene(recipeScene);
								recipeStage.show();
								
								saveRecipe.setOnAction(s -> {
									
									thisRecipe.setIntructions(instructions.getText());
									thisRecipe.setRecipe(recipeText.getText());
									saveProgress();

								});
					
							}
						});
						
					}

					if (delete == true) {
						
						BorderPane deleteCookbookPane = new BorderPane();
						deleteCookbookPane.setPadding(new Insets(15, 15, 15, 15));

						Label deleteCookbookLabel = new Label("Are you sure you want to delete this recipe; " + activeCookbook.getSecName());
						deleteCookbookPane.setTop(deleteCookbookLabel);
						
						HBox deleteCookbookHBox = new HBox();
						deleteCookbookHBox.setAlignment(Pos.CENTER);
						Button deleteCookbookYes = new Button("Yes");
						Button deleteCookbookNo = new Button("No"); 
						deleteCookbookHBox.getChildren().addAll(deleteCookbookYes, deleteCookbookNo);
						deleteCookbookPane.setBottom(deleteCookbookHBox);
						
						Scene deleteCookbookScene = new Scene(deleteCookbookPane);
						Stage deleteCookbookStage = new Stage();
						deleteCookbookStage.setScene(deleteCookbookScene);
						deleteCookbookStage.setResizable(false);
						deleteCookbookStage.show();
						
						deleteCookbookYes.setOnAction(w -> {
							
							cookbookArray.remove(num - 1);
							cookbookButtons.remove(num - 1);
							grid.getChildren().remove(num - 1);
							deleteCookbookStage.close();
							delete = false;
							if (collum == 1 && row > 1) {
								
								collum = 9;
								row -= 1;
								
							} else {
								
								collum -= 1;
							}


							
						});
	
						deleteCookbookNo.setOnAction(w -> {
							
							deleteCookbookStage.close();
							
						});
					
					} else { 
						
						StackPane cookbookNameforLabel = new StackPane();
						BorderPane pane2 = new BorderPane();
						pane2.setCenter(rgrid);
						rgrid.setHgap(40);
						rgrid.setVgap(40);
						
						//Set up top bar to allow for the creation and deletion of files
						HBox hBox2 = new HBox(5); 
						hBox2.setPrefWidth(150);
						hBox2.setAlignment(Pos.TOP_LEFT);
						hBox2.setPadding(new Insets(15, 15, 15, 15));
						hBox2.setStyle("-fx-background-color: white");
						Button goBack = new Button("Go Back");
						Button newRecipe = new Button("Make New Recipe");
						Button deleteRecipe= new Button("Delete Recipe");
						hBox2.getChildren().add(goBack);
						hBox2.getChildren().add(newRecipe);
						hBox2.getChildren().add(deleteRecipe);
						cookbookNameforLabel.getChildren().add(hBox2);
					
						//Create a label and add it to the StackPane with the name of the cookbook on it
						Label cookbookNameLabel = new Label(activeCookbook.getSecName());
						cookbookNameLabel.setFont(new Font("Ariel", 32));
						cookbookNameLabel.setAlignment(Pos.CENTER);
						cookbookNameforLabel.getChildren().add(cookbookNameLabel);
						pane2.setTop(cookbookNameforLabel);

						goBack.setOnAction(o -> {
						
						primaryStage.setScene(scene);
						delete = false;
						
						});
						
						newRecipe.setOnAction(o -> {
						
							Label lable2 = new Label("Name of the new Recipe:");
							TextField textbox2 = new TextField();
							TextField error2 = new TextField();
							Button submit2 = new Button("Create");
						
							error2.setEditable(false);
							
							GridPane getName2 = new GridPane();
							getName2.setStyle("-fx-background-color: white");
							getName2.setPadding(new Insets(10, 10, 10, 10));
							getName2.setHgap(10);
							getName2.setVgap(20);
							getName2.add(lable2, 0, 0, 1, 1);
							getName2.add(textbox2, 1, 0, 7, 1);
							
							HBox bottom2 = new HBox();
							bottom2.getChildren().add(submit2);
							bottom2.getChildren().add(error2);
							getName2.add(bottom2, 0, 1, 5, 1);
						
							Scene scene3 = new Scene(getName2);
							Stage thirdStage = new Stage();
							thirdStage.setScene(scene3);
							thirdStage.setTitle("Create New Recipe");
							thirdStage.show();
							saveProgress(); 
						
							submit2.setOnAction(p -> {
								
								if (textbox2.getText().length() > 22) {
							
									error2.setText("Exceeded 21 Character Limit");
									saveProgress();

								} else if (textbox2.getText().length() == 0) {
							
									error2.setText("Please Enter Name");
									saveProgress();
							
								} else {
										
										
									Button accessRecipe = new Button(textbox2.getText());				
							
									//This button pulls up a seperate window that you can Input the recipe and instructions in
									accessRecipe.setOnAction(q -> {
								
										int numButton = rgrid.getColumnIndex(accessRecipe) + (10 *(rgrid.getRowIndex(accessRecipe) - 1));

										Recipe thisRecipe = activeCookbook.getRecipe(numButton - 1);

								
										if (delete == true) {
									
											
											BorderPane deletePane = new BorderPane();
											deletePane.setPadding(new Insets(15, 15, 15, 15));
											Label deleteLabel = new Label("Are you sure you want to delete this recipe; " + thisRecipe.getName());
											deletePane.setTop(deleteLabel);
									
											HBox deleteHBox = new HBox();
											deleteHBox.setAlignment(Pos.CENTER);
											Button deleteYes = new Button("Yes");
											Button deleteNo = new Button("No"); 
											deleteHBox.getChildren().addAll(deleteYes, deleteNo);
											deletePane.setBottom(deleteHBox);
									
											Scene deleteScene = new Scene(deletePane);
											Stage deleteStage = new Stage();
											deleteStage.setScene(deleteScene);
											deleteStage.setResizable(false);
											deleteStage.show();
											saveProgress();

									
											deleteYes.setOnAction(w -> {
										
												int rcollum2 = rgrid.getColumnIndex(b), rrow2 = rgrid.getRowIndex(b);
												activeCookbook.removeRecipe(numButton - 1);
												recipeButtons.remove(numButton - 1);
												rgrid.getChildren().remove(numButton - 1);
												deleteStage.close();
												delete = false;
												
												if (rcollum2 == 1 && rrow2 > 1) {
													
													rcollum2 = 9;
													rrow2 -= 1;
													
												} else {
													
													rcollum2 -= 1;
												}
												
												saveProgress();

										
											});
				
											deleteNo.setOnAction(w -> {
										
												saveProgress();
												deleteStage.close();
										
											});
									
								
										} else {
											//Makes the recipe pane for all of its buttons
											BorderPane recipeBorder = new BorderPane();
											StackPane recipeStack = new StackPane();
								
											HBox recipeButtonsHBox = new HBox();
											recipeButtonsHBox.setAlignment(Pos.CENTER_LEFT);
											recipeButtonsHBox.setPadding(new Insets(15, 15, 15, 15));
											Button saveRecipe = new Button("Save");
											recipeButtonsHBox.getChildren().add(saveRecipe);
											
											HBox forthingbelow = new HBox();
											forthingbelow.setStyle("-fx-background-color: white");
											Label recipeName = new Label(thisRecipe.getName());
											recipeName.setFont(new Font("Ariel", 48));
											forthingbelow.setAlignment(Pos.CENTER);
											forthingbelow.getChildren().add(recipeName);
											recipeStack.getChildren().add(forthingbelow);
											recipeStack.getChildren().add(recipeButtonsHBox);
											recipeBorder.setTop(recipeStack);
								
											VBox recipeSection = new VBox();
											Label theWordRecipe = new Label("Recipe");
											theWordRecipe.setFont(new Font("Ariel", 32));
											TextArea recipeText = new TextArea(thisRecipe.getRecipe());
											recipeText.setWrapText(true);
											recipeText.setPrefWidth(850);
											recipeText.setPrefHeight(800);
											recipeSection.setAlignment(Pos.CENTER);
											recipeSection.getChildren().addAll(theWordRecipe, recipeText);
											recipeBorder.setLeft(recipeSection);
								
											VBox instructionSection = new VBox();
											Label theWordInstruction = new Label("Instruction");
											theWordInstruction.setFont(new Font("Ariel", 32));
											TextArea instructions = new TextArea(thisRecipe.getIntructions());
											instructions.setPrefWidth(850);
											instructions.setPrefHeight(800);
											instructionSection.setAlignment(Pos.CENTER);
											instructionSection.getChildren().addAll(theWordInstruction, instructions);
											recipeBorder.setRight(instructionSection);
								
											recipeBorder.setPadding(new Insets(10, 10, 10, 10));
											Scene recipeScene = new Scene(recipeBorder, 1700, 1000);
											Stage recipeStage = new Stage();
											recipeStage.setScene(recipeScene);
											recipeStage.show();
											
											saveRecipe.setOnAction(s -> {
												
												thisRecipe.setIntructions(instructions.getText());
												thisRecipe.setRecipe(recipeText.getText());
												saveProgress();

											});
								
										}
									});
							
									int rrow = activeCookbook.getNumOfRecipe() / 10 + 1, rcollum = activeCookbook.getNumOfRecipe() % 10 + 1;

									recipeButtons.add(accessRecipe);
									activeCookbook.addRecipe();
									activeCookbook.getRecipe(activeCookbook.getNumOfRecipe() - 1).setName(textbox2.getText());
							
									recipeButtons.get(recipeButtons.size() - 1).setPrefHeight(100);
									recipeButtons.get(recipeButtons.size() - 1).setPrefWidth(150);
							
									if (rcollum < 9) {
								
										rgrid.add(recipeButtons.get(recipeButtons.size() - 1), rcollum, rrow, 1, 1);
										rcollum += 1;
							
									} else {
							
										rgrid.add(recipeButtons.get(recipeButtons.size() - 1), rcollum, rrow, 1, 1);
										rcollum = 1;
										rrow += 1;
							
									}	
									
									saveProgress();
									thirdStage.close();
									
								}
						
							});	
						});
					
						deleteRecipe.setOnAction(o -> {
							
							saveProgress();
							delete = !delete;
							
						});
						
						Scene recipeScene = new Scene(pane2, 1700, 1000);
						primaryStage.setScene(recipeScene);
						saveProgress();

					
						}
					});
				
			}
		
		deleteCookbook.setOnAction(e -> {
			
			delete = !delete;
			
			
		});
			
			} else {
			
			cookbookArray = new ArrayList<Cookbook>();
			saveProgress();
			
		}	
		
		//Section that makes buttons do things
		//This button makes a second scene that allows the user to make a new Cookbook class
		newCookbook.setOnAction(e -> {
			
			Label lable = new Label("Name of the new Chapter:");
			TextField textbox = new TextField();
			TextField error = new TextField();
			Button submit = new Button("Create");
			
			error.setEditable(false);
			
			GridPane getName = new GridPane();
		    getName.setStyle("-fx-background-color: white");
		    getName.setPadding(new Insets(10, 10, 10, 10));
			getName.setHgap(10);
			getName.setVgap(20);
		    getName.add(lable, 0, 0, 1, 1);
		    getName.add(textbox, 1, 0, 7, 1);
		    
		    HBox bottom = new HBox();
		    bottom.getChildren().add(submit);
		    bottom.getChildren().add(error);
		    getName.add(bottom, 0, 1, 5, 1);
			
			Scene scene2 = new Scene(getName);
			Stage secondaryStage = new Stage();
			secondaryStage.setScene(scene2);
			secondaryStage.setTitle("Create New Chapter");
			secondaryStage.show();
			
			submit.setOnAction(a -> {
			if (textbox.getText().length() > 22) {
				
				error.setText("Exceeded 21 Character Limit");
				saveProgress();

			} else if (textbox.getText().length() == 0) {
				
				error.setText("Please Enter Name");
				saveProgress();

				
			} else {
			
				
				Button b = new Button(textbox.getText());				
				cookbookButtons.add(b);
				Cookbook cookbook = new Cookbook(textbox.getText());
				cookbookArray.add(cookbook);
				
				//Setting the buttons to make a new Pane that shows what recipes are in the cookbook along with the actions that can be taken
				b.setOnAction(d -> {
					
					GridPane rgrid = new GridPane();
					rgrid.setPadding(new Insets(15, 15, 15, 15));
					
					System.out.print(grid.getColumnIndex(b));
					int num = grid.getColumnIndex(b) + (10 *(grid.getRowIndex(b) - 1));
					Cookbook activeCookbook = cookbookArray.get(num - 1);
					
					if (delete == true) {
						
						BorderPane deleteCookbookPane = new BorderPane();
						deleteCookbookPane.setPadding(new Insets(15, 15, 15, 15));

						Label deleteCookbookLabel = new Label("Are you sure you want to delete this recipe; " + activeCookbook.getSecName());
						deleteCookbookPane.setTop(deleteCookbookLabel);
						
						HBox deleteCookbookHBox = new HBox();
						deleteCookbookHBox.setAlignment(Pos.CENTER);
						Button deleteCookbookYes = new Button("Yes");
						Button deleteCookbookNo = new Button("No"); 
						deleteCookbookHBox.getChildren().addAll(deleteCookbookYes, deleteCookbookNo);
						deleteCookbookPane.setBottom(deleteCookbookHBox);
						
						Scene deleteCookbookScene = new Scene(deleteCookbookPane);
						Stage deleteCookbookStage = new Stage();
						deleteCookbookStage.setScene(deleteCookbookScene);
						deleteCookbookStage.setResizable(false);
						deleteCookbookStage.show();
						
						deleteCookbookYes.setOnAction(w -> {
							
							cookbookArray.remove(num - 1);
							cookbookButtons.remove(num - 1);
							grid.getChildren().remove(num - 1);
							deleteCookbookStage.close();
							delete = false;
							if (collum == 1 && row > 1) {
								
								collum = 9;
								row -= 1;
								
							} else {
								
								collum -= 1;
							}

							saveProgress();

							
						});
	
						deleteCookbookNo.setOnAction(w -> {
							
							deleteCookbookStage.close();
							saveProgress();

							
						});
					
					} else { 
						
						StackPane cookbookNameforLabel = new StackPane();
						BorderPane pane2 = new BorderPane();
						pane2.setCenter(rgrid);
						rgrid.setHgap(40);
						rgrid.setVgap(40);
						
						//Set up top bar to allow for the creation and deletion of files
						HBox hBox2 = new HBox(5); 
						hBox2.setPrefWidth(150);
						hBox2.setAlignment(Pos.TOP_LEFT);
						hBox2.setPadding(new Insets(15, 15, 15, 15));
						hBox2.setStyle("-fx-background-color: white");
						Button goBack = new Button("Go Back");
						Button newRecipe = new Button("Make New Recipe");
						Button deleteRecipe= new Button("Delete Recipe");
						hBox2.getChildren().add(goBack);
						hBox2.getChildren().add(newRecipe);
						hBox2.getChildren().add(deleteRecipe);
						cookbookNameforLabel.getChildren().add(hBox2);
					
						//Create a label and add it to the StackPane with the name of the cookbook on it
						Label cookbookNameLabel = new Label(activeCookbook.getSecName());
						cookbookNameLabel.setFont(new Font("Ariel", 32));
						cookbookNameLabel.setAlignment(Pos.CENTER);
						cookbookNameforLabel.getChildren().add(cookbookNameLabel);
						pane2.setTop(cookbookNameforLabel);
						saveProgress();

						goBack.setOnAction(o -> {
						
						primaryStage.setScene(scene);
						delete = false;
						saveProgress();

						
						});
						
						newRecipe.setOnAction(o -> {
							
							Label lable2 = new Label("Name of the new Recipe:");
							TextField textbox2 = new TextField();
							TextField error2 = new TextField();
							Button submit2 = new Button("Create");
						
							error2.setEditable(false);
							
							GridPane getName2 = new GridPane();
							getName2.setStyle("-fx-background-color: white");
							getName2.setPadding(new Insets(10, 10, 10, 10));
							getName2.setHgap(10);
							getName2.setVgap(20);
							getName2.add(lable2, 0, 0, 1, 1);
							getName2.add(textbox2, 1, 0, 7, 1);
							
							HBox bottom2 = new HBox();
							bottom2.getChildren().add(submit2);
							bottom2.getChildren().add(error2);
							getName2.add(bottom2, 0, 1, 5, 1);
						
							Scene scene3 = new Scene(getName2);
							Stage thirdStage = new Stage();
							thirdStage.setScene(scene3);
							thirdStage.setTitle("Create New Recipe");
							thirdStage.show();
							saveProgress();
						
							submit2.setOnAction(p -> {
								
								if (textbox2.getText().length() > 22) {
							
									error2.setText("Exceeded 21 Character Limit");
									saveProgress();
							
								} else if (textbox2.getText().length() == 0) {
							
									error2.setText("Please Enter Name");
									saveProgress();

							
								} else {
							
									ArrayList<Button> recipeButtons= new ArrayList<Button>();

							
									Button accessRecipe = new Button(textbox2.getText());				
							
									//This button pulls up a seperate window that you can Input the recipe and instructions in
									accessRecipe.setOnAction(q -> {
								
										int numButton = rgrid.getColumnIndex(accessRecipe) + (10 *(rgrid.getRowIndex(accessRecipe) - 1));

										Recipe thisRecipe = activeCookbook.getRecipe(numButton - 1);

								
										if (delete == true) {
									
											
											BorderPane deletePane = new BorderPane();
											deletePane.setPadding(new Insets(15, 15, 15, 15));
											Label deleteLabel = new Label("Are you sure you want to delete this recipe; " + thisRecipe.getName());
											deletePane.setTop(deleteLabel);
									
											HBox deleteHBox = new HBox();
											deleteHBox.setAlignment(Pos.CENTER);
											Button deleteYes = new Button("Yes");
											Button deleteNo = new Button("No"); 
											deleteHBox.getChildren().addAll(deleteYes, deleteNo);
											deletePane.setBottom(deleteHBox);
									
											Scene deleteScene = new Scene(deletePane);
											Stage deleteStage = new Stage();
											deleteStage.setScene(deleteScene);
											deleteStage.setResizable(false);
											deleteStage.show();
											saveProgress();
									
											deleteYes.setOnAction(w -> {
										
												int rcollum2 = rgrid.getColumnIndex(b), rrow2 = rgrid.getRowIndex(b);
												activeCookbook.removeRecipe(numButton - 1);
												recipeButtons.remove(numButton - 1);
												rgrid.getChildren().remove(numButton - 1);
												deleteStage.close();
												delete = false;
												
												if (rcollum2 == 1 && rrow2 > 1) {
													
													rcollum2 = 9;
													rrow2 -= 1;
													
												} else {
													
													rcollum2 -= 1;
												}
												saveProgress();

										
											});
				
											deleteNo.setOnAction(w -> {
										
												deleteStage.close();
												saveProgress();

										
											});
									
								
										} else {
											//Makes the recipe pane for all of its buttons
											BorderPane recipeBorder = new BorderPane();
											StackPane recipeStack = new StackPane();
								
											HBox recipeButtonsHBox = new HBox();
											recipeButtonsHBox.setAlignment(Pos.CENTER_LEFT);
											recipeButtonsHBox.setPadding(new Insets(15, 15, 15, 15));
											Button saveRecipe = new Button("Save");
											recipeButtonsHBox.getChildren().add(saveRecipe);
											
											HBox forthingbelow = new HBox();
											forthingbelow.setStyle("-fx-background-color: white");
											Label recipeName = new Label(thisRecipe.getName());
											recipeName.setFont(new Font("Ariel", 48));
											forthingbelow.setAlignment(Pos.CENTER);
											forthingbelow.getChildren().add(recipeName);
											recipeStack.getChildren().add(forthingbelow);
											recipeStack.getChildren().add(recipeButtonsHBox);
											recipeBorder.setTop(recipeStack);
								
											VBox recipeSection = new VBox();
											Label theWordRecipe = new Label("Recipe");
											theWordRecipe.setFont(new Font("Ariel", 32));
											TextArea recipeText = new TextArea(thisRecipe.getRecipe());
											recipeText.setWrapText(true);
											recipeText.setPrefWidth(850);
											recipeText.setPrefHeight(800);
											recipeSection.setAlignment(Pos.CENTER);
											recipeSection.getChildren().addAll(theWordRecipe, recipeText);
											recipeBorder.setLeft(recipeSection);
								
											VBox instructionSection = new VBox();
											Label theWordInstruction = new Label("Instruction");
											theWordInstruction.setFont(new Font("Ariel", 32));
											TextArea instructions = new TextArea(thisRecipe.getIntructions());
											instructions.setPrefWidth(850);
											instructions.setPrefHeight(800);
											instructionSection.setAlignment(Pos.CENTER);
											instructionSection.getChildren().addAll(theWordInstruction, instructions);
											recipeBorder.setRight(instructionSection);
								
											recipeBorder.setPadding(new Insets(10, 10, 10, 10));
											Scene recipeScene = new Scene(recipeBorder, 1700, 1000);
											Stage recipeStage = new Stage();
											recipeStage.setScene(recipeScene);
											recipeStage.show();
											
											saveRecipe.setOnAction(s -> {
												
												thisRecipe.setIntructions(instructions.getText());
												thisRecipe.setRecipe(recipeText.getText());
												saveProgress();

											});
								
										}
									});
							
									recipeButtons.add(accessRecipe);
									int rrow = (rgrid.getRowIndex(rgrid)) / 10 + 1, rcollum = (rgrid.getColumnIndex(rgrid)) % 10 + 1;
									activeCookbook.addRecipe();
									activeCookbook.getRecipe(activeCookbook.getNumOfRecipe() - 1).setName(textbox2.getText());
							
									recipeButtons.get(recipeButtons.size() - 1).setPrefHeight(100);
									recipeButtons.get(recipeButtons.size() - 1).setPrefWidth(150);
							
									if (rcollum < 9) {
								
										rgrid.add(recipeButtons.get(recipeButtons.size() - 1), rcollum, rrow, 1, 1);
							
									} else {
							
										rgrid.add(recipeButtons.get(recipeButtons.size() - 1), rcollum, rrow, 1, 1);
							
									}	
							
									thirdStage.close();
									saveProgress();

								}
						
							});	
						});
					
						deleteRecipe.setOnAction(o -> {
							
							saveProgress();
							delete = !delete;
							
						});
						
						Scene recipeScene = new Scene(pane2, 1700, 1000);
						primaryStage.setScene(recipeScene);
					
						}
					});
				

				cookbookButtons.get(cookbookButtons.size() - 1).setPrefHeight(100);
				cookbookButtons.get(cookbookButtons.size() - 1).setPrefWidth(150);
				
			
				if (collum < 9) {
			
					grid.add(cookbookButtons.get(cookbookButtons.size() - 1), collum, row, 1, 1);
					collum += 1;
				
				} else {
				
					grid.add(cookbookButtons.get(cookbookButtons.size() - 1), collum, row, 1, 1);
					collum = 1;
					row += 1;
				
				}
				
				secondaryStage.close();
				saveProgress();

				
			}
			});
		});
		
		deleteCookbook.setOnAction(e -> {
			
			delete = !delete;
			
		});
		
		primaryStage.show();
		

		saveProgress();

	}
	
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

}
