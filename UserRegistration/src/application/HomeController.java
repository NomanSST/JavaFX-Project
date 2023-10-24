package application;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class HomeController implements Initializable {

	@FXML
	private AnchorPane assignDate;

	@FXML
	private Label displayName;

	@FXML
	private AnchorPane headerLabel;

	@FXML
	private Label headerText;

	@FXML
	private AnchorPane inventoryBtnPane;

	@FXML
	private Button inventoryViewBtn;

	@FXML
	private TableColumn<instrumentData, String> inventory_col_itemID;

	@FXML
	private TableColumn<instrumentData, String> inventory_col_itemName;

	@FXML
	private TableColumn<instrumentData, String> inventory_col_itemQuantity;

	@FXML
	private TableColumn<instrumentData, String> inventory_col_itemStatus;

	@FXML
	private Button inventory_clearBtn;

	@FXML
	private Button inventory_deleteBtn;

	@FXML
	private AnchorPane inventory_form;

	@FXML
	private ImageView inventory_imageView;

	@FXML
	private Button inventory_importBtn;

	@FXML
	private Button inventory_insertBtn;

	@FXML
	private TextField inventory_itemID;

	@FXML
	private TextField inventory_itemName;

	@FXML
	private TextField inventory_itemQuantity;

	@FXML
	private ComboBox<String> inventory_itemStatus;

	@FXML
	private TableView<instrumentData> inventory_tableView;

	@FXML
	private Button inventory_updateBtn;

	@FXML
	private AnchorPane item_form;

	@FXML
	private Button itemsViewBtn;

	@FXML
	private Button logOutBtn;

	@FXML
	private AnchorPane main_form;

	@FXML
	private TableColumn<instrumentData, String> menu_col_InstrumentName;

	@FXML
	private TableColumn<instrumentData, String> menu_col_itemID;

	@FXML
	private TableColumn<instrumentData, String> menu_col_quantity;

	@FXML
	private AnchorPane menu_form;

	@FXML
	private GridPane menu_gridPane;

	@FXML
	private Button menu_removeBtn;

	@FXML
	private Button menu_reportBtn;

	@FXML
	private ScrollPane menu_scrollPane;

	@FXML
	private Button menu_submitBtn;

	@FXML
	private TableView<instrumentData> menu_tableView;

	@FXML
	private AnchorPane requestList;

	@FXML
	private Button requestResultBtn;

	@FXML
	private Button searchQueryBtn;

	@FXML
	private AnchorPane request_form;

	@FXML
	private ScrollPane request_scrollPane;

	@FXML
	private Label result_messageBox;
	
    @FXML
    private DatePicker returnDatePicker;

	@FXML
	private Button req1_approveBtn;
	
    @FXML
    private Button req2_assignBtn;

	@FXML
	private TableView<studentRequest> table_requestListView;

	@FXML
	private TableView<studentRequest> table_requestListView2;

	@FXML
	private TableView<studentRequest> table_requestListView3;

	@FXML
	private TableColumn<studentRequest, String> req_col_studentID;

	@FXML
	private TableColumn<studentRequest, String> req_col_studentName;

	@FXML
	private TableColumn<studentRequest, String> req_col_Dept;

	@FXML
	private TableColumn<studentRequest, String> req_col_Session;

	@FXML
	private TableColumn<studentRequest, String> req_col_requestNo;

	@FXML
	private TableColumn<studentRequest, String> req2_col_studentID;

	@FXML
	private TableColumn<studentRequest, String> req2_col_studentName;

	@FXML
	private TableColumn<studentRequest, String> req2_col_requestNo;

	@FXML
	private TableColumn<studentRequest, String> req2_col_approveStatus;

	@FXML
	private TableColumn<studentRequest, String> req3_col_studentID;

	@FXML
	private TableColumn<studentRequest, String> req3_col_studentName;

	@FXML
	private TableColumn<studentRequest, String> req3_col_requestNo;

	@FXML
	private TableColumn<studentRequest, String> req3_col_approveStatus;

	@FXML
	private TableColumn<studentRequest, String> req3_col_returnDate;

	private Image image;

	public String uname;

	private int uid;
	private String stdname;
	private String stdsession;
	private String stddept;
	private int req_no = 0;

	private Connection conn;
	private PreparedStatement ps, ps_mt, std_ps;
	private ResultSet rs, std_rs;

	public String resultmsg="";
	
	public void showMessage() {
		String getmsg = "select item_message from message_list where userID = "+uid;
		try {
			ps = conn.prepareStatement(getmsg);
			rs = ps.executeQuery();
			rs.next();
			resultmsg = rs.getString("item_message");
			
			result_messageBox.setStyle(null);
			result_messageBox.setText("");
			result_messageBox.setText(resultmsg);
			result_messageBox.setStyle("-fx-text-fill: #04800c; -fx-font-weight:bold; -fx-font-size: 15px;");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void showName(String userType, String lguname) throws Exception {
		String uType = userType;
		String userName = lguname;
		uname = lguname;

		if (uType == "Student") {
			String url = "select Name from student where ID in (select userID from user_info where Username ='"
					+ userName + "');";
			ps = conn.prepareStatement(url);
			rs = ps.executeQuery();// eta dewar karone cursor ekdom oi row er shurute chilo. tai se kono column er
									// value dite parsilo na.
			rs.next();// eta diye cursor ke next row te set kore ager row er value guloke access kora
						// jay
			String name = rs.getString(1);
			displayName.setText("" + name);
			
			
			
		} else if (userType == "Teacher") {
			String url = "select tName from teacher where teacherID in (select userID from user_info where Username ='"
					+ lguname + "';);";
			ps = conn.prepareStatement(url);
			rs = ps.executeQuery();
			rs.next();
			String name = rs.getString(1);
			displayName.setText("" + name);
		} else if (userType == "Instructor") {
			String url = "select insName from instructor where insID in (select userID from user_info where Username = '"
					+ lguname + "';);";
			ps = conn.prepareStatement(url);
			rs = ps.executeQuery();
			rs.next();
			String name = rs.getString(1);
			displayName.setText("" + name);
		}
	}

	public void getUserID(String lguname) {
		String username = uname;
		String uidQuery = "select userID from user_info where Username ='" + username + "'";
		try {
			ps = conn.prepareStatement(uidQuery);
			rs = ps.executeQuery();
			rs.next();
			uid = rs.getInt("userID");

			String getDetails = "Select Name,Dept,Session from student where ID = " + uid;
			ps = conn.prepareStatement(getDetails);
			rs = ps.executeQuery();
			rs.next();
			stdname = rs.getString("Name");
			stddept = rs.getString("Dept");
			stdsession = rs.getString("Session");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchUserAccess(ActionEvent event) {

		if (event.getSource() == itemsViewBtn) {
			inventory_form.setVisible(false);
			request_form.setVisible(false);
			item_form.setVisible(true);

			menuShowListData();
			menuDisplayCard();
			showMessage();

		} else if (event.getSource() == requestResultBtn) {
			inventory_form.setVisible(false);
			item_form.setVisible(false);
			request_form.setVisible(true);

			reqShowListData1();
			reqShowListData2();
			reqShowListData3();

		} else if (event.getSource() == searchQueryBtn) {

		} else if (event.getSource() == inventoryViewBtn) {
			item_form.setVisible(false);
			request_form.setVisible(false);
			inventory_form.setVisible(true);

			inventoryShowData();
		}

	}

	// Items button er kaj ekhan theke shuru

	private ObservableList<instrumentData> cardListData = FXCollections.observableArrayList();

	public ObservableList<instrumentData> menuGetData() {

		ObservableList<instrumentData> listData = FXCollections.observableArrayList();

		String sql = "select itemID, itemImage, itemName, itemQuantity from inventory_table";

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			instrumentData insDt;
			while (rs.next()) {
				insDt = new instrumentData(rs.getString("itemID"), rs.getString("itemImage"), rs.getString("itemName"),
						rs.getInt("itemQuantity"));

				listData.add(insDt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listData;
	}

	public void menuDisplayCard() {
		cardListData.clear();
		cardListData.addAll(menuGetData());

		int row = 0;
		int column = 0;

		menu_gridPane.getChildren().clear();
		menu_gridPane.getRowConstraints().clear();
		menu_gridPane.getColumnConstraints().clear();
		for (int i = 0; i < cardListData.size(); i++) {
			try {
				FXMLLoader cardLoader = new FXMLLoader();
				cardLoader.setLocation(getClass().getResource("cardItemfxml.fxml"));
				AnchorPane pane = cardLoader.load();
				carditemController cardC = cardLoader.getController();
				cardC.setData(cardListData.get(i));

				if (column == 3) {
					column = 0;
					row = row + 1;
				}

				menu_gridPane.add(pane, column++, row);
				GridPane.setMargin(pane, new Insets(5));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ObservableList<instrumentData> menuGetItem() {

		ObservableList<instrumentData> listData = FXCollections.observableArrayList();

		String sql = "SELECT itemID, itemName, itemQuantity FROM temp_menu_table WHERE userID = " + uid;

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			instrumentData insDT;

			while (rs.next()) {// 3 ta column niye etar constructor create kora hoise
				insDT = new instrumentData(rs.getString("itemID"), rs.getString("itemName"), rs.getInt("itemQuantity"));

				listData.add(insDT);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;
	}

	private ObservableList<instrumentData> menuItemListData;

	public void menuShowListData() {
		menuItemListData = menuGetItem();

		menu_col_itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
		menu_col_InstrumentName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

		menu_tableView.setItems(menuItemListData);
	}

	@FXML
	void menu_submitList(ActionEvent event) {
		String uidChecker = "select userID from menu_table where userID = " + uid;
		try {
			ps = conn.prepareStatement(uidChecker);
			rs = ps.executeQuery();
			if (rs.next()) {
				int ex_uid = rs.getInt("userID");
				if (uid == ex_uid) {
					Alert alert1 = new Alert(Alert.AlertType.ERROR);
					alert1.setTitle("Pending Request");
					alert1.setHeaderText(null);
					alert1.setContentText("Your previous request is pending. Please wait.");
					alert1.showAndWait();
				}
			} else {
				String selectData = "select itemID from temp_menu_table where userID = " + uid;
				ps = conn.prepareStatement(selectData);
				rs = ps.executeQuery();
				if (rs.next()) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Submit");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure you want to SUBMIT ?");
					Optional<ButtonType> option = alert.showAndWait();

					if (option.get().equals(ButtonType.OK)) {

						String newInsert = "Insert into menu_table(userID,itemID,itemName,itemQuantity)"
								+ " Select * from temp_menu_table";
						ps_mt = conn.prepareStatement(newInsert);
						ps_mt.execute();
						
						String insertReq = "Insert into request_list(studentID, studentName, Dept, Session) VALUES(?,?,?,?)";
						std_ps = conn.prepareStatement(insertReq);
						std_ps.setInt(1, uid);
						std_ps.setString(2, stdname);
						std_ps.setString(3, stddept);
						std_ps.setString(4, stdsession);
						std_ps.execute();

						Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
						alert1.setTitle("Submit Confirmation");
						alert1.setHeaderText(null);
						alert1.setContentText("Instrument list submitted.");
						alert1.showAndWait();
						
						resultmsg = "Your request has been SUBMITTED";
						String updateRsMsg = "Update message_list set "
								+"userID = "+uid+", item_message = '"+resultmsg+"'"
								+"Where userID = "+uid;
						ps = conn.prepareStatement(updateRsMsg);
						ps.executeUpdate();
						showMessage();
						
					} else {
						Alert alert1 = new Alert(Alert.AlertType.ERROR);
						alert1.setTitle("Submit Cancel");
						alert1.setHeaderText(null);
						alert1.setContentText("Submission cancelled");
						alert1.showAndWait();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Item Selection Error");
					alert.setHeaderText(null);
					alert.setContentText("Please select instruments");
					alert.showAndWait();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String itemid;

	public void menuSelectData() {
		instrumentData insData = menu_tableView.getSelectionModel().getSelectedItem();
		int num = menu_tableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}
		itemid = insData.getItemID();
	}

	@FXML
	void menu_removeList(ActionEvent event) throws SQLException {
		String uidChecker = "select userID from menu_table where userID = " + uid;
		try {
			ps = conn.prepareStatement(uidChecker);
			rs = ps.executeQuery();
			if (rs.next()) {
				int ex_uid = rs.getInt("userID");
				if (uid == ex_uid) {
					Alert alert1 = new Alert(Alert.AlertType.ERROR);
					alert1.setTitle("Pending Request");
					alert1.setHeaderText(null);
					alert1.setContentText("Your previous request is pending. Please wait.");
					alert1.showAndWait();
				}
			} else {
				if (itemid == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Item Selection error");
					alert.setHeaderText(null);
					alert.setContentText("Please select an item to remove.");
					alert.showAndWait();
				} else {
					String deleteitem = "Delete from temp_menu_table where itemID= '" + itemid + "'";

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Message");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure you want to delete this item ?");
					Optional<ButtonType> option = alert.showAndWait();

					if (option.get().equals(ButtonType.OK)) {
						ps = conn.prepareStatement(deleteitem);
						ps.executeUpdate();
					}

					menuShowListData();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Items section ends here

	// Request Button starts here

	public ObservableList<studentRequest> reqGetList() {

		ObservableList<studentRequest> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM request_list";

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			studentRequest reqDT;

			while (rs.next()) {
				reqDT = new studentRequest(rs.getInt("studentID"), rs.getString("studentName"), rs.getString("Dept"),
						rs.getString("Session"), rs.getInt("request_no"));

				listData.add(reqDT);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;
	}

	private ObservableList<studentRequest> reqListData;

	public void reqShowListData1() {
		reqListData = reqGetList();

		req_col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		req_col_studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
		req_col_Dept.setCellValueFactory(new PropertyValueFactory<>("Dept"));
		req_col_Session.setCellValueFactory(new PropertyValueFactory<>("Session"));
		req_col_requestNo.setCellValueFactory(new PropertyValueFactory<>("request_no"));

		table_requestListView.setItems(reqListData);
	}

	public Integer studentID;
	public String studentName;
	public Integer request_no;

	public void reqSelectData1() {
		studentRequest reqData = table_requestListView.getSelectionModel().getSelectedItem();
		int num = table_requestListView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}
		studentID = reqData.getStudentID();
		studentName = reqData.getStudentName();
		request_no = reqData.getRequest_no();
	}

	@FXML
	void req1_doApprove(ActionEvent event) {
		if (studentID.equals(null)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Data Selection Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student first");
			alert.showAndWait();
			
		} else {
			String appSts = "APPROVED";
			String selectuid = "Select * from request_list where studentID = " + studentID;
			try {
				ps = conn.prepareStatement(selectuid);
				rs = ps.executeQuery();

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Approval Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Do you want to approve request of ID: " + studentID);
				Optional<ButtonType> option = alert.showAndWait();
				if (option.get().equals(ButtonType.OK)) {

					String insertApprove = "INSERT INTO approved_list(studentID, studentName, request_no, approvedStatus) VALUES(?,?,?,?)";
					ps = conn.prepareStatement(insertApprove);
					ps.setInt(1, studentID);
					ps.setString(2, studentName);
					ps.setInt(3, request_no);
					ps.setString(4, appSts);
					ps.executeUpdate();

					String delreq = "Delete from request_list where studentID = " + studentID;
					ps = conn.prepareStatement(delreq);
					ps.executeUpdate();
					
					// update result messagebox
					if (uid == studentID) {
						resultmsg = "Your request has been APPROVED";
						String updateRsMsg = "Update message_list set "
								+"userID = "+studentID+", item_message = '"+resultmsg+"'"
								+"Where userID = "+studentID;
						ps = conn.prepareStatement(updateRsMsg);
						ps.executeUpdate();
						showMessage();
					}

					Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
					alert1.setTitle("Approval Confirmation");
					alert1.setHeaderText(null);
					alert1.setContentText("Request approved of ID : " + studentID);
					alert1.showAndWait();

					reqShowListData1();
					reqShowListData2();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ObservableList<studentRequest> reqGetList2() {

		ObservableList<studentRequest> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM approved_list";

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			studentRequest reqDT2;

			while (rs.next()) {
				reqDT2 = new studentRequest(rs.getInt("studentID"), rs.getString("studentName"),
						rs.getInt("request_no"), rs.getString("approvedStatus"));

				listData.add(reqDT2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;
	}

	private ObservableList<studentRequest> reqListData2;

	public void reqShowListData2() {
		reqListData2 = reqGetList2();

		req2_col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		req2_col_studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
		req2_col_requestNo.setCellValueFactory(new PropertyValueFactory<>("request_no"));
		req2_col_approveStatus.setCellValueFactory(new PropertyValueFactory<>("approvedStatus"));

		table_requestListView2.setItems(reqListData2);
	}

	public Integer studentID2;
	public String studentName2;
	public Integer request_no2;
	public String approvedStatus2;
	
	public void reqSelectData2() {
		studentRequest reqData = table_requestListView2.getSelectionModel().getSelectedItem();
		int num = table_requestListView2.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}
		studentID2 = reqData.getStudentID();
		studentName2 = reqData.getStudentName();
		request_no2 = reqData.getRequest_no();
		approvedStatus2 = reqData.getApprovedStatus();
	}
	
	public String str_returnDate;
	LocalDate returnDate;
	
    @FXML
    void req2_returnDate(ActionEvent event) {
    	returnDate = returnDatePicker.getValue();
    	str_returnDate = returnDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
//    	System.out.println(str_returnDate);
    }
    
    @FXML
    void req2_doAssignDate(ActionEvent event) {
//    	System.out.println(studentID2);
    	if (studentID2.equals(null)||str_returnDate.equals(null)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Data Selection Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a student & Choose date first.");
			alert.showAndWait();
		}else {
			
			String selectuid2 = "Select * from approved_list where studentID = " + studentID2;
			try {
				ps = conn.prepareStatement(selectuid2);
				rs = ps.executeQuery();

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Approval Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Do you want assign return date for ID: " + studentID2);
				Optional<ButtonType> option = alert.showAndWait();
				if (option.get().equals(ButtonType.OK)) {

					String insertreturnDate = "INSERT INTO date_assign_table(studentID, studentName, request_no, approvedStatus, return_date) VALUES(?,?,?,?,?)";
					ps = conn.prepareStatement(insertreturnDate);
					ps.setInt(1, studentID2);
					ps.setString(2, studentName2);
					ps.setInt(3, request_no2);
					ps.setString(4, approvedStatus2);
					ps.setDate(5, Date.valueOf(returnDate));
//					ps.setDate(5, Date.valueOf(str_returnDate));
					ps.executeUpdate();

					String delapprove = "Delete from approved_list where studentID = " + studentID2;
					ps = conn.prepareStatement(delapprove);
					ps.executeUpdate();
					// update result messagebox
					if (uid == studentID2) {
						resultmsg = "Reuqest APPROVED. Collect instruments at "+str_returnDate;
						String updateRsMsg = "Update message_list set "
								+"userID = "+studentID2+", item_message = '"+resultmsg+"'"
								+"Where userID = "+studentID2;
						ps = conn.prepareStatement(updateRsMsg);
						ps.executeUpdate();
						showMessage();
					}

					Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
					alert1.setTitle("Date Assign Confirmation");
					alert1.setHeaderText(null);
					alert1.setContentText("Assigned Date for ID : " + studentID2);
					alert1.showAndWait();

					reqShowListData1();
					reqShowListData2();
					reqShowListData3();
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
    
    
	public ObservableList<studentRequest> reqGetList3() {

		ObservableList<studentRequest> listData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM date_assign_table";

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			studentRequest reqDT3;

			while (rs.next()) {
				reqDT3 = new studentRequest(rs.getInt("studentID"), rs.getString("studentName"),
						rs.getInt("request_no"), rs.getString("approvedStatus"), rs.getDate("return_date"));

				listData.add(reqDT3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listData;
	}

	private ObservableList<studentRequest> reqListData3;

	public void reqShowListData3() {
		reqListData3 = reqGetList3();

		req3_col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		req3_col_studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
		req3_col_requestNo.setCellValueFactory(new PropertyValueFactory<>("request_no"));
		req3_col_approveStatus.setCellValueFactory(new PropertyValueFactory<>("approvedStatus"));
		req3_col_returnDate.setCellValueFactory(new PropertyValueFactory<>("return_date"));

		table_requestListView3.setItems(reqListData3);
	}
	
	
	// Request section ends here
	// Inventory section starts here

	// Merges all data
	public ObservableList<instrumentData> inventoryDataList() {
		ObservableList<instrumentData> listData = FXCollections.observableArrayList();
		String sql = "SELECT * FROM inventory_table";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			instrumentData insData;

			while (rs.next()) {
				insData = new instrumentData(rs.getString("itemID"), rs.getString("itemImage"),
						rs.getString("itemName"), rs.getInt("itemQuantity"), rs.getString("itemStatus"));

				listData.add(insData);
			}

		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Data Validation Error");
			alert.setHeaderText(null);
			alert.setContentText("" + e);
			alert.showAndWait();
		}
		return listData;
	}

	// To show data on the inventory table
	private ObservableList<instrumentData> inventoryListData;

	public void inventoryShowData() {
		inventoryListData = inventoryDataList();

		inventory_col_itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
		inventory_col_itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		inventory_col_itemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
		inventory_col_itemStatus.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

		inventory_tableView.setItems(inventoryListData);
	}

	public void inventorySelectData() {
		instrumentData insData = inventory_tableView.getSelectionModel().getSelectedItem();
		int num = inventory_tableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}
		inventory_itemID.setText(insData.getItemID());
		inventory_itemName.setText(insData.getItemName());
		inventory_itemQuantity.setText(String.valueOf(insData.getItemQuantity()));
		inventory_itemStatus.setValue(insData.getItemStatus());

		myData.path = "File:" + insData.getItemImage();
		image = new Image(myData.path, 120, 120, false, true);
		inventory_imageView.setImage(image);
	}

	@FXML
	void doImportInventoryTable(ActionEvent event) {
		FileChooser openFile = new FileChooser();
		openFile.getExtensionFilters().add(new ExtensionFilter("Open image file", "*png", "*jpg", "*jpeg"));
		File file = openFile.showOpenDialog(main_form.getScene().getWindow());
		if (file != null) {
			myData.path = file.getAbsolutePath();
			image = new Image(file.toURI().toString(), 120, 120, false, true);
			inventory_imageView.setImage(image);
		}
	}

	@FXML
	void doInsertInventoryTable(ActionEvent event) {
		String itemID = inventory_itemID.getText();
		String itemName = inventory_itemName.getText();
		String itemQuantity = inventory_itemQuantity.getText();
		int item_Quantity = Integer.parseInt(itemQuantity);
		String itemStatus = inventory_itemStatus.getValue();

		if (itemID.isEmpty() || itemName.isEmpty() || itemQuantity.isEmpty() || itemStatus == null
				|| inventory_imageView.getImage() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Empty value found");
			alert.setHeaderText(null);
			alert.setContentText("fill all the fields with valid info.");
			alert.showAndWait();
		} else {
			String checkItemID = "select itemID from inventory_table where itemID = '" + itemID + "';";
			try {
				ps = conn.prepareStatement(checkItemID);
				rs = ps.executeQuery();

				if (rs.next()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("ERROR: Value already exists ");
					alert.setHeaderText(null);
					alert.setContentText(itemID + " already exists in the database.");
					alert.showAndWait();
				} else {
					String insertUrl = "insert into inventory_table(itemID, itemImage, itemName, itemQuantity, itemStatus) values(?,?,?,?,?);";
					ps = conn.prepareStatement(insertUrl);
					ps.setString(1, itemID);

					String path = myData.path;
					path = myData.path.replace("\\", "\\\\");

					ps.setString(2, path);
					ps.setString(3, itemName);
					ps.setInt(4, item_Quantity);
					ps.setString(5, itemStatus);

					ps.executeUpdate();// returns nothing

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Insert into Inventory");
					alert.setHeaderText(null);
					alert.setContentText("Data successfully added in the table.");
					alert.showAndWait();

					inventory_itemID.clear();
					inventory_itemName.clear();
					inventory_itemQuantity.clear();
					inventory_itemStatus.getSelectionModel().clearSelection();
					inventory_imageView.setImage(null);

					inventoryShowData();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void doUpdateInventoryTable(ActionEvent event) {
		String itemID = inventory_itemID.getText();
		String itemName = inventory_itemName.getText();
		String itemQuantity = inventory_itemQuantity.getText();
		int item_Quantity = Integer.parseInt(itemQuantity);
		String itemStatus = inventory_itemStatus.getValue();

		if (itemID.isEmpty() || itemName.isEmpty() || itemQuantity.isEmpty() || itemStatus.isEmpty()
				|| inventory_imageView.getImage() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Empty value found");
			alert.setHeaderText(null);
			alert.setContentText("fill all the fields with valid info.");
			alert.showAndWait();
		} else {
			String path = myData.path;
			// myData.path e "File:C\\....." esob chole ase. Tai age seta remove kori
			String deleteText = "File:";
			path = path.replace(deleteText, "");

			path = path.replace("\\", "\\\\");

			String updateQuery = "UPDATE inventory_table SET " + "itemImage = '" + path + "', " + "itemName = '"
					+ itemName + "', " + "itemQuantity = " + item_Quantity + ", " + "itemStatus = '" + itemStatus
					+ "' WHERE itemID = '" + itemID + "'";

			try {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Update");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to UPDATE Item ID: " + itemID + "?");
				Optional<ButtonType> option = alert.showAndWait();

				if (option.get().equals(ButtonType.OK)) {
					ps = conn.prepareStatement(updateQuery);
					ps.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("UPDATE Inventory Data");
					alert.setHeaderText(null);
					alert.setContentText(itemID + " Successfully Updated.");
					alert.showAndWait();

					doClearInventoryFields();
					inventoryShowData();
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Cancel Update");
					alert.setHeaderText(null);
					alert.setContentText("Update Cancelled.");
					alert.showAndWait();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void doClearInventoryFields() {
		inventory_itemID.clear();
		inventory_itemName.clear();
		inventory_itemQuantity.clear();
		inventory_itemStatus.getSelectionModel().clearSelection();
		inventory_imageView.setImage(null);
	}

	@FXML
	void doClearInventoryTable(ActionEvent event) {
		inventory_itemID.clear();
		inventory_itemName.clear();
		inventory_itemQuantity.clear();
		inventory_itemStatus.getSelectionModel().clearSelection();
		inventory_imageView.setImage(null);
	}

	@FXML
	void doDeleteInventoryTable(ActionEvent event) {
		String itemID = inventory_itemID.getText();

		if (itemID == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("DELETE from Inventory");
			alert.setHeaderText(null);
			alert.setContentText("Please fill at least Item ID field");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("DELETE Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to DELETE Item ID: " + itemID + "?");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {
				String deleteData = "DELETE FROM inventory_table WHERE itemID = '" + itemID + "'";
				try {
					ps = conn.prepareStatement(deleteData);
					ps.executeUpdate();

					Alert alert1 = new Alert(AlertType.ERROR);
					alert1.setTitle("DELETED from Data Table");
					alert1.setHeaderText(null);
					alert1.setContentText("All information of " + itemID + " has been successfully DELETED");
					alert1.showAndWait();

					inventoryShowData();

					// Clear all the fields
					inventory_itemID.clear();
					inventory_itemName.clear();
					inventory_itemQuantity.clear();
					inventory_itemStatus.getSelectionModel().clearSelection();
					inventory_imageView.setImage(null);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	void doLogOut(ActionEvent event) {
		try {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Log out from this page");
			alert.setHeaderText(null);
			alert.setContentText("Do you want to log out?");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {
//    			Stage primaryStage = new Stage();
//    			main.start(primaryStage);Main main pass kore erpor try korbo

				logOutBtn.getScene().getWindow().hide();

				Parent root = FXMLLoader.load(getClass().getResource("regifxml.fxml"));
				Scene scene = new Scene(root);
				Stage primaryStage = new Stage();
				primaryStage.setTitle("LabAssist : Lab Management System");

				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			conn = javaConnect.connectDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
		inventory_itemStatus.setItems(FXCollections.observableArrayList("AVAILABLE", "UNAVAILABLE"));
		
		showMessage();
		
		menuShowListData();
		menuSelectData();
		menuDisplayCard();

		reqShowListData1();
		reqSelectData1();
		reqShowListData2();
		reqSelectData2();
		reqShowListData3();

		inventoryShowData();
		inventorySelectData();
		doClearInventoryFields();
	}
}
