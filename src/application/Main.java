package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	static DLL majors=new DLL();
	//SLL allStudents=new SLL();
	
	Start start = new Start(majors);
	DNode temp;
	Student tempS;
	BorderPane root = new BorderPane();
	FileChooser fileChooser;

	
	ObservableList<Major> majorInfoList;
	ObservableList<Major> majorsList=FXCollections.observableArrayList();
	ObservableList<Student> studentsList=FXCollections.observableArrayList();
	ObservableList<Student> NotAcceptedstudentsList=FXCollections.observableArrayList();
	ObservableList<Student> studentsInTempMajorList=FXCollections.observableArrayList();
	ObservableList<Student> tempList=FXCollections.observableArrayList();
	
	TableView<Major> majorInfo;
	TextField majorNameField;
	TextField idField;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root.setCenter(start.getPane());
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			start.getManage().setOnAction(e->{
				root.setCenter(manageMagor());
			});
			start.getRead().setOnAction(e->{
				root.setCenter(readMajorFile());
			});
			start.getReadS().setOnAction(e->{
				root.setCenter(readStudentFile());
			});
			start.getReports().setOnAction(e->{
				root.setCenter(statistics());
			});
			start.getSave().setOnAction(e->{
				saveUpdatesOnFileStage();
			});
			start.getManageStudent().setOnAction(e->{
				root.setCenter(manageStudents());
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	////////////////////Read Major File ///////////////////////////
	private VBox readMajorFile() {
		
		ImageView bzu = new ImageView("bzu.png");
		bzu.setScaleX(1);
		bzu.setScaleY(1);
		bzu.setX(2);
		bzu.setY(0);
		bzu.setFitWidth(300);
		bzu.setFitHeight(100);
		
		//============= TableView ===================
		TableView<Major> MajorsTable = new TableView<Major>();
		//name column
		TableColumn<Major, String> nameColumn = new TableColumn<>("Major Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setMinWidth(250); 
		nameColumn.setStyle("-fx-alignment: CENTER;");
		//AG column
		TableColumn<Major, Integer> AGColumn = new TableColumn<>("ACCEPTENCE GRADE");
		AGColumn.setCellValueFactory(new PropertyValueFactory<>("acc_grade"));
		AGColumn.setMinWidth(250); 
		AGColumn.setStyle("-fx-alignment: CENTER;");
		// TW column
		TableColumn<Major, Double> TWColumn = new TableColumn<>("TAWJIHI WEIGHT");
		TWColumn.setCellValueFactory(new PropertyValueFactory<>("taw_grade"));
		TWColumn.setMinWidth(250); 
     	TWColumn.setStyle("-fx-alignment: CENTER;");
		// PTW column
		TableColumn<Major, Double> PTWColumn = new TableColumn<>("PLACEMENT TEST WEIGHT");
		PTWColumn.setCellValueFactory(new PropertyValueFactory<>("ptest_grade"));
		PTWColumn.setMinWidth(250);  
		PTWColumn.setStyle("-fx-alignment: CENTER;");
		// add columns into table
		MajorsTable.setMaxWidth(1000);
		MajorsTable.setMaxHeight(900);
		MajorsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
		MajorsTable.getColumns().addAll(nameColumn,AGColumn,TWColumn,PTWColumn);
		MajorsTable.setItems(majorsList);
		
		
		//================ buttons===================
		Button choose = new Button("Choose File");
		Button back = new Button("Back");
		choose.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
		choose.setPrefWidth(170);
		choose.setPrefHeight(50);
	    back.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
		back.setPrefWidth(170);
		back.setPrefHeight(50);
		back.setOnMouseEntered(e -> back.setTranslateY(back.getTranslateY() - 7));
		back.setOnMouseExited(e -> back.setTranslateY(back.getTranslateY() + 7));
		choose.setOnMouseEntered(e -> choose.setTranslateY(choose.getTranslateY() - 7));
		choose.setOnMouseExited(e -> choose.setTranslateY(choose.getTranslateY() + 7));
		
		//============ setOnAction =============
		back.setOnAction(e->{
			root.setCenter(start.getPane());
		});
		
		choose.setOnAction(e->{
			
		fileChooser=new FileChooser();
		//fileChooser.setInitialDirectory(new File("C:\\Users\\HP\\Desktop"));
		File file =fileChooser.showOpenDialog(new Stage());
		try {
			Scanner scanner=new Scanner(file);
			while(scanner.hasNext()){
				String line=scanner.nextLine();
				String [] atributes =line.split("\\|");
				if(atributes.length==4) {
					try {
					majors.sortedAdd(new Major(atributes[3],Integer.parseInt(atributes[2]),Double.parseDouble(atributes[0]),Double.parseDouble(atributes[1])));
					}catch(IllegalArgumentException i) {
					}
				}
				else
					throw new  IllegalArgumentException();
			}
			scanner.close();
			if(majors.getCount()!=1) {
			majors.remove(majors.search("Major's Name").getElement());
			}
		
		}
		catch(FileNotFoundException m) {
			getAlert("The System Is Not Able To Read File !!");
		}catch(NullPointerException h) {
			getAlert("No File Was Choosen !!");
		}catch(IllegalArgumentException o) {
			getAlert("File has Wrong Data Format");
		}
		updateMajorsList();
		
		});
		
		VBox mvbox=new VBox(30);
		mvbox.setAlignment(Pos.TOP_CENTER);
		mvbox.setPadding(new Insets(40));
		mvbox.setStyle("-fx-background-color:white;");
		mvbox.getChildren().addAll(bzu,choose,MajorsTable,back);
		return mvbox;
	}
	
	//////////////// Read Students file////////////////////////////////
	
	private VBox readStudentFile() {
			
			ImageView bzu = new ImageView("bzu.png");
			bzu.setScaleX(1);
			bzu.setScaleY(1);
			bzu.setX(2);
			bzu.setY(0);
			bzu.setFitWidth(300);
			bzu.setFitHeight(100);
			
			//================== tableView========================
			TableView<Student> StudentsTable = new TableView<Student>();
			//name column
			TableColumn<Student, String> nameColumn = new TableColumn<>("STUDENT NAME");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			nameColumn.setMinWidth(250); 
			nameColumn.setStyle("-fx-alignment: CENTER;");
			// accepted column
			TableColumn<Student, Boolean> acceptedColumn = new TableColumn<>("ACCEPTED");
			acceptedColumn.setCellValueFactory(new PropertyValueFactory<>("accepted"));
			acceptedColumn.setMinWidth(100); 
			acceptedColumn.setStyle("-fx-alignment: CENTER;");
			//AG column
			TableColumn<Student, Double> TGColumn = new TableColumn<>("TAWJIHI GRADE");
			TGColumn.setCellValueFactory(new PropertyValueFactory<>("TG"));
			TGColumn.setMinWidth(200); 
			TGColumn.setStyle("-fx-alignment: CENTER;");
			// TW column
			TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			idColumn.setMinWidth(100); 
			idColumn.setStyle("-fx-alignment: CENTER;");
			// PTW column
			TableColumn<Student, Double> PTGColumn = new TableColumn<>("PLACEMENT TEST GRADE");
			PTGColumn.setCellValueFactory(new PropertyValueFactory<>("PTG"));
			PTGColumn.setMinWidth(200);  
			PTGColumn.setStyle("-fx-alignment: CENTER;");
			//name column
			TableColumn<Student, String> choosenMajorColumn = new TableColumn<>("CHOOSEN MAJOR");
			choosenMajorColumn.setCellValueFactory(new PropertyValueFactory<>("choosenMajor"));
			choosenMajorColumn.setMinWidth(250); 
			choosenMajorColumn.setStyle("-fx-alignment: CENTER;");
			// add columns into table
			StudentsTable.setMaxWidth(1100);
			StudentsTable.setMaxHeight(900);
			StudentsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
			StudentsTable.getColumns().addAll(idColumn,nameColumn,acceptedColumn,TGColumn,PTGColumn,choosenMajorColumn);
	        StudentsTable.setItems(studentsList);
			
			//================ buttons===================
			Button choose = new Button("Choose File");
			Button back = new Button("Back");
			choose.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
			choose.setPrefWidth(170);
			choose.setPrefHeight(50);
		    back.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
			back.setPrefWidth(170);
			back.setPrefHeight(50);
			back.setOnMouseEntered(e -> back.setTranslateY(back.getTranslateY() - 7));
			back.setOnMouseExited(e -> back.setTranslateY(back.getTranslateY() + 7));
			choose.setOnMouseEntered(e -> choose.setTranslateY(choose.getTranslateY() - 7));
			choose.setOnMouseExited(e -> choose.setTranslateY(choose.getTranslateY() + 7));
			
			//============ setOnAction =============
			back.setOnAction(e->{
				root.setCenter(start.getPane());
			});
			
			choose.setOnAction(e->{
				if(majors.getCount()!=1) {
					fileChooser=new FileChooser();
					File file =fileChooser.showOpenDialog(new Stage());
					try {
						Scanner scanner=new Scanner(file);
						while(scanner.hasNext()){
							String line=scanner.nextLine();
							String [] atributes =line.split("\\|");
							if(atributes.length==4) {
								try {
					               addStudent( new Student(atributes[0],Double.parseDouble(atributes[1]),Double.parseDouble(atributes[2]),atributes[3]));
								}catch(IllegalArgumentException i) {
								}catch(NullPointerException u) {
								}
							}
							else
								throw new  IllegalArgumentException("wrong data formate !!");
						}
						scanner.close();
					}
					catch(FileNotFoundException m) {
						getAlert("The System Is Not Able To Read File !!");
					}catch(NullPointerException w) {
						getAlert("No File Was Choosen !!");
					}catch(IllegalArgumentException o) {
						getAlert(o.getMessage());
					}
					
				}
				else
					getAlert("NO MAJORS IN SYSTEM , PLEASE INSERT MAJORS THEN  READ STUDENTS FILE!!");
			});
			
			
			VBox mvbox=new VBox(30);
			mvbox.setAlignment(Pos.TOP_CENTER);
			mvbox.setPadding(new Insets(40));
			mvbox.setStyle("-fx-background-color:white;");
			mvbox.getChildren().addAll(bzu,choose,StudentsTable,back);
			return mvbox;
	}

	///////////// Statistics//////////////////////////////////////////
	
	private VBox statistics() {
		
		ImageView bzu = new ImageView("bzu.png");
		bzu.setScaleX(1);
		bzu.setScaleY(1);
		bzu.setX(2);
		bzu.setY(0);
		bzu.setFitWidth(300);
		bzu.setFitHeight(100);
		
		//============= buttons ===============================
		
		Button back = new Button("Back");
		back.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
		back.setPrefWidth(170);
		back.setPrefHeight(50);
		back.setOnMouseEntered(e -> back.setTranslateY(back.getTranslateY() - 7));
		back.setOnMouseExited(e -> back.setTranslateY(back.getTranslateY() + 7));
		
		// ================ table view ============================
		
		TableView<Major> MajorsStatTable = new TableView<Major>();
		//name column
		TableColumn<Major, String> nameColumn = new TableColumn<>("MAJOR NAME");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setMinWidth(250); 
		nameColumn.setStyle("-fx-alignment: CENTER;");
		//AG column
		TableColumn<Major, Double> ASColumn = new TableColumn<>("ACCEPTED STUDENTS");
		ASColumn.setCellValueFactory(new PropertyValueFactory<>("acceptedStudentsCount"));
		ASColumn.setMinWidth(250); 
		ASColumn.setStyle("-fx-alignment: CENTER;");
		// TW column
		TableColumn<Major, Double> NASColumn = new TableColumn<>("REJECTED STUDENTS");
		NASColumn.setCellValueFactory(new PropertyValueFactory<>("notAcceptedStudentsCount"));
		NASColumn.setMinWidth(250); 
		NASColumn.setStyle("-fx-alignment: CENTER;");
        // TW column
 		TableColumn<Major, Double> ARColumn = new TableColumn<>("ACCEPTENCE RATE");
 		ARColumn.setCellValueFactory(new PropertyValueFactory<>("acceptenceRate"));
 		ARColumn.setMinWidth(250); 
 		ARColumn.setStyle("-fx-alignment: CENTER;");
 		MajorsStatTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
 		MajorsStatTable.setMaxWidth(1000);
 		MajorsStatTable.setMaxHeight(900);
 		MajorsStatTable.getColumns().addAll(nameColumn,ASColumn,NASColumn,ARColumn);
 		MajorsStatTable.setItems(majorsList);
 		MajorsStatTable.refresh();
 		
 		TableView<Statistics> StatTable = new TableView<Statistics>();
 		TableColumn<Statistics, Double> ASColumn2= new TableColumn<>("TOTAL ACCEPTEd STUDENTS");
 		ASColumn2.setCellValueFactory(new PropertyValueFactory<>("TotalAccepted"));
 		ASColumn2.setMinWidth(240); 
 		ASColumn2.setStyle("-fx-alignment: CENTER;");
		// rejected column
		TableColumn<Statistics, Double> NASColumn2 = new TableColumn<>("TOTAL REJECTED STUDENTS");
		NASColumn2.setCellValueFactory(new PropertyValueFactory<>("TotalNotAccepted"));
		NASColumn2.setMinWidth(240); 
		NASColumn2.setStyle("-fx-alignment: CENTER;");
        // rate column
 		TableColumn<Statistics, Double> ARColumn2 = new TableColumn<>("ACCEPTENCE RATE");
 		ARColumn2.setCellValueFactory(new PropertyValueFactory<>("AcceptenceRate"));
 		ARColumn2.setMinWidth(240); 
 		ARColumn2.setStyle("-fx-alignment: CENTER;");
 	   // total column
 		TableColumn<Statistics, Double> total = new TableColumn<>("TOTAL STUDENTS");
 		total.setCellValueFactory(new PropertyValueFactory<>("total"));
 		total.setMinWidth(240); 
 		total.setStyle("-fx-alignment: CENTER;");
 		StatTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
 		StatTable.setMaxWidth(1000);
 		StatTable.setMaxHeight(50);
 		StatTable.getColumns().addAll(total,ASColumn2,NASColumn2,ARColumn2);
 		ObservableList<Statistics> statList= FXCollections.observableArrayList();
 		Statistics statistics=new Statistics(majors);
 		statList.add(statistics);
 		StatTable.setItems(statList);
 		StatTable.refresh();

     	
		//============== setOnAction============================
		back.setOnAction(e->{
			root.setCenter(start.getPane());
		});
		
		VBox mvbox=new VBox(30);
		mvbox.setAlignment(Pos.TOP_CENTER);
		mvbox.setPadding(new Insets(40));
		mvbox.setStyle("-fx-background-color:white;");
		mvbox.getChildren().addAll(bzu,MajorsStatTable,StatTable,back);
		return mvbox;
	
	}
	
	/////////////// Manage Majors////////////////////////////////////////
	
	private VBox manageMagor() {
	
	    temp=majors.getFirst();
		
	    majorInfo=new TableView<Major>();
		majorInfoList=FXCollections.observableArrayList(); 
		Label header=new Label("Majors");
		ImageView img = new ImageView("arrow.png");
	    img.setRotate(180);
	    Button delete = new Button("Delete");
	    Button update = new Button("Update");
	    Button search = new Button("Search");
	    Button back = new Button("Back");
	    Button view = new Button("View Majors Table");
	    Button next = new Button("", new ImageView("arrow.png"));
	    Button prev = new Button("", img);
	    Button manageStudent = new Button("Show Students");
	    Button addNew = new Button("Add New");
	    majorNameField=new TextField();
	    TextField searchField=new TextField();
	    searchField.setStyle("-fx-border-color: rgb(0, 100, 38); -fx-border-width: 2;-fx-pref-width: 250px;-fx-pref-height: 40px; -fx-font-size: 20px;");
	    majorNameField.setStyle("-fx-border-color: rgb(0, 100, 38); -fx-border-width: 2;-fx-pref-width: 400px;-fx-pref-height: 60px; -fx-font-size: 25px;");
	   // searchField.setStyle("-fx-pref-width: 250px;-fx-pref-height: 40px; -fx-font-size: 20px;");
		majorInfoList.add((Major)temp.getElement());

	    
	    //================== Styling==================================
	    
	    header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		header.setFont(Font.font("Times New Roman", 100));
		
		//majorNameField.setStyle("-fx-pref-width: 400px;-fx-pref-height: 60px; -fx-font-size: 25px;");
		majorNameField.setAlignment(Pos.CENTER);
		
		HBox hbox1=new HBox(30);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.getChildren().addAll(prev,majorNameField,next);
		
		//AG column
		TableColumn<Major, Integer> AGColumn = new TableColumn<>("ACCEPTENCE GRADE");
		AGColumn.setCellValueFactory(new PropertyValueFactory<>("acc_grade"));
		AGColumn.setMinWidth(250); 
		AGColumn.setStyle("-fx-alignment: CENTER;");
		// TW column
		TableColumn<Major, Double> TWColumn = new TableColumn<>("TAWJIHI WEIGHT");
		TWColumn.setCellValueFactory(new PropertyValueFactory<>("taw_grade"));
		TWColumn.setMinWidth(250); 
     	TWColumn.setStyle("-fx-alignment: CENTER;");
		// PTW column
		TableColumn<Major, Double> PTWColumn = new TableColumn<>("PLACEMENT TEST WEIGHT");
		PTWColumn.setCellValueFactory(new PropertyValueFactory<>("ptest_grade"));
		PTWColumn.setMinWidth(250);  
		PTWColumn.setStyle("-fx-alignment: CENTER;");
		// add columns into table
		majorInfo.setMaxWidth(750);
		majorInfo.setMaxHeight(70);
		majorInfo.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
		majorInfo.getColumns().addAll(AGColumn,TWColumn,PTWColumn);
		majorInfo.setItems(majorInfoList);
		
		
		addNew.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
		update.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
		delete.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		view.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		manageStudent.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		addNew.setPrefWidth(170);
		addNew.setPrefHeight(50);
		addNew.setOnMouseEntered(e -> addNew.setTranslateY(addNew.getTranslateY() - 7));
		addNew.setOnMouseExited(e -> addNew.setTranslateY(addNew.getTranslateY() + 7));
		update.setPrefWidth(170);
		update.setPrefHeight(50);
		update.setOnMouseEntered(e -> update.setTranslateY(update.getTranslateY() - 7));
		update.setOnMouseExited(e -> update.setTranslateY(update.getTranslateY() + 7));
		delete.setPrefWidth(170);
		delete.setPrefHeight(50);
		delete.setOnMouseEntered(e -> delete.setTranslateY(delete.getTranslateY() - 7));
		delete.setOnMouseExited(e -> delete.setTranslateY(delete.getTranslateY() + 7));
		view.setPrefWidth(200);
		view.setPrefHeight(50);
		view.setOnMouseEntered(e -> view.setTranslateY(view.getTranslateY() - 7));
		view.setOnMouseExited(e -> view.setTranslateY(view.getTranslateY() + 7));
		manageStudent.setPrefWidth(170);
		manageStudent.setPrefHeight(50);
		manageStudent.setOnMouseEntered(e -> manageStudent.setTranslateY(manageStudent.getTranslateY() - 7));
		manageStudent.setOnMouseExited(e -> manageStudent.setTranslateY(manageStudent.getTranslateY() + 7));
		HBox hbox2=new HBox(20);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(addNew,update,delete,manageStudent,view);
		
		search.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		search.setPrefWidth(170);
		search.setPrefHeight(50);
		search.setOnMouseEntered(e -> search.setTranslateY(search.getTranslateY() - 7));
		search.setOnMouseExited(e -> search.setTranslateY(search.getTranslateY() + 7));
		
		back.setStyle("-fx-background-color: rgb(204, 255, 229); -fx-text-fill: black; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
		back.setPrefWidth(170);
		back.setPrefHeight(50);
		back.setOnMouseEntered(e -> back.setTranslateY(back.getTranslateY() - 7));
		back.setOnMouseExited(e -> back.setTranslateY(back.getTranslateY() + 7));
		
		HBox hbox3=new HBox(20);
		hbox3.setAlignment(Pos.CENTER);
		hbox3.getChildren().addAll(search,searchField);
		
		ImageView bzu = new ImageView("bzu.png");
		bzu.setScaleX(1);
		bzu.setScaleY(1);
		bzu.setX(2);
		bzu.setY(0);
		bzu.setFitWidth(300);
		bzu.setFitHeight(100);
		
		VBox mvbox=new VBox(30);
		mvbox.setAlignment(Pos.TOP_CENTER);
		mvbox.setPadding(new Insets(40));
		mvbox.setStyle("-fx-background-color:white;");
		mvbox.getChildren().addAll(bzu,header,hbox1,majorInfo,hbox2,hbox3,back);
		
		//================= setOnAction=============================
		back.setOnAction(e->{
			root.setCenter(start.getPane());
		});
		
		next.setOnAction(e->{
			try {
			temp=start.getTempNext(temp);
			majorNameField.setText(((Major)temp.getElement()).getName());
			majorInfoList.clear();
			majorInfoList.add((Major)temp.getElement());
			}catch(NullPointerException r) {
				
			}
		});
		prev.setOnAction(e->{
			try {
			temp=start.getTempPrev(temp);
			majorNameField.setText(((Major)temp.getElement()).getName());
			majorInfoList.clear();
			majorInfoList.add((Major)temp.getElement());
			}catch(NullPointerException r) {
				
			}
		});
		
		addNew.setOnAction(e->{
			addMagorStage();
		});
		
		search.setOnAction(e->{
			try {
				if(searchField.getText().isEmpty())
					throw new NullPointerException("PLEASE ENTER MAJOR NAME TO PROCEED !!");
				temp=start.setTemp(temp, majors.search(searchField.getText()));
				majorNameField.setText(((Major)temp.getElement()).getName());
				majorInfoList.clear();
				majorInfoList.add((Major)temp.getElement());
			}catch(NullPointerException w) {
				getAlert(w.getMessage());
			}
		});
		
		view.setOnAction(e->{
			updateMajorsList();
			showMagorStage();
		});
		
		update.setOnAction(e->{
			updateMagorStage();
		});
		
		delete.setOnAction(e->{
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("ERORR");
			alert.setContentText("ARE YOU SURE YOU WANT TO DELETE THIS MAJOR FROM THE SYSTEM ?");
			alert.setHeaderText("WARNING ALERT");
			ButtonType confirm= new ButtonType("confirm");
			alert.getDialogPane().getButtonTypes().clear();
			alert.getDialogPane().getButtonTypes().add(confirm);
			alert.setResultConverter(dialogButton -> {
	            if (dialogButton == confirm) {
			if(temp!=null) { 
				if(majors.getCount()==1) {
					majorNameField.setText("");
					majorInfoList.clear();
					majorInfo.refresh();
				}
				Major todelete=(Major)temp.getElement();
				temp=start.getTempNext(temp);
				majors.remove(todelete);
				majorNameField.setText(((Major)temp.getElement()).getName());
				majorInfoList.clear();
				majorInfoList.add((Major)temp.getElement());
				majorInfo.refresh();
			}
	            }
			return null;
		    });
			alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			alert.showAndWait();
		});
		
		manageStudent.setOnAction(e->{
			updateStudentsInTempMajor();
			showStudentStage();
			
		});
			
		majorNameField.setText(((Major)temp.getElement()).getName());
		return mvbox;
		
	}
	
	/////////////////// Manage Students///////////////////////////////
	
	private VBox manageStudents() {
		
		ImageView bzu = new ImageView("bzu.png");
		bzu.setScaleX(1);
		bzu.setScaleY(1);
		bzu.setX(2);
		bzu.setY(0);
		bzu.setFitWidth(300);
		bzu.setFitHeight(100);
		
		Label header=new Label("Students");
		header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		header.setFont(Font.font("Times New Roman", 100));
		
		//==================== textFields====================
		
		idField=new TextField();
		idField.setStyle("-fx-border-color: rgb(0, 100, 38); -fx-border-width: 2;-fx-pref-width: 70px;-fx-pref-height: 60px; -fx-font-size: 25px;");
		idField.setAlignment(Pos.CENTER);
		idField.setMaxWidth(400);
		TextField searchField=new TextField();
		searchField.setStyle("-fx-border-color: rgb(0, 100, 38); -fx-border-width: 2;-fx-pref-width: 250px;-fx-pref-height: 40px; -fx-font-size: 20px;");
		
		//================= table view =================================
		
		TableView<Student> StudentsTable = new TableView<Student>();
		//name column
		TableColumn<Student, String> nameColumn = new TableColumn<>("STUDENT NAME");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setMinWidth(250); 
		nameColumn.setStyle("-fx-alignment: CENTER;");
		// accepted column
		TableColumn<Student, Boolean> acceptedColumn = new TableColumn<>("ACCEPTED");
		acceptedColumn.setCellValueFactory(new PropertyValueFactory<>("accepted"));
		acceptedColumn.setMinWidth(100); 
		acceptedColumn.setStyle("-fx-alignment: CENTER;");
		//TG column
		TableColumn<Student, Double> TGColumn = new TableColumn<>("TAWJIHI GRADE");
		TGColumn.setCellValueFactory(new PropertyValueFactory<>("TG"));
		TGColumn.setMinWidth(200); 
		TGColumn.setStyle("-fx-alignment: CENTER;");
		// PTW column
		TableColumn<Student, Double> PTGColumn = new TableColumn<>("PLACEMENT TEST GRADE");
		PTGColumn.setCellValueFactory(new PropertyValueFactory<>("PTG"));
		PTGColumn.setMinWidth(200);  
		PTGColumn.setStyle("-fx-alignment: CENTER;");
		//name column
		TableColumn<Student, String> choosenMajorColumn = new TableColumn<>("CHOOSEN MAJOR");
		choosenMajorColumn.setCellValueFactory(new PropertyValueFactory<>("choosenMajor"));
		choosenMajorColumn.setMinWidth(250); 
		choosenMajorColumn.setStyle("-fx-alignment: CENTER;");
		// add columns into table
		StudentsTable.setMaxWidth(1000);
		StudentsTable.setMaxHeight(70);
		StudentsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
		StudentsTable.getColumns().addAll(nameColumn,acceptedColumn,TGColumn,PTGColumn,choosenMajorColumn);
	
	    tempS=new Student("student Name",99,99,"Major's Name");
		tempList.add(tempS);
        StudentsTable.setItems(tempList);
        idField.setText(tempS.getId()+"");
        
		//============= buttons ===============================
		
		Button back = new Button("Back");
		back.setStyle("-fx-background-color: rgb(204, 255, 229); -fx-text-fill: black; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
		back.setPrefWidth(170);
		back.setPrefHeight(50);
		back.setOnMouseEntered(e -> back.setTranslateY(back.getTranslateY() - 7));
		back.setOnMouseExited(e -> back.setTranslateY(back.getTranslateY() + 7));
		
		Button delete = new Button("Delete");
	    Button update = new Button("Update");
	    Button search = new Button("Search");
	    Button addNew = new Button("Add New");
	    Button showStudent = new Button("Show Students");
	    Button rejectedStudent = new Button("Rejected Students");
	    
	    addNew.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
	    search.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
		update.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
		delete.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		showStudent.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		rejectedStudent.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		search.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 15px;");
		search.setPrefWidth(170);
		search.setPrefHeight(50);
		search.setOnMouseEntered(e -> search.setTranslateY(search.getTranslateY() - 7));
		search.setOnMouseExited(e -> search.setTranslateY(search.getTranslateY() + 7));
		addNew.setPrefWidth(170);
		addNew.setPrefHeight(50);
		addNew.setOnMouseEntered(e -> addNew.setTranslateY(addNew.getTranslateY() - 7));
		addNew.setOnMouseExited(e -> addNew.setTranslateY(addNew.getTranslateY() + 7));
		update.setPrefWidth(170);
		update.setPrefHeight(50);
		update.setOnMouseEntered(e -> update.setTranslateY(update.getTranslateY() - 7));
		update.setOnMouseExited(e -> update.setTranslateY(update.getTranslateY() + 7));
		delete.setPrefWidth(170);
		delete.setPrefHeight(50);
		delete.setOnMouseEntered(e -> delete.setTranslateY(delete.getTranslateY() - 7));
		delete.setOnMouseExited(e -> delete.setTranslateY(delete.getTranslateY() + 7));
		showStudent.setPrefWidth(200);
		showStudent.setPrefHeight(50);
		showStudent.setOnMouseEntered(e -> showStudent.setTranslateY(showStudent.getTranslateY() - 7));
		showStudent.setOnMouseExited(e -> showStudent.setTranslateY(showStudent.getTranslateY() + 7));
		rejectedStudent.setPrefWidth(170);
		rejectedStudent.setPrefHeight(50);
		rejectedStudent.setOnMouseEntered(e -> rejectedStudent.setTranslateY(rejectedStudent.getTranslateY() - 7));
		rejectedStudent.setOnMouseExited(e -> rejectedStudent.setTranslateY(rejectedStudent.getTranslateY() + 7));
		
		HBox hbox2=new HBox(20);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(addNew,update,delete,showStudent,rejectedStudent);
		
		HBox hbox3=new HBox(20);
		hbox3.setAlignment(Pos.CENTER);
		hbox3.getChildren().addAll(search,searchField);
		//============== setOnAction============================
		
		back.setOnAction(e->{
			root.setCenter(start.getPane());
		});
		
		update.setOnAction(e->{
			updateStudentStage();
		});
		addNew.setOnAction(e->{
			addNewStudentStage();
		});
		
		delete.setOnAction(e->{
			
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("ERORR");
			alert.setContentText("ARE YOU SURE YOU WANT TO DELETE THIS STUDENT FROM THE SYSTEM ?");
			alert.setHeaderText("WARNING ALERT");
			ButtonType confirm= new ButtonType("confirm");
			alert.getDialogPane().getButtonTypes().clear();
			alert.getDialogPane().getButtonTypes().add(confirm);
			alert.setResultConverter(dialogButton -> {
	          if (dialogButton == confirm) {
	            if(studentsList.size()>1) {
            		if(tempS!=null) { 
						if(tempS.isAccepted()==true) {
							Student s=tempS;
							tempS=studentsList.getFirst();
							tempList.clear();
			                tempList.add(tempS);
			                idField.setText(tempS.getId()+"");
							studentsList.remove(s);
							Major m=(Major)majors.search(s.getChoosenMajor()).getElement();
							m.getStudents().remove(s);
							m.setAcceptedStudentsCount(m.getAcceptedStudentsCount()-1);
						}
						else {
							Student s=tempS;
							tempS=studentsList.getFirst();
							tempList.clear();
			                tempList.add(tempS);
			                idField.setText(tempS.getId()+"");
							studentsList.remove(s);
							NotAcceptedstudentsList.remove(s);
						}
			       }
	            }
	            else
	            	getAlert(" NO STUDENTS IN SYSTEM TO DELETE !!");
	          }
			return null;
		    });
			alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			alert.showAndWait();
		});
		showStudent.setOnAction(e->{
			showallStudentsStage();
		});
		rejectedStudent.setOnAction(e->{
			showRejectedStudentsStage();
		});
		search.setOnAction(e->{
			try {
			if(!searchField.getText().isEmpty()) {
				int found=0;
				int id=Integer.parseInt(searchField.getText());
				for(int i=0;i<studentsList.size();i++) {
					Student s=studentsList.get(i);
					if(s.getId()==id) {
						tempS=s;
		                tempList.clear();
		                tempList.add(tempS);
		                idField.setText(tempS.getId()+"");
		                found=1;
		                break;
					}
				}
				if(found==0) {
					getAlert("STUDENT WAS NOT FOUND !!");
				}
			}
			else
				getAlert("empty field !! please enter student ID then press the button !!");
			}catch(NumberFormatException y) {
				getAlert("ONLY NUMBERS ARE ALLOWED !! PLEASE INTER ID NUMBER THEN PRESS THE BUTTON !!");
			}
		});
		
		VBox mvbox=new VBox(30);
		mvbox.setAlignment(Pos.TOP_CENTER);
		mvbox.setPadding(new Insets(40));
		mvbox.setStyle("-fx-background-color:white;");
		mvbox.getChildren().addAll(bzu,header,idField,StudentsTable,hbox2,hbox3,back);
		return mvbox;
	}
	
	//////////////////////// add major stage///////////////////////////////
	
    private void addMagorStage() {
		
		//=============== Labels ==================
		String style="-fx-font-family: 'Times New Roman'; -fx-font-size: 15; -fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;";
		Label header=new Label("Add New Major");
		Label name = new Label("MAJOR NAME :");
		Label acc_grade = new Label("ACCEPTENCE GRADE :");
		Label taw_grade = new Label("TAWJIHI WEIGHT :");
		Label ptest_grade = new Label("Placement TEST WEIGHT :");
		
		name.setStyle(style);
		acc_grade.setStyle(style);
		taw_grade.setStyle(style);
		ptest_grade.setStyle(style);
		header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		header.setFont(Font.font("Times New Roman", 50));
		//=========== TextFields===================
		TextField namef = new TextField();
		TextField acc_gradef = new TextField();
		TextField taw_gradef = new TextField();
		TextField ptest_gradef = new TextField();
		//=============== Grid pane==========
		GridPane gpane=new GridPane();
		gpane.add(name, 0, 0);
		gpane.add(acc_grade, 0, 1);
		gpane.add(taw_grade, 0, 2);
		gpane.add(ptest_grade, 0, 3);
		gpane.add(namef, 1, 0);
		gpane.add(acc_gradef, 1, 1);
		gpane.add(taw_gradef, 1, 2);
		gpane.add(ptest_gradef, 1, 3);
		gpane.setAlignment(Pos.CENTER);
		gpane.setVgap(30);
		gpane.setHgap(30);
		//============Button==================
		Button button = new Button("ADD NEW MAJOR");
		button.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
		button.setOnAction(e->{
			try {
				if(!namef.getText().isEmpty()||!acc_gradef.getText().isEmpty()||!taw_gradef.getText().isEmpty()||!ptest_gradef.getText().isEmpty()) {
					Major newMajor=new Major(namef.getText(),Integer.parseInt(acc_gradef.getText()),Double.parseDouble(taw_gradef.getText()),Double.parseDouble(ptest_gradef.getText()));
					majors.sortedAdd(newMajor);
					//mlist.add(newMajor);
					namef.setText(null);
					acc_gradef.setText(null);
					taw_gradef.setText(null);
					ptest_gradef.setText(null);
				}
				else
					throw new NullPointerException();
			}catch(NullPointerException n) {
				getAlert("NO EMPTY FIELDS ARE ALLOWED , PLEASE FILL ALL FIELDS THEN PRESS THE BUTTON!");
			}catch(NumberFormatException nf) {
				getAlert("WRONG DATA TYPE , PLEASE INSERT CORRECT DATA THEN PRESS THE BUTTON !");
			}catch(IllegalArgumentException i) {
				getAlert(i.getMessage());
			}
		});
		
		VBox vbox=new VBox(30);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(header,gpane,button);
		BorderPane borderpane=new BorderPane();
		borderpane.setCenter(vbox);
		
		Stage addMajor = new Stage();
		Scene scene=new Scene(borderpane,500,500); 
		addMajor.setTitle("Add New Major");
		addMajor.setScene(scene);
		addMajor.show();
		
	}
    
    ///////////////////// update major stage///////////////////////////
    
	private void updateMagorStage() {
			
			//=============== Labels ==================
			String style="-fx-font-family: 'Times New Roman'; -fx-font-size: 15; -fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;";
			Label header=new Label("Update Selected Major");
			Label name = new Label("MAJOR NAME :");
			Label acc_grade = new Label("ACCEPTENCE GRADE :");
			Label taw_grade = new Label("TAWJIHI WEIGHT :");
			Label ptest_grade = new Label("Placement TEST WEIGHT :");
			Label messege = new Label("");
			
			name.setStyle(style);
			acc_grade.setStyle(style);
			taw_grade.setStyle(style);
			ptest_grade.setStyle(style);
			messege.setStyle(style);
			header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
			header.setFont(Font.font("Times New Roman", 40));
			//=========== TextFields===================
			TextField namef = new TextField();
			namef.setText(((Major)temp.getElement()).getName());
			TextField acc_gradef = new TextField();
			acc_gradef.setText(((Major)temp.getElement()).getAcc_grade()+"");
			TextField taw_gradef = new TextField();
			taw_gradef.setText(((Major)temp.getElement()).getTaw_grade()+"");
			TextField ptest_gradef = new TextField();
			ptest_gradef.setText(((Major)temp.getElement()).getPtest_grade()+"");
			//=============== Grid pane==========
			GridPane gpane=new GridPane();
			gpane.add(name, 0, 0);
			gpane.add(acc_grade, 0, 1);
			gpane.add(taw_grade, 0, 2);
			gpane.add(ptest_grade, 0, 3);
			gpane.add(namef, 1, 0);
			gpane.add(acc_gradef, 1, 1);
			gpane.add(taw_gradef, 1, 2);
			gpane.add(ptest_gradef, 1, 3);
			gpane.setAlignment(Pos.CENTER);
			gpane.setVgap(30);
			gpane.setHgap(30);
			//============Button==================
			Button button = new Button("UPDATE MAJOR");
			button.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
			
			Major selected=(Major)temp.getElement();
			
			button.setOnAction(e->{
				Alert alert=new Alert(Alert.AlertType.WARNING);
				alert.setTitle("ERORR");
				alert.setContentText("ARE YOU SURE YOU WANT TO UPDATE THIS MAJOR'S INFO ?");
				alert.setHeaderText("WARNING ALERT");
				ButtonType confirm= new ButtonType("confirm");
				alert.getDialogPane().getButtonTypes().clear();
				alert.getDialogPane().getButtonTypes().add(confirm);
				alert.setResultConverter(dialogButton -> {
		            if (dialogButton == confirm) {
		            	try {
							
							if(!namef.getText().isEmpty()||!acc_gradef.getText().isEmpty()||!taw_gradef.getText().isEmpty()||!ptest_gradef.getText().isEmpty()) {
								Major test=new Major(namef.getText(),Integer.parseInt(acc_gradef.getText()),Double.parseDouble(ptest_gradef.getText()),Double.parseDouble(taw_gradef.getText()));
								DNode testnode=new DNode(test);
								if(!majors.searchforDuplicates(test)) {
										DNode toUpdate=temp;
										temp=start.getTempNext(temp);
										majors.remove(toUpdate.getElement());
										majors.sortedAdd(test);
										messege.setText("Major Updated");
										majorNameField.setText(((Major)temp.getElement()).getName());
										majorInfoList.clear();
										majorInfoList.add((Major)temp.getElement());
										majorInfo.refresh();
								}
								else if(((Major)temp.getElement()).getName().equals(namef.getText())) {
									selected.setAcc_grade(Integer.parseInt(acc_gradef.getText()));
									selected.setTaw_grade(Double.parseDouble(taw_gradef.getText()));
									selected.setPtest_grade(Double.parseDouble(ptest_gradef.getText()));
									messege.setText("Major Updated");
									majorInfoList.clear();
									majorInfoList.add((Major)temp.getElement());
									majorInfo.refresh();
								}
								else
									throw new IllegalArgumentException("THIS MAJOR NAME CAN NOT BE USED , THERE IS AN EXISTING MAJOR WITH THAT NAME !! ");
							}
							else
								throw new NullPointerException("NO EMPTY FIELDS ARE ALLOWED , PLEASE FILL ALL FIELDS THEN PRESS THE BUTTON!");
						}catch(NullPointerException n) {
							getAlert(n.getMessage());
						}catch(NumberFormatException nf) {
							getAlert("WRONG DATA TYPE , PLEASE INSERT CORRECT DATA THEN PRESS THE BUTTON !");
						}catch(IllegalArgumentException i) {
							getAlert(i.getMessage());
						}
	
		           
		            }
		            return null;
				});
				alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
				alert.showAndWait();
				
			});
			
			VBox vbox=new VBox(30);
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().addAll(header,gpane,messege,button);
			BorderPane borderpane=new BorderPane();
			borderpane.setCenter(vbox);
			
			Stage addMajor = new Stage();
			Scene scene=new Scene(borderpane,500,500); 
			addMajor.setTitle("Add New Major");
			addMajor.setScene(scene);
			addMajor.show();
	}
	
	//////////////////////// show majors stage///////////////////////////
	
	 private void showMagorStage() {
		 
		 //=============== labels ====================
		 Label header=new Label(" Majors");
		 header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		 header.setFont(Font.font("Times New Roman", 40));
		 
		TableView<Major> MajorsTable = new TableView<Major>();
		//name column
		TableColumn<Major, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setMinWidth(160); 
		nameColumn.setStyle("-fx-alignment: CENTER;");
		//AG column
		TableColumn<Major, Integer> AGColumn = new TableColumn<>("ACC_Grade");
		AGColumn.setCellValueFactory(new PropertyValueFactory<>("acc_grade"));
		AGColumn.setMinWidth(80); 
		AGColumn.setStyle("-fx-alignment: CENTER;");
		// TW column
		TableColumn<Major, Double> TWColumn = new TableColumn<>("TW");
		TWColumn.setCellValueFactory(new PropertyValueFactory<>("taw_grade"));
		TWColumn.setMinWidth(80); 
	 	TWColumn.setStyle("-fx-alignment: CENTER;");
		// PTW column
		TableColumn<Major, Double> PTWColumn = new TableColumn<>("PTW");
		PTWColumn.setCellValueFactory(new PropertyValueFactory<>("ptest_grade"));
		PTWColumn.setMinWidth(80);  
		PTWColumn.setStyle("-fx-alignment: CENTER;");
		// add columns into table
		MajorsTable.setMaxWidth(420);
		MajorsTable.setMaxHeight(1000);
		MajorsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
		MajorsTable.getColumns().addAll(nameColumn,AGColumn,TWColumn,PTWColumn);
		MajorsTable.setItems(majorsList);
		
		Stage addMajor = new Stage();
		 
		MajorsTable.setOnMouseClicked(event -> {
	        if (event.getClickCount() == 2) {
	            Major selectedMajor = MajorsTable.getSelectionModel().getSelectedItem();
	            if (selectedMajor != null) {
	                temp=start.setTemp(temp, majors.search(selectedMajor.getName()));
	                majorNameField.setText(((Major)temp.getElement()).getName());
					majorInfoList.clear();
					majorInfoList.add((Major)temp.getElement());
					addMajor.close();
	            }
	        }
	    });
		
		VBox vbox=new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(header,MajorsTable);
		BorderPane borderpane=new BorderPane();
		borderpane.setCenter(vbox);
		
		Scene scene=new Scene(borderpane,500,500); 
		addMajor.setTitle("Add New Major");
		addMajor.setScene(scene);
		addMajor.show();
		 
	 }
	 
	//////////////// show student stage/////////////////////
	 
	 private void showStudentStage() {
		 
		 Label header=new Label("Students");
		 header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		 header.setFont(Font.font("Times New Roman", 40));
		 
		TableView<Student> studentsInTempMajer = new TableView<Student>();
		TableColumn<Student, String> nameColumn = new TableColumn<>("Student Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setMinWidth(150); 
		nameColumn.setStyle("-fx-alignment: CENTER;");
		//AG column
		TableColumn<Student, Double> TGColumn = new TableColumn<>("Tawjihi Grade");
		TGColumn.setCellValueFactory(new PropertyValueFactory<>("TG"));
		TGColumn.setMinWidth(120); 
		TGColumn.setStyle("-fx-alignment: CENTER;");
		//AG column
		TableColumn<Student, Double> AMColumn = new TableColumn<>("AM");
		AMColumn.setCellValueFactory(new PropertyValueFactory<>("addmissionMark"));
		AMColumn.setMinWidth(50); 
		AMColumn.setStyle("-fx-alignment: CENTER;");
		// TW column
		TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setMinWidth(70); 
		idColumn.setStyle("-fx-alignment: CENTER;");
		// PTW column
		TableColumn<Student, Double> PTGColumn = new TableColumn<>("Placement Test Grade");
		PTGColumn.setCellValueFactory(new PropertyValueFactory<>("PTG"));
		PTGColumn.setMinWidth(150);  
		PTGColumn.setStyle("-fx-alignment: CENTER;");
		//name column
		TableColumn<Student, String> choosenMajorColumn = new TableColumn<>("Choosen Major");
		choosenMajorColumn.setCellValueFactory(new PropertyValueFactory<>("choosenMajor"));
		choosenMajorColumn.setMinWidth(150); 
		choosenMajorColumn.setStyle("-fx-alignment: CENTER;");
		// add columns into table
		studentsInTempMajer.setMaxWidth(750);
		studentsInTempMajer.setMaxHeight(550);
		studentsInTempMajer.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
		studentsInTempMajer.getColumns().addAll(idColumn,nameColumn,AMColumn,TGColumn,PTGColumn,choosenMajorColumn);
		studentsInTempMajer.setItems(studentsInTempMajorList);
		studentsInTempMajer.refresh();
		
		Button topN =new Button("TOP N ");
		topN.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
		TextField topNF = new TextField();
		topNF.setStyle("-fx-border-color: rgb(0, 100, 38); -fx-border-width: 2;-fx-pref-width: 250px;-fx-pref-height: 40px; -fx-font-size: 20px;");
		topN.setOnAction(e->{
			if(!topNF.getText().isEmpty()) {
				try {
				int n=Integer.parseInt(topNF.getText());
				if(n>0 ) {
					if( n<((Major)temp.getElement()).getStudents().getCount()){
						SNode sTemp=((Major)temp.getElement()).getStudents().getFirst();
						studentsInTempMajorList.clear();
						if(sTemp!=null) {
							studentsInTempMajorList.clear();
							for(int i=0;i<n;i++) {
								studentsInTempMajorList.add((Student)sTemp.getElement());	
								sTemp=sTemp.getNext();
							}
							studentsInTempMajer.refresh();
						}
		            }
				else {
					getAlert("THE NUMBER OF STUDENTS YOU ARE TRYING TO VIEW IS NOT VALID !! PLEASE ENTER A SMALLER NUMBER :)");
					updateStudentsInTempMajor();
					studentsInTempMajer.refresh();
				}
			   }
				else
					getAlert("NEGATIVE NUMBERS ARE NOT ALLOWED !! PLEASE ENTER POSITIVE NUMBER THEN PRESS THE BUTTON!");
			}catch(NumberFormatException y) {
				getAlert("PLEASE ENTER A NUMBER THEN PRESS THE BUTTON !! ANYTHING BUT NUMBERS ARE NOT ALLOWED !!");
			}
			}
			else {
				getAlert("PLEASE ENTER A NUMBER BEFORE PRESSING THE BUTTON !!");
			}
			
		});
		
		
		HBox hbox=new HBox(20);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(topN,topNF);
		 
		 VBox vbox=new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(header,studentsInTempMajer,hbox);
		BorderPane borderpane=new BorderPane();
		borderpane.setCenter(vbox);
		
		Stage addMajor = new Stage();
		Scene scene=new Scene(borderpane,800,600); 
		addMajor.setTitle("Add New Major");
		addMajor.setScene(scene);
		addMajor.show();
		 
	 }
	 
	 ///////////// save updates on file Stage///////////////
	 
	 private void saveUpdatesOnFileStage() {
			
			ImageView bzu = new ImageView("bzu.png");
			bzu.setScaleX(1);
			bzu.setScaleY(1);
			bzu.setX(2);
			bzu.setY(0);
			bzu.setFitWidth(200);
			bzu.setFitHeight(70);
			
			//=============== buttons=======================
			
			
			Button saveStudents = new Button("SAVE STUDENTS");
			saveStudents.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
			saveStudents.setPrefWidth(250);
			saveStudents.setPrefHeight(50);
			saveStudents.setOnMouseEntered(e -> saveStudents.setTranslateY(saveStudents.getTranslateY() - 7));
			saveStudents.setOnMouseExited(e -> saveStudents.setTranslateY(saveStudents.getTranslateY() + 7));
			
			Button saveMajors = new Button("SAVE MAJORS");
			saveMajors.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
			saveMajors.setPrefWidth(250);
			saveMajors.setPrefHeight(50);
			saveMajors.setOnMouseEntered(e -> saveMajors.setTranslateY(saveMajors.getTranslateY() - 7));
			saveMajors.setOnMouseExited(e -> saveMajors.setTranslateY(saveMajors.getTranslateY() + 7));
			
			Button saveStatistics = new Button("SAVE STATISTICS");
			saveStatistics.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold; -fx-font-size: 20px;");
			saveStatistics.setPrefWidth(250);
			saveStatistics.setPrefHeight(50);
			saveStatistics.setOnMouseEntered(e -> saveStatistics.setTranslateY(saveStatistics.getTranslateY() - 7));
			saveStatistics.setOnMouseExited(e -> saveStatistics.setTranslateY(saveStatistics.getTranslateY() + 7));
			
			//============ setOnAction =============
			Stage stage = new Stage();
			
			saveStudents.setOnAction(e->{
				try {
					fileChooser= new FileChooser();
				    File file =fileChooser.showOpenDialog(new Stage());
				    PrintWriter output = new PrintWriter(file);
				    output.print("");//clear file content
				    for(int i=0;i<studentsList.size();i++) {
				    	Student s=studentsList.get(i);
				    	output.println(s.getId()+""+"|"+s.getName()+"|"+s.getTG()+""+"|"+s.getPTG()+""+"|"+s.getChoosenMajor());
				    }
				    output.close();
				    getAlert("STUDENT INFO WAS SAVED SUCCESSFULY :)");	
				}catch(NullPointerException i) {
					getAlert("NO FILE WAS CHOOSEN , INFORMATION WAS NOT SAVED !!");
				}catch(FileNotFoundException u) {
					getAlert("THE FILE YOU CHOOSED IS NOT VALID OR NOT FOUND !!");
				}
			});
			
			saveMajors.setOnAction(e->{
				try {
					fileChooser= new FileChooser();
				    File file =fileChooser.showOpenDialog(new Stage());
				    PrintWriter output = new PrintWriter(file);
				    output.print("");//clear file content
				   DNode mtemp=majors.getFirst(); 
				   for(int i=0;i<majors.getCount();i++) {
				    	Major m=(Major)mtemp.getElement();
				    	output.println(m.getName()+"|"+m.getAcc_grade()+""+"|"+m.getTaw_grade()+""+"|"+m.getPtest_grade()+"");
				    	mtemp=mtemp.getNext();
				    }
				   output.close();
				   getAlert("MAJOR INFO WAS SAVED SUCCESSFULLY :)");
				    
				}catch(NullPointerException i) {
					getAlert("NO FILE WAS CHOOSEN , INFORMATION WAS NOT SAVED !!");
				}catch(FileNotFoundException u) {
					getAlert("THE FILE YOU CHOOSED IS NOT VALID OR NOT FOUND !!");
				}
			});
			
			saveStatistics.setOnAction(e->{
				try {
					fileChooser= new FileChooser();
				    File file =fileChooser.showOpenDialog(new Stage());
				    PrintWriter output = new PrintWriter(file);
				    output.print("");// clear file
				    output.println("GENERAL STATISTICS :");
				    output.println("=========================================================");
				    Statistics stat=new Statistics(majors);
				    output.println("Total Number Of Students : "+ stat.getTotal()+"");
				    output.println("Total Acepted Students : "+ stat.getTotalAccepted()+"");
				    output.println("Total Rejected Students : "+ stat.getTotalNotAccepted()+"");
				    output.println("Acceptence Rate : "+ stat.getAcceptenceRate()+"");
				    output.println("===============================================================");
				    DNode mtemp=majors.getFirst(); 
				   for(int i=0;i<majors.getCount();i++) {
				    	Major m=(Major)mtemp.getElement();
				    	output.println(m.getName());
				    	output.println("Accepted Students: "+m.getAcceptedStudentsCount()+"");
				    	output.println("Rejected Students: "+m.getNotAcceptedStudentsCount()+"");
				    	output.println("Acceptance Rate: "+m.getAcceptenceRate()+"");
				    	output.println("------------------------------------------------------");
				    	mtemp=mtemp.getNext();
				    }
				   output.close();
				   getAlert("STATISTICS INFO WAS SAVED SUCCESSFULLY :)");
				}catch(NullPointerException i) {
					getAlert("NO FILE WAS CHOOSEN , INFORMATION WAS NOT SAVED !!");
				}catch(FileNotFoundException u) {
					getAlert("THE FILE YOU CHOOSED IS NOT VALID OR NOT FOUND !!");
				}
			});
			
			VBox mvbox=new VBox(30);
			mvbox.setAlignment(Pos.TOP_CENTER);
			mvbox.setPadding(new Insets(40));
			mvbox.setStyle("-fx-background-color:white;");
			mvbox.getChildren().addAll(bzu,saveMajors,saveStudents,saveStatistics);
			BorderPane borderpane=new BorderPane();
			borderpane.setCenter(mvbox);
			
			Scene scene=new Scene(borderpane,400,400); 
			stage.setTitle("Add New Major");
			stage.setScene(scene);
			stage.show();
			
	 }
	 
	 ///////////// show all students stage/////////////
	 
	 private void showallStudentsStage() {
		 
		 Label header=new Label(" Students");
		 header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		 header.setFont(Font.font("Times New Roman", 40));
		 
		//================== tableView========================
			TableView<Student> StudentsTable = new TableView<Student>();
			//name column
			TableColumn<Student, String> nameColumn = new TableColumn<>("STUDENT NAME");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			nameColumn.setMinWidth(250); 
			nameColumn.setStyle("-fx-alignment: CENTER;");
			// accepted column
			TableColumn<Student, Boolean> acceptedColumn = new TableColumn<>("ACCEPTED");
			acceptedColumn.setCellValueFactory(new PropertyValueFactory<>("accepted"));
			acceptedColumn.setMinWidth(100); 
			acceptedColumn.setStyle("-fx-alignment: CENTER;");
			//AG column
			TableColumn<Student, Double> TGColumn = new TableColumn<>("TAWJIHI GRADE");
			TGColumn.setCellValueFactory(new PropertyValueFactory<>("TG"));
			TGColumn.setMinWidth(200); 
			TGColumn.setStyle("-fx-alignment: CENTER;");
			// TW column
			TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			idColumn.setMinWidth(100); 
			idColumn.setStyle("-fx-alignment: CENTER;");
			// PTW column
			TableColumn<Student, Double> PTGColumn = new TableColumn<>("PLACEMENT TEST GRADE");
			PTGColumn.setCellValueFactory(new PropertyValueFactory<>("PTG"));
			PTGColumn.setMinWidth(200);  
			PTGColumn.setStyle("-fx-alignment: CENTER;");
			//name column
			TableColumn<Student, String> choosenMajorColumn = new TableColumn<>("CHOOSEN MAJOR");
			choosenMajorColumn.setCellValueFactory(new PropertyValueFactory<>("choosenMajor"));
			choosenMajorColumn.setMinWidth(250); 
			choosenMajorColumn.setStyle("-fx-alignment: CENTER;");
			// add columns into table
			StudentsTable.setMaxWidth(1100);
			StudentsTable.setMaxHeight(900);
			StudentsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
			StudentsTable.getColumns().addAll(idColumn,nameColumn,acceptedColumn,TGColumn,PTGColumn,choosenMajorColumn);
	        StudentsTable.setItems(studentsList);
	        
	        Stage addMajor = new Stage();
	        
	        StudentsTable.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2) {
		            Student selectedStudent = StudentsTable.getSelectionModel().getSelectedItem();
		            if (selectedStudent != null) {
		                tempS=selectedStudent;
		                tempList.clear();
		                tempList.add(tempS);
		                idField.setText(tempS.getId()+"");
						addMajor.close();
		            }
		        }
		    });
	        
	        VBox vbox=new VBox(10);
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().addAll(header,StudentsTable);
			BorderPane borderpane=new BorderPane();
			borderpane.setCenter(vbox);
			
			Scene scene=new Scene(borderpane,1200,500); 
			addMajor.setTitle("Add New Major");
			addMajor.setScene(scene);
			addMajor.show();
		 
	 }

	 ////////////show rejected students //////////////////
	 
	 private void showRejectedStudentsStage() {
		 
		 Label header=new Label("Rejected Students");
		 header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		 header.setFont(Font.font("Times New Roman", 40));
		 
		//================== tableView========================
			TableView<Student> rejectedStudentsTable = new TableView<Student>();
			//name column
			TableColumn<Student, String> nameColumn = new TableColumn<>("STUDENT NAME");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			nameColumn.setMinWidth(250); 
			nameColumn.setStyle("-fx-alignment: CENTER;");
			// accepted column
			TableColumn<Student, Boolean> acceptedColumn = new TableColumn<>("ACCEPTED");
			acceptedColumn.setCellValueFactory(new PropertyValueFactory<>("accepted"));
			acceptedColumn.setMinWidth(100); 
			acceptedColumn.setStyle("-fx-alignment: CENTER;");
			//AG column
			TableColumn<Student, Double> TGColumn = new TableColumn<>("TAWJIHI GRADE");
			TGColumn.setCellValueFactory(new PropertyValueFactory<>("TG"));
			TGColumn.setMinWidth(200); 
			TGColumn.setStyle("-fx-alignment: CENTER;");
			// TW column
			TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			idColumn.setMinWidth(100); 
			idColumn.setStyle("-fx-alignment: CENTER;");
			// PTW column
			TableColumn<Student, Double> PTGColumn = new TableColumn<>("PLACEMENT TEST GRADE");
			PTGColumn.setCellValueFactory(new PropertyValueFactory<>("PTG"));
			PTGColumn.setMinWidth(200);  
			PTGColumn.setStyle("-fx-alignment: CENTER;");
			//name column
			TableColumn<Student, String> choosenMajorColumn = new TableColumn<>("CHOOSEN MAJOR");
			choosenMajorColumn.setCellValueFactory(new PropertyValueFactory<>("choosenMajor"));
			choosenMajorColumn.setMinWidth(250); 
			choosenMajorColumn.setStyle("-fx-alignment: CENTER;");
			// add columns into table
			rejectedStudentsTable.setMaxWidth(1100);
			rejectedStudentsTable.setMaxHeight(900);
			rejectedStudentsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
			rejectedStudentsTable.getColumns().addAll(idColumn,nameColumn,acceptedColumn,TGColumn,PTGColumn,choosenMajorColumn);
			rejectedStudentsTable.setItems(NotAcceptedstudentsList);
	        
        	Stage addMajor = new Stage();
	        
        	rejectedStudentsTable.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2) {
		            Student selectedStudent = rejectedStudentsTable.getSelectionModel().getSelectedItem();
		            if (selectedStudent != null) {
		                tempS=selectedStudent;
		                tempList.clear();
		                tempList.add(tempS);
		                idField.setText(tempS.getId()+"");
						addMajor.close();
		            }
		        }
		    });
	        
	        VBox vbox=new VBox(10);
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().addAll(header,rejectedStudentsTable);
			BorderPane borderpane=new BorderPane();
			borderpane.setCenter(vbox);
			
			Scene scene=new Scene(borderpane,1200,500); 
			addMajor.setTitle("Add New Major");
			addMajor.setScene(scene);
			addMajor.show();
		 
	 }
	
	 /////////////// add new Student stage ////////////
	 private void addNewStudentStage() {
		 
		//=============== Labels ==================
		String style="-fx-font-family: 'Times New Roman'; -fx-font-size: 15; -fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;";
		Label header=new Label("Add New Student");
		Label name = new Label("STUDENT NAME :");
		Label choosenMajor = new Label("CHOOSEN MAJOR :");
		Label taw_grade = new Label("TAWJIHI GRADE :");
		Label ptest_grade = new Label("PLACEMENT TEST GRADE :");
		
		name.setStyle(style);
		choosenMajor.setStyle(style);
		taw_grade.setStyle(style);
		ptest_grade.setStyle(style);
		header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
		header.setFont(Font.font("Times New Roman", 50));
		//=========== TextFields===================
		TextField namef = new TextField();
		TextField choosenMajorf = new TextField();
		TextField taw_gradef = new TextField();
		TextField ptest_gradef = new TextField();
		//=============== Grid pane==========
		GridPane gpane=new GridPane();
		gpane.add(name, 0, 0);
		gpane.add(choosenMajor, 0, 1);
		gpane.add(taw_grade, 0, 2);
		gpane.add(ptest_grade, 0, 3);
		gpane.add(namef, 1, 0);
		gpane.add(choosenMajorf, 1, 1);
		gpane.add(taw_gradef, 1, 2);
		gpane.add(ptest_gradef, 1, 3);
		gpane.setAlignment(Pos.CENTER);
		gpane.setVgap(30);
		gpane.setHgap(30);
		
		TableView<Major> suggestedMajorsTable = new TableView<Major>();
		//name column
		TableColumn<Major, String> nameColumn = new TableColumn<>("SUGESTED MAJORS");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setMinWidth(250); 
		nameColumn.setStyle("-fx-alignment: CENTER;");
		suggestedMajorsTable.setMaxWidth(260);
		suggestedMajorsTable.setMaxHeight(450);
		suggestedMajorsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
		suggestedMajorsTable.getColumns().addAll(nameColumn);
		
		
		//============Button==================
		Button button = new Button("ADD NEW STUDENT");
		button.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
		
		button.setOnAction(e->{
			try {
				if(!namef.getText().isEmpty()||!choosenMajorf.getText().isEmpty()||!taw_gradef.getText().isEmpty()||!ptest_gradef.getText().isEmpty()) {
					Student newStudent=new Student(namef.getText(),Double.parseDouble(taw_gradef.getText()),Double.parseDouble(ptest_gradef.getText()),choosenMajorf.getText());
					boolean accepted=addStudent(newStudent);
					String reason="";
					if(newStudent.getPTG()>newStudent.getTG()) 
						reason=" HIS TAWGIHI GRADE ";
					else if(newStudent.getPTG()<newStudent.getTG())
						reason=" HIS PLACEMENT TEST GRADE ";
					else
						reason=" THE CHOOSEN MAJOR ACCEPTENCE GRADE ";
					if(accepted) {
						namef.setText(null);
						choosenMajorf.setText(null);
						taw_gradef.setText(null);
						ptest_gradef.setText(null);
					}
					else {
						getAlert("STUDENT NOT ACCEPTED IN THIS MAJOR BECAUSE OF"+reason);
						suggestedMajorsTable.setItems(getSuggestedMajors(Double.parseDouble(taw_gradef.getText()),Double.parseDouble(ptest_gradef.getText())));
						suggestedMajorsTable.setOnMouseClicked(event -> {
					        if (event.getClickCount() == 2) {
					            Major selectedMajor = suggestedMajorsTable.getSelectionModel().getSelectedItem();
					            if (selectedMajor!= null) {
					            	choosenMajorf.setText(selectedMajor.getName());
					            }
					        }
					    });
					}
				}
				else
					throw new NullPointerException("NO EMPTY FIELDS ARE ALLOWED , PLEASE FILL ALL FIELDS THEN PRESS THE BUTTON!");
			}catch(NullPointerException n) {
				getAlert(n.getMessage());
			}catch(NumberFormatException nf) {
				getAlert("WRONG DATA TYPE , PLEASE INSERT CORRECT DATA THEN PRESS THE BUTTON !");
			}catch(IllegalArgumentException i) {
				getAlert(i.getMessage());
			}
		});
		
		VBox vbox=new VBox(30);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(header,gpane,button);
		HBox hbox=new HBox(10);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(vbox,suggestedMajorsTable);
		BorderPane borderpane=new BorderPane();
		borderpane.setCenter(hbox);
		
		Stage addMajor = new Stage();
		Scene scene=new Scene(borderpane,700,500); 
		addMajor.setTitle("Add New Major");
		addMajor.setScene(scene);
		addMajor.show();
			
	 }
	
	 //////////////// update student stage/////////////
	 	
      private void updateStudentStage() {
    	  
    	//=============== Labels ==================
  		String style="-fx-font-family: 'Times New Roman'; -fx-font-size: 15; -fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;";
  		Label header=new Label("Update Student");
  		Label name = new Label("STUDENT NAME :");
  		Label choosenMajor = new Label("CHOOSEN MAJOR :");
  		Label taw_grade = new Label("TAWJIHI GRADE :");
  		Label ptest_grade = new Label("PLACEMENT TEST GRADE :");
  		Label messege = new Label("");
  		
  		name.setStyle(style);
  		choosenMajor.setStyle(style);
  		taw_grade.setStyle(style);
  		ptest_grade.setStyle(style);
  		messege.setStyle(style);
  		header.setStyle("-fx-text-fill: rgb(0, 100, 38); -fx-font-weight: bold;");
  		header.setFont(Font.font("Times New Roman", 50));
  		//=========== TextFields===================
  		TextField namef = new TextField();
  		TextField choosenMajorf = new TextField();
  		TextField taw_gradef = new TextField();
  		TextField ptest_gradef = new TextField();
  		namef.setText(tempS.getName());
		choosenMajorf.setText(tempS.getChoosenMajor());
		taw_gradef.setText(tempS.getTG()+"");
		ptest_gradef.setText(tempS.getPTG()+"");
  		//=============== Grid pane==========
  		GridPane gpane=new GridPane();
  		gpane.add(name, 0, 0);
  		gpane.add(choosenMajor, 0, 1);
  		gpane.add(taw_grade, 0, 2);
  		gpane.add(ptest_grade, 0, 3);
  		gpane.add(namef, 1, 0);
  		gpane.add(choosenMajorf, 1, 1);
  		gpane.add(taw_gradef, 1, 2);
  		gpane.add(ptest_gradef, 1, 3);
  		gpane.setAlignment(Pos.CENTER);
  		gpane.setVgap(30);
  		gpane.setHgap(30);
  		
  		TableView<Major> suggestedMajorsTable = new TableView<Major>();
  		//name column
  		TableColumn<Major, String> nameColumn = new TableColumn<>("SUGESTED MAJORS");
  		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
  		nameColumn.setMinWidth(250); 
  		nameColumn.setStyle("-fx-alignment: CENTER;");
  		suggestedMajorsTable.setMaxWidth(260);
  		suggestedMajorsTable.setMaxHeight(450);
  		suggestedMajorsTable.setStyle("-fx-border-color:black;-fx-border-Width:3;-fx-background-color:rgb(204, 255, 229);-fx-control-inner-background:rgb(204, 255, 229);");
  		suggestedMajorsTable.getColumns().addAll(nameColumn);
  		
  	//============Button==================
		Button button = new Button("UPDATE STUDENT");
		button.setStyle("-fx-background-color: rgb(0, 100, 38); -fx-text-fill: white; -fx-border-color: black; -fx-font-weight: bold;-fx-font-size: 15px;");
  		
		button.setOnAction(e->{
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("ERORR");
			alert.setContentText("ARE YOU SURE YOU WANT TO UPDATE THIS STUDENT INFORMATION ?");
			alert.setHeaderText("WARNING ALERT");
			ButtonType confirm= new ButtonType("confirm");
			alert.getDialogPane().getButtonTypes().clear();
			alert.getDialogPane().getButtonTypes().add(confirm);
			alert.setResultConverter(dialogButton -> {
	          if (dialogButton == confirm) {
	        	  try {
	        		  if(studentsList.size()>1) {
	              		if(tempS!=null) { 
	  						
						if(!namef.getText().isEmpty()||!choosenMajorf.getText().isEmpty()||!taw_gradef.getText().isEmpty()||!ptest_gradef.getText().isEmpty()) {
							
							if(tempS.getChoosenMajor().equalsIgnoreCase(choosenMajorf.getText())) {
								tempS.setName(namef.getText());
								tempS.setPTG(Double.parseDouble(ptest_gradef.getText()));
								tempS.setTG(Double.parseDouble(taw_gradef.getText()));
								DNode choosenN=majors.search(choosenMajorf.getText());
								Major choosen=(Major)choosenN.getElement();
								double AM=(choosen.getTaw_grade()*tempS.getTG())+(choosen.getPtest_grade()*tempS.getPTG());
								if(AM>=choosen.getAcc_grade()) {
									tempS.setAccepted(true);
								}
								else
									tempS.setAccepted(false);
								tempList.clear();
								tempList.add(tempS);
								
							}
							else {
								if(majors.getCount()!=1) {
									DNode choosenN=majors.search(choosenMajorf.getText());
									Major choosen=(Major)choosenN.getElement();
									if(choosen!=null) {
										double AM=(choosen.getTaw_grade()*tempS.getTG())+(choosen.getPtest_grade()*tempS.getPTG());
										//System.out.println(AM);
										if(AM>=choosen.getAcc_grade()) {
											DNode node=majors.search(tempS.getChoosenMajor());
											((Major) node.getElement()).getStudents().remove(tempS);
											((Major) node.getElement()).setAcceptedStudentsCount(((Major) node.getElement()).getAcceptedStudentsCount()-1);
											tempS.setAddmissionMark(AM);
											tempS.setChoosenMajor(choosenMajorf.getText());
											choosen.getStudents().sortedAdd(tempS);
											choosen.setAcceptedStudentsCount(choosen.getAcceptedStudentsCount()+1);
											tempS.setAccepted(true);
											tempList.clear();
											tempList.add(tempS);
										}
										else {
											choosen.setNotAcceptedStudentsCount(choosen.getNotAcceptedStudentsCount()+1);
											tempS.setAccepted(false);
										}	
									}
									else {
										throw new NullPointerException("THIS MAJOR IS NOT IN SYSTEM!!");
									}
								}
								else
									throw new NullPointerException("No Majors In System !!");
							}
							String reason="";
							if(tempS.getPTG()>tempS.getTG()) 
								reason=" HIS TAWGIHI GRADE ";
							else if(tempS.getPTG()<tempS.getTG())
								reason=" HIS PLACEMENT TEST GRADE ";
							else
								reason=" THE CHOOSEN MAJOR ACCEPTENCE GRADE ";
							
							if(tempS.isAccepted()) {
								messege.setText("Student Updated");
								for(int i=0;i<NotAcceptedstudentsList.size();i++) {
									if(tempS.getId()==NotAcceptedstudentsList.get(i).getId()) {
										NotAcceptedstudentsList.remove(i);
									}
								}
								
							}
							else {
								getAlert("STUDENT NOT ACCEPTED IN THIS MAJOR BECAUSE OF"+reason);
								suggestedMajorsTable.setItems(getSuggestedMajors(Double.parseDouble(taw_gradef.getText()),Double.parseDouble(ptest_gradef.getText())));
								suggestedMajorsTable.setOnMouseClicked(event -> {
							        if (event.getClickCount() == 2) {
							            Major selectedMajor = suggestedMajorsTable.getSelectionModel().getSelectedItem();
							            if (selectedMajor!= null) {
							            	choosenMajorf.setText(selectedMajor.getName());
							            }
							        }
							    });
							}
						}
						else
							throw new NullPointerException("NO EMPTY FIELDS ARE ALLOWED , PLEASE FILL ALL FIELDS THEN PRESS THE BUTTON!");
					}		
	  			       }
	        	  }catch(NullPointerException n) {
						getAlert(n.getMessage());
					}catch(NumberFormatException nf) {
						getAlert("WRONG DATA TYPE , PLEASE INSERT CORRECT DATA THEN PRESS THE BUTTON !");
					}catch(IllegalArgumentException i) {
						getAlert(i.getMessage());
					}
	          }
			return null;
		    });
			alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			alert.showAndWait();
		});
		VBox vbox=new VBox(30);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(header,gpane,messege,button);
		HBox hbox=new HBox(10);
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(vbox,suggestedMajorsTable);
		BorderPane borderpane=new BorderPane();
		borderpane.setCenter(hbox);
		
		Stage addMajor = new Stage();
		Scene scene=new Scene(borderpane,700,500); 
		addMajor.setTitle("Add New Major");
		addMajor.setScene(scene);
		addMajor.show();
      }
	 ////////////// get alert//////////////////////////

	private void getAlert(String str) {
		Alert alert=new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERORR");
		alert.setContentText(str);
		alert.setHeaderText("ERROR ALERT");
		alert.showAndWait();
	}
	
	//////////////// update majors OBlist/////////////////
	
	private void updateMajorsList() {
		majorsList.clear();
		DNode ptr=majors.getFirst();
		if(ptr!=null) {
			for(int i=0;i<majors.getCount();i++) {
				majorsList.add((Major)ptr.getElement());
				ptr=ptr.getNext();
			}
		}
	}
	
	////////////// update students OBlist/////////////////////////
	
	private void updateStudentsInTempMajor() {
		SNode sTemp=((Major)temp.getElement()).getStudents().getFirst();
		studentsInTempMajorList.clear();
		if(sTemp!=null) {
			for(int i=0;i<((Major)temp.getElement()).getStudents().getCount()-1;i++) {
				studentsInTempMajorList.add((Student)sTemp.getElement());	
				sTemp=sTemp.getNext();
			}
			//.out.println(((Major)temp.getElement()).getStudents().getCount()-1);
		}
	}
	
	/////////////// addStudent //////////////////////
	private boolean addStudent(Student s) {
		studentsList.add(s);
		if(majors.getCount()!=1) {
			DNode choosenN=majors.search(s.getChoosenMajor());
			Major choosen=(Major)choosenN.getElement();
			if(choosen!=null) {
				double AM=(choosen.getTaw_grade()*s.getTG())+(choosen.getPtest_grade()*s.getPTG());
				//System.out.println(AM);
				if(AM>=choosen.getAcc_grade()) {
					s.setAddmissionMark(AM);
					choosen.getStudents().sortedAdd(s);
					choosen.setAcceptedStudentsCount(choosen.getAcceptedStudentsCount()+1);
					s.setAccepted(true);
				}
				else {
					choosen.setNotAcceptedStudentsCount(choosen.getNotAcceptedStudentsCount()+1);
					s.setAccepted(false);
					NotAcceptedstudentsList.add(s);
					return false;
				}
				return true;	
			}
			else {
				throw new NullPointerException("THIS MAJOR IS NOT IN SYSTEM!!");
			}
		}
		throw new NullPointerException("No Majors In System !!");
	}

	////////////// getSuggestedMajors //////////////
	private ObservableList<Major> getSuggestedMajors(double TG,double PTG){
		ObservableList<Major> suggestedMajorsList=FXCollections.observableArrayList();
		suggestedMajorsList.clear();
		DNode node=majors.getFirst();
		if(majors.getCount()>1) {
			for(int i=0;i<majors.getCount();i++) {
				Major m=(Major)node.getElement();
				double ag=(m.getTaw_grade()*TG)+(m.getPtest_grade()*PTG);
				if(ag>=m.getAcc_grade()) {
					suggestedMajorsList.add(m);
				}
				node=node.getNext();
			}
		}
		else {
			throw new IllegalArgumentException("NO MAJORS IN SYSTEM!!");
		}
		if(suggestedMajorsList.size()==0)
			throw new IllegalArgumentException("NO MAJORS SUGGESTED !! STUDENT CAN NOT REGISTRATE :(");
		return suggestedMajorsList;
		
	}
	
	////////////////// main method /////////////////////
	public static void main(String[] args) {
		majors.sortedAdd(new Major("Major's Name",99,0.5,0.5));
		launch(args);
	}
}
