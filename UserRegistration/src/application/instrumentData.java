package application;

public class instrumentData {
	private String itemID;
	private String itemImage;
	private String itemName;
	private Integer itemQuantity;
	private String itemStatus;
	
	public instrumentData(String itemID, String itemImage, String itemName, Integer itemQuantity, String itemStatus) {
		this.itemID = itemID;
		this.itemImage = itemImage;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.itemStatus = itemStatus;
	}
	public instrumentData(String itemID, String itemImage, String itemName, Integer itemQuantity) {
		this.itemID = itemID;
		this.itemImage = itemImage;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
	}
	public instrumentData(String itemID, String itemName, Integer itemQuantity) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
	}
	
	//getter method e camelCase use korte hbe. getitemID likhle hbe na.
	public String getItemID() {
		return itemID;
	}
	public String getItemImage() {
		return itemImage;
	}
	public String getItemName() {
		return itemName;
	}
	public Integer getItemQuantity() {
		return itemQuantity;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	
}
