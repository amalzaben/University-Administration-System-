package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Start {
	BorderPane pane=new BorderPane();
	private Button manage, reports, read, readS,save,manageS;
	ImageView img;

	public Start(DLL majors) {
	
		read = new Button("Read Majors From File");
		manage = new Button("Manage Majors");
		manageS = new Button("Manage Students");
		reports = new Button("View Statistics");
		readS = new Button("Read Students From File");
		save = new Button("Save Updates on File");


		read.setPrefWidth(160);
		read.setPrefHeight(35);
		manage.setPrefWidth(150);
		manage.setPrefHeight(35);
		manageS.setPrefWidth(150);
		manageS.setPrefHeight(35);
		reports.setPrefWidth(150);
		reports.setPrefHeight(35);
		readS.setPrefWidth(160);
		readS.setPrefHeight(35);
		save.setPrefWidth(160);
		save.setPrefHeight(35);
		read.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;");
		manage.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;");
		manageS.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;");
		reports.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;");
		readS.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;");
		save.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;");
	

		Label heading = new Label("Adminstration System");
		heading.setFont(Font.font("Elephant", FontWeight.BOLD, 40));
		heading.setLayoutX(245);
		heading.setLayoutY(70);
		heading.setTextFill(Color.BLACK);


		img = new ImageView("bzu.png");
		img.setScaleX(1);
		img.setScaleY(1);
		img.setX(2);
		img.setY(0);
		img.setFitWidth(1000);
		img.setFitHeight(500);

		HBox hbox = new HBox(10);
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(read ,readS, manage,manageS, reports,save);
		hbox.setLayoutX(28);
		hbox.setLayoutY(490);
		
		VBox vbox=new VBox(10);
		vbox.setSpacing(30);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(img, heading,hbox);

		pane.setStyle("-fx-background-color:white;");
		buttonMovments();

		pane.setCenter(vbox);

	}
	public Pane getPane() {
		return pane;
	}
	private void buttonMovments() {// button moves when entering and exiting it

		read.setOnMouseEntered(e -> read.setTranslateY(read.getTranslateY() - 7));

		read.setOnMouseExited(e -> read.setTranslateY(read.getTranslateY() + 7));

		manage.setOnMouseEntered(e -> manage.setTranslateY(manage.getTranslateY() - 7));

		manage.setOnMouseExited(e -> manage.setTranslateY(manage.getTranslateY() + 7));
		
		manageS.setOnMouseEntered(e -> manageS.setTranslateY(manageS.getTranslateY() - 7));

		manageS.setOnMouseExited(e -> manageS.setTranslateY(manageS.getTranslateY() + 7));
		
		reports.setOnMouseEntered(e -> reports.setTranslateY(reports.getTranslateY() - 7));

		reports.setOnMouseExited(e -> reports.setTranslateY(reports.getTranslateY() + 7));
	
		readS.setOnMouseEntered(e -> readS.setTranslateY(readS.getTranslateY() - 7));

		readS.setOnMouseExited(e -> readS.setTranslateY(readS.getTranslateY() + 7));
		
		save.setOnMouseEntered(e -> save.setTranslateY(save.getTranslateY() - 7));

		save.setOnMouseExited(e -> save.setTranslateY(save.getTranslateY() + 7));

	}
	public Button getManage() {
		return manage;
	}
	public Button getManageStudent() {
		return manageS;
	}
	public Button getReports() {
		return reports;
	}
	public Button getRead() {
		return read;
	}
	public Button getReadS() {
		return readS;
	}
	public Button getSave() {
		return save;
	}
	public DNode getTempNext(DNode temp) { 
		try {
		temp=temp.getNext();
		return temp;
		}catch(NullPointerException e) {
			
		}
		return temp;
	}
	public DNode setTemp(DNode curr,DNode temp) {
		curr=temp;
		return curr;
	}
	public DNode getTempPrev(DNode temp) {
		try {
		temp=temp.getPrev();
		return temp;
		}catch(NullPointerException o) {
			
		}
		return temp;
	}
	

	
	

}
