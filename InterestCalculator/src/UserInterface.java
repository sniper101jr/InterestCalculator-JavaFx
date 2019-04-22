import java.awt.Checkbox;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.stage.Stage;

//imports for this application
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;

//layout
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/*
 * Name: Tochukwu Michael Chizea
 * Student Number: 2981920
 * 
 * 
 * *\
 */


public class UserInterface extends Application {

	//declre componets at class level

	Label lblCapital, lblIntRate, lblIntTerm, lblStartDate, lblEndDate, lblShowStartDate,lblShowEndDate, errorMessage, lblType;

	//buttons
	Button btnQuit, btnCalculate, btnOpenDate;
	

	//date
	LocalDate startDate,endDate;

	//date picker
	DatePicker dpStartDate, dpEndDate, dpIntTerm;

	//textarea
	TextArea txtCalculation;

	//textfields
	TextField txtCapital, txtintRate, txtintTerm;
	
	
	CheckBox cbSI;

	CheckBox cbBoth;

	CheckBox cbCI;
	



	public UserInterface() {
		// TODO Auto-generated constructor stub
	}//constructor


	@Override
	public void init() {

		//labels
		lblCapital = new Label("Capital:");
		lblIntRate = new Label("Investment Rate (%):"); 
		lblIntTerm = new Label("Investment Term (Years):");
		lblType= new Label("Choose Calculation Type: ");
		errorMessage = new Label("");
		errorMessage.setTextFill(Color.web("#D80032"));


		//text 
		txtCalculation = new TextArea("");
				
		//checkboexes
		cbSI = new CheckBox("Simple Interest "); 
		cbCI = new CheckBox("Compund Interest"); 

		//button
		btnQuit =new Button("Quit");
		btnQuit.setOnAction(ae -> Platform.exit());

		btnCalculate = new Button("Calculate");
		btnCalculate.setOnAction(ae -> {

			//view text
			try {
				
				//instantiate the the methods used to calculate the  Simpleinterd and compund interest
			double termToInt = Integer.parseInt(txtintTerm.getText());

			double capitalToInt = Integer.parseInt(txtCapital.getText());

			double	calc = calculateInterest(txtCapital, txtintRate, txtintTerm);	

			double interestCalc = calculateInterestEarned(txtCapital, txtintRate, txtintTerm);
			
			double compoundCalc = calculateCompond(txtCapital, txtintRate, txtintTerm);

			//int interestEarned = txtCapital* txtintRate*txtintTerm;


			//calculateInterest(txtCapital, txtintRate, txtintTerm);
			
			if(cbCI.isSelected() && cbSI.isSelected() ) {

			txtCalculation.setText("Simple Interest:"+ "\n" + "Year: " + txtintTerm.getText() + "    Initial Capital: "+capitalToInt  + "    Interest Earned: " + interestCalc + "    Final Amount: " + calc 
					+ "\nCompound Interest:"+ "\n" + "Year: " + termToInt + "    Initial Capital: "+capitalToInt  +   "   Compund Interest: " + compoundCalc );

			}//if
			else if(cbCI.isSelected()) {
				
				txtCalculation.setText("Compound Interest:"+ "\n" + "Year: " + termToInt + "    Initial Capital: "+capitalToInt  +   "   Compund Interest: " + compoundCalc  );

				
			}//elseif
			else if(cbSI.isSelected()) {
				
				txtCalculation.setText("Simple Interest:"+ "\n" + "Year: " + termToInt + "    Initial Capital: "+capitalToInt  + "    Interest Earned: " + interestCalc + "    Final Amount: " + calc + "\n" 
						
						
						);
				
			}//else if
			
			}//try
			catch(NumberFormatException e){
				errorMessage.setText("Please Enter A Value For Each Field");
				
			}//catch
			
			
			




		});//end of Calculate button


		btnOpenDate = new Button("...");
		btnOpenDate.setOnAction(ae -> openDialog());
		
		//textFields
		txtCapital = new TextField();
		txtintRate =new TextField();
		txtintTerm = new TextField();
		
		
		
		//datePicker
		dpIntTerm = new DatePicker();


	}
	
	
public void openDialog() {
		
		//Create a dialog. Requires a new stage.
				Stage nStage = new Stage();
				
				//Set the width and height.
				nStage.setWidth(350);
				nStage.setHeight(250);
				
				
				//Set up the dialog.
				nStage.setTitle("Select Investment Term:");
				nStage.setResizable(false);

				
				//The dialog requires calendars/DatePickers.
				dpStartDate = new DatePicker();
				dpEndDate = new DatePicker();
				
				//Set the default date in the dps.
				dpStartDate.setValue(LocalDate.now());
				dpEndDate.setValue(LocalDate.now());
				
				//Labels for the dialog.
				Label lblStart = new Label("Investment Start date:");
				Label lblEnd = new Label("Investment End date:");
				Label lblStatus = new Label("");
				
				//The ok and cancel buttons.
				Button btnOK = new Button("OK");
				Button btnCancel = new  Button("Cancel");
				
				//Manage button sizes.
				btnOK.setMinWidth(40);
				btnCancel.setMinWidth(40);
					
				
				//Handle events on the buttons.
				//First the cancel button. Do nothing.
				btnCancel.setOnAction(ae -> nStage.close());
				
				
				//The OK button. The dialog is confirmed.
				//Get the data from the dialog.
				//Then dismiss the dialog.
				btnOK.setOnAction(ae -> {
					//Check the data order but get the start and end dates.
					LocalDate start = dpStartDate.getValue();
					LocalDate end = dpEndDate.getValue();
					
					//Check the order.
					if(end.isAfter(start) ) {
						
						//double start = Integer.parseInt(txtintTerm.getText());
						txtintTerm.setText(start.toString());
						txtintTerm.setText(end.toString());
					
						
						//Get the years represented by what the user input
						//This is the number of days between dates.
						long p = ChronoUnit.YEARS.between(dpStartDate.getValue(), dpEndDate.getValue());
						
						
						//Show the total cost on the main UI.
						//an emty strinis ther because settext needs to have a string in it
						txtintTerm.setText( p + "" );
						
						double s = Integer.parseInt(txtintTerm.getText());
						
						//Close the dialog.
						nStage.close();
						
					}//if
					else {
						
						//Prompt user. Enter valid dates and a cost per day.
						lblStatus.setText("Invalid Dates Entererd, Enter Valid Dates");
						
					}//else
		
					
				}); //Done handling the OK button events.
				
				

				//Create a layout.
				GridPane dgp = new GridPane();
				
				dgp.add(lblStart, 0, 0);
				dgp.add(dpStartDate, 1, 0);
				
				dgp.add(lblEnd, 0, 1);
				dgp.add(dpEndDate, 1, 1);
				
				//Container for the buttons.
				HBox hbButtons = new HBox();
				hbButtons.getChildren().addAll(btnCancel,btnOK);
				hbButtons.setAlignment(Pos.BASELINE_RIGHT);
				hbButtons.setPadding(new Insets(20));
				hbButtons.setSpacing(10);
				dgp.add(hbButtons, 1, 3);
				
				//The status label.
				dgp.add(lblStatus, 0, 3);
		
				
				//Beautify
				dgp.setPadding(new Insets(10));
				dgp.setHgap(20);
				dgp.setVgap(10);
						
				//Create a scene.
				Scene ds = new Scene(dgp);
				
				
				
				//Set the scene.
				nStage.setScene(ds);
				
				//Show the stage.
				nStage.show();		
		
		
	}//end openDialog

	
	

	@Override
	public void start(Stage pStage) throws Exception {
		// TODO Auto-generated method stub


		//set the width and height  
		pStage.setWidth(650);
		pStage.setHeight(450);
		pStage.setResizable(false);
		//so the the size cant be changed, this is useful so that the layout staye the way it was designed by the developer and it will remain the same everywher the program is used

		//set the title
		pStage.setTitle("Interest Calculator");


		//create a layout
		VBox vbMain = new VBox();
		GridPane gp = new GridPane();
		GridPane gp2 = new GridPane();
		
		HBox hbButtons = new HBox();
		HBox hb = new HBox();
		HBox hbError = new HBox();
		
		HBox hbRadio = new HBox();
		
		//setting the leght of the ltextfield and the button so it fits the leght of the other textbox
		txtintTerm.setPrefWidth(290);
		
		
		
		//add tehe componnets to the layout
		hbRadio.getChildren().addAll(cbSI,cbCI);
		hbButtons.getChildren().addAll(btnCalculate,btnQuit);
		hb.getChildren().addAll(txtintTerm,btnOpenDate);
		hbError.getChildren().add(errorMessage);
		
		//used for the styling of th buttons
		//the button1 is a calss that was created in the css file
		btnCalculate.getStyleClass().add("button1");
		btnQuit.getStyleClass().add("button1");
		//errorMessage.getStyleClass().add("error");
		
		



		//add componets to the gp
		gp.add(lblType, 9, 0);
		gp.add(hbRadio, 10, 0);
		
		gp.add(lblCapital, 9, 1);
		gp.add(txtCapital, 10, 1);

		gp.add(lblIntRate, 9, 2);
		gp.add(txtintRate, 10, 2);

		gp.add(lblIntTerm, 9, 3);
		gp.add(hb, 10, 3); 
	

		//dpIntTerm


		//ad the gridpane
		vbMain.getChildren().add(gp);

		//now add the text area under the gp
		vbMain.getChildren().add(txtCalculation);
		txtCalculation.setPrefWidth(30);
		
		
	vbMain.getChildren().add(errorMessage);
		//create a scene

		//	finallay add the hbox contailnig the butttons
		vbMain.getChildren().addAll(hbButtons);
		
		//gp.add(errorMessage, columnIndex, rowIndex);
		
	//	vbMain.getChildren().addAll(lblShowStartDate,lblEndDate);


		vbMain.setPadding(new Insets(10));
		//gp.setPadding(new Insets(100));
		gp.setHgap(15);
		gp.setVgap(15);
		vbMain.setSpacing(10);
		hbButtons.setAlignment(Pos.BASELINE_RIGHT);
		hbButtons.setSpacing(10);
		hb.setSpacing(10);
	

		//txtCapital.setColumn(100);

		//size of buttons
		
		btnQuit.setMinSize(100,30);
		btnCalculate.setMinSize(100,30);
		
		





		//create scene
		Scene s = new Scene(vbMain);

		//set the scene
		//this was used to refer to the file which the syeling statements are
		s.getStylesheets().add("./Style.css");


		//set the scene
		pStage.setScene(s);

		//show the stage
		pStage.show();


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch();

	}

	public static double calculateInterest(TextField c, TextField rate, TextField term) {

		//this is conversting the string valu that it gets form the user and then it is parse to a double which can be used to calcaluuate the SI


		int capital = Integer.parseInt(c.getText());
		int rate1 = Integer.parseInt(rate.getText());
		int term1 = Integer.parseInt(term.getText());

		
		double si;
		si = (capital * rate1 * term1) / 100 + capital;
		
		return si;

	}//end Interest Rate

	public static double calculateInterestEarned(TextField c, TextField rate, TextField term) {

		//this is conversting the string valu that it gets form the user and then it is parse to a double which can be used to calcaluuate the Interst eanrd
		//interst is basically Simple interest without capitial

		int capital = Integer.parseInt(c.getText());
		double rate1 = Double.parseDouble(rate.getText());
		int term1 = Integer.parseInt(term.getText());

		
		double interest;
		interest = (capital * rate1 * term1) / 100 ;
		

		return interest;

	}//endcal Interest Earned
	
	public static double calculateCompond(TextField c, TextField rate, TextField term) {
//		
		//double amount=0,principle,time,ci,t=1;		
	double time=0, t=1, amount, ci;
	
	
	//this is conversting the string valu that it gets form the user and then it is parse to a double which can be used to calcaluuate the compound
		double capital = Integer.parseInt(c.getText());
		double rate1 = Double.parseDouble(rate.getText());
		double term1 = Double.parseDouble(term.getText());
	
			
		rate1=(1+rate1/100);
	        
	        for(int i=0;i<term1;i++)
	          t*=rate1;
		 
	 
		amount=capital*t;
	 
		ci= (amount-capital);
	 	
		return ci;
		
	}// end calculate compound 
	
	
}
