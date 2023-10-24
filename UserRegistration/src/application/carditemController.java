package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class carditemController implements Initializable {

	@FXML
	private AnchorPane card_form;

	@FXML
	private Button card_itemAddBtn;

	@FXML
	private Label card_itemName;

	@FXML
	private Label card_itemQuantity;

	@FXML
	private Spinner<Integer> card_itemSpinner;

	@FXML
	private ImageView card_item_imageView;

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	private SpinnerValueFactory<Integer> spin;

	private String item_image;

	private Image image;

	private String itemID;

	instrumentData insData;

	public static int uid=0;

	public void getUserID(String lguname) {
		String uname = lguname;
		String uidQuery = "select userID from user_info where Username ='" + uname + "'";
		try {
			ps = conn.prepareStatement(uidQuery);
			rs = ps.executeQuery();
			rs.next();
			uid = rs.getInt("userID");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setData(instrumentData insData) {
		this.insData = insData;

		itemID = insData.getItemID();
		item_image = insData.getItemImage();
		card_itemName.setText(insData.getItemName());

		card_itemQuantity.setText(String.valueOf(insData.getItemQuantity()));
		String path = "File:" + insData.getItemImage();
		image = new Image(path, 190, 95, false, true);
		card_item_imageView.setImage(image);
	}

	private int qty;

	public void setQuantity() {
		spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
		card_itemSpinner.setValueFactory(spin);
	}

	public void doAddCardItem(ActionEvent event){
		String uidChecker = "select userID from menu_table where userID = " + uid;
		try {
			ps = conn.prepareStatement(uidChecker);
			rs = ps.executeQuery();
			if(rs.next()) {
				int ex_uid = rs.getInt("userID");
				if (uid == ex_uid) {
					Alert alert1 = new Alert(Alert.AlertType.ERROR);
					alert1.setTitle("Pending Request");
					alert1.setHeaderText(null);
					alert1.setContentText("Your request is pending. You can't add item to the list at this moment.");
					alert1.showAndWait();
				}	
			}else {
				qty = card_itemSpinner.getValue();
				String check = "";
				String checkAvailable = "SELECT itemStatus FROM inventory_table WHERE itemID = '" + itemID + "'";
				
				ps = conn.prepareStatement(checkAvailable);
				rs = ps.executeQuery();

				if (rs.next()) {
					check = rs.getString("itemStatus");
					if (!check.equals("AVAILABLE") || qty == 0) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Item Selection Error");
						alert.setHeaderText(null);
						alert.setContentText("Item cannot be added.");
						alert.showAndWait();
					} else {
						if (qty > (insData.getItemQuantity() - 5)) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Item Selection Error");
							alert.setHeaderText(null);
							alert.setContentText("You can select MAX '" + (insData.getItemQuantity() - 5) + "' pcs.");
							alert.showAndWait();
						}
						else {

							String path = insData.getItemImage();
							path = path.replace("\\", "\\\\");
	
							String insertData = "INSERT INTO temp_menu_table"
									+ "(userID, itemID, itemName, itemQuantity) " + "VALUES(?,?,?,?)";
	
							ps = conn.prepareStatement(insertData);
	
							ps.setInt(1, uid);
							ps.setString(2, itemID);
							ps.setString(3, card_itemName.getText());
							ps.setInt(4, qty);
	
							ps.executeUpdate();
	
							card_itemQuantity.setText(String.valueOf(insData.getItemQuantity() - qty));
	
							String updateQntty = "UPDATE inventory_table SET " + "itemImage = '" + path + "', "
									+ "itemName = '" + card_itemName.getText() + "', " + "itemQuantity = "
									+ (insData.getItemQuantity() - qty) + ", " + "itemStatus = '" + check
									+ "' WHERE itemID = '" + itemID + "'";
	
							ps = conn.prepareStatement(updateQntty);
							ps.executeUpdate();
	
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("Item Selection");
							alert.setHeaderText(null);
							alert.setContentText(qty + " pcs " + card_itemName.getText() + " added to the list");
							alert.showAndWait();
						}
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			conn = javaConnect.connectDB();
			setQuantity();
//			card_itemAddBtn.setOnAction(this::doAddCardItem);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
