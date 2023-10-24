package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class regController implements Initializable{

	@FXML
    private TextField fullName;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane loginForm;
    
    @FXML
    private ComboBox<String> loginUserSelection;

    @FXML
    private PasswordField password;
    
    @FXML
    private AnchorPane sideCover;

    @FXML
    private PasswordField sidePassword;

    @FXML
    private TextField sideUsername;

    @FXML
    private Button side_alreadyBtn;

    @FXML
    private Button side_createBtn;

    @FXML
    private Button signupBtn;

    @FXML
    private AnchorPane signupForm;

    @FXML
    private TextField userDept;
    
    @FXML
    private TextField userPhn;
    
    @FXML
    private TextField userEmail;
    
    @FXML
    private TextField userID;
    
    @FXML
    private TextField userSession;
    
    @FXML
    private ComboBox<String> userSelection;

    @FXML
	private TextField username;
    
    @FXML
    private ImageView passViewImageBtn;

    Connection conn, lgnconn;
    PreparedStatement ps, ps1, ps2, ps3, ps4, lgnps, chk_ps, user_ps;
    ResultSet rs, user_rs;
    
//    public void connectDB() throws Exception {
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/lab_assist", "root", "");
//		}catch(Exception e) {
//			Logger.getLogger(regController.class.getName()).log(Level.SEVERE, null, e);
//		}	
//	}  
    
    @FXML
    void doSignUp(ActionEvent event) {
    	String name, dept, session, str_id, phnNo, userMail, uname, pwd;
    	name = fullName.getText();
    	dept = userDept.getText();
    	session = userSession.getText();
    	str_id = userID.getText();
    	phnNo = userPhn.getText();
    	userMail = userEmail.getText();
    	uname = sideUsername.getText();
    	pwd = sidePassword.getText();
    	
    	if(name.isEmpty()||dept.isEmpty()||session.isEmpty()||str_id.isEmpty()||phnNo.isEmpty()||userMail.isEmpty()||uname.isEmpty()||pwd.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("User Registration");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all the text fields.");
    		alert.showAndWait();
		}
    	else {
    		
        	String choice = userSelection.getValue();
        	
        	try {
        		int id = Integer.parseInt(str_id);
        		
        		if(choice==null) {
        			Alert alert = new Alert(Alert.AlertType.ERROR);
            		alert.setTitle("User Registration");
            		alert.setHeaderText(null);
            		alert.setContentText("Please select User Type first.");
            		alert.showAndWait();
        			
        		}
        		
        		else if(choice=="Teacher") {
        			ps1 = conn.prepareStatement("insert into teacher(teacherID, tName, tDept) values(?,?,?);");
            		ps1.setInt(1, id);
            		ps1.setString(2, name);
            		ps1.setString(3, dept);	
        		}
        		else if(choice=="Instructor") {
        			ps1 = conn.prepareStatement("insert into instructor(insID, insName) values(?,?);");
            		ps1.setInt(1, id);
            		ps1.setString(2, name);
        		}
        		else if(choice == "Student") {
    	    		ps1 = conn.prepareStatement("insert into student(ID, Name, Dept, Session) values(?,?,?,?);");
    	    		ps1.setInt(1, id);
    	    		ps1.setString(2, name);
    	    		ps1.setString(3, dept);
    	    		ps1.setString(4, session);	
    	    		
    	    		String msg = "Please submit your request first.";
    	    		String insertMsg = "insert into message_list(userID, item_message) VALUES(?,?)";
    	    		ps4 = conn.prepareStatement(insertMsg);
    	    		ps4.setInt(1, id);
    	    		ps4.setString(2,msg);
    	    		
        		}
        		
        		//Insert into user_info table
        		ps2 = conn.prepareStatement("insert into user_info(Username, Password, UserID) values(?,?,?);");
        		ps2.setString(1, uname);
        		ps2.setString(2, pwd);
        		ps2.setInt(3, id);

        		//Insert into contact table
        		ps3 = conn.prepareStatement("insert into contact(userID, Phone_no, Email) values(?,?,?);");
        		ps3.setInt(1, id);
        		ps3.setString(2, phnNo);
        		ps3.setString(3, userMail);

        		//check if user exists already or not
        		if(choice=="Student"||choice == "Teacher"||choice == "Instructor") {
        			String userChecker = "Select userID from user_info where userID='" + id + "'";
            		chk_ps = conn.prepareStatement(userChecker);
            		rs = chk_ps.executeQuery();
            		if(rs.next()) {
            			Alert alert = new Alert(Alert.AlertType.ERROR);
                		alert.setTitle("User Registration");
                		alert.setHeaderText(null);
                		alert.setContentText(id + " already exixts in the database.");
                		alert.showAndWait();
            		}
            		else {
            			
            			ps2.execute();
            			ps1.execute();
            			ps3.execute();
            			ps4.execute();
            			
                		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                		alert.setTitle("User Registration");
                		alert.setHeaderText(null);
                		alert.setContentText("Registration Successful");
                		alert.showAndWait();
                		
                		fullName.clear();
                    	userDept.clear();
                    	userSession.clear();
                    	userID.clear();
                    	userPhn.clear();
                    	userEmail.clear();
                    	sideUsername.clear();
                    	sidePassword.clear();
            		}
        		}
        	}
        	catch(Exception e) {
        		Alert alert = new Alert(Alert.AlertType.ERROR);
        		alert.setTitle("Error Found");
        		alert.setHeaderText(null);
        		alert.setContentText(" "+e);
        		alert.showAndWait();
        	}
    	}
    }

    
    @FXML
    void doLogin(ActionEvent event){

    	String lguname, lgpwd, idCheck="";
    	lguname = username.getText();
    	lgpwd = password.getText();

    	try {
    		String userType = loginUserSelection.getValue();
    		
    		if(userType==null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("User Type Error");
	    		alert.setHeaderText(null);
	    		alert.setContentText("Please select a correct User Type.");
	    		alert.showAndWait();
			}
    		else if(userType.equals("Student")) {
    			idCheck = "select ID from student where ID in(select userID from user_info where Username = '"+lguname+"');";		
    		}
    		else if(userType.equals("Teacher")) {
    			idCheck = "select teacherID from teacher where teacherID in(select userID from user_info where Username = '"+lguname+"');";
    		}
    		else if(userType.equals("Instructor")) {
    			idCheck = "select insID from instructor where insID in(select userID from user_info where Username = '"+lguname+"');";
    		}
    		
    		user_ps = conn.prepareStatement(idCheck);
			user_rs = user_ps.executeQuery();
			
			if(!user_rs.next()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("Login Credentials Erroe");
	    		alert.setHeaderText(null);
	    		alert.setContentText(lguname+" did not match for "+userType+" user type");
	    		alert.showAndWait();
			}
			else {
				lgnps = conn.prepareStatement("select Username,Password from user_info where Username=? and Password=?");
	    		lgnps.setString(1, lguname);
	    		lgnps.setString(2, lgpwd);
	    		rs = lgnps.executeQuery();
	    		
	    		if(rs.next()) {
	    			Alert alert = new Alert(AlertType.CONFIRMATION);
	        		alert.setTitle("User Login");
	        		alert.setHeaderText(null); 
	        		alert.setContentText("Login Successful. Welcome to LabAssist.");
	        		alert.show();
	        		
	        		//carditemController function ke call kora
	        		FXMLLoader cloader = new FXMLLoader(getClass().getResource("cardItemfxml.fxml"));
	        		Parent root1 = cloader.load();
	            	carditemController cardC = cloader.getController();
	            	cardC.getUserID(lguname);
	        		
	        		//get access to another conroller
	            	FXMLLoader loader = new FXMLLoader(getClass().getResource("Homefxml.fxml"));
	            	Parent root = loader.load();
	            	HomeController homeController = loader.getController();
	            	homeController.showName(userType,lguname);//call function of another controller
	            	homeController.getUserID(lguname);

	        		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        		Scene scene = new Scene(root);
	        		stage.setScene(scene);
	        		stage.show();
	    		}
	    		else {
	    			Alert alert = new Alert(Alert.AlertType.ERROR);
	        		alert.setTitle("Error Found");
	        		alert.setHeaderText(null);
	        		alert.setContentText("Password did not match.");
	        		alert.showAndWait();
				}
			}
    	}catch(Exception e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("Error Found");
    		alert.setHeaderText(null);
    		alert.setContentText(" "+e);
    		alert.showAndWait();
    	}	
    }
    
    private Image image;
    private Button imgBtn;
   
    void setImageAsButton() {
    	String path = "File:" + "C:\\Users\\Noman\\Desktop\\Project Image\\pwdview.png";
    	image = new Image(path, 30, 30, false, true);
    	passViewImageBtn.setImage(image);
    	
    	imgBtn = new Button(""+passViewImageBtn);
    }
    
    public void switchForm(ActionEvent event) {
    	TranslateTransition slider = new TranslateTransition();
    	
    	if(event.getSource()==side_createBtn) {
    		slider.setNode(sideCover);
    		slider.setToX(350);
    		slider.setDuration(Duration.seconds(.5));
    		
    		slider.setOnFinished((ActionEvent e) ->{
    			side_alreadyBtn.setVisible(true);
    			side_createBtn.setVisible(false);
    		});
    		slider.play();
    	}else if(event.getSource()==side_alreadyBtn) {
    		slider.setNode(sideCover);
    		slider.setToX(0);
    		slider.setDuration(Duration.seconds(.5));
    		
    		slider.setOnFinished((ActionEvent e) ->{
    			side_alreadyBtn.setVisible(false);
    			side_createBtn.setVisible(true);
    		});
    		slider.play();
    	}
    }
    public void doChoice(ActionEvent event) {
    	String choice = userSelection.getValue();;
    	
    	if(choice=="Teacher") {
    		userDept.setDisable(false);
    		userDept.clear();
    		userDept.setStyle("");
    		
    		userSession.setDisable(true);
    		userSession.setText("Not Applicable");
    		userSession.setStyle("-fx-text-fill: #9a1010; -fx-font-weight:bold; -fx-font-size: 13px;");
    	}
    	else if(choice == "Instructor") {
    		userDept.setDisable(true);
    		userDept.setText("Not Applicable");
    		userDept.setStyle("-fx-text-fill: #9a1010; -fx-font-weight:bold; -fx-font-size: 13px;");
    		
    		userSession.setDisable(true);
    		userSession.setText("Not Applicable");
    		userSession.setStyle("-fx-text-fill: #9a1010; -fx-font-weight:bold; -fx-font-size: 13px;");
    		
    	}else {
    		userSession.setDisable(false);
    		userSession.clear();
    		userSession.setStyle("");
    		
    		userDept.setDisable(false);
    		userDept.clear();
    		userDept.setStyle("");
    	}	
    }
    
	@Override
    public void initialize(URL url, ResourceBundle rb) {
		try {
			conn = javaConnect.connectDB();
			userSelection.setItems(FXCollections.observableArrayList("Student","Teacher","Instructor"));
			loginUserSelection.setItems(FXCollections.observableArrayList("Student","Teacher","Instructor"));
			userSelection.setOnAction(this::doChoice);
			setImageAsButton();
			
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
    } 
}
