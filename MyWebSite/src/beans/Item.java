package beans;

public class Item {
		private int id;
		private String itemName;
		private String itemDetail;
		private String type;
		private int price;
		private String fileName;
		private int count;
		private int countPrice;
		private int totalPrice;
		private String buyDate;
		private String buyTime;
		private String deliveryMethod;
		private int deliPrice;
		private int soldNum;

	public Item() {
	}

	public Item(int setId, String setItemName, String setItemDetail, int setPrice, String setFileName, int setCount, int setCountPrice) {
		this.id = setId;
		this.itemName = setItemName;
		this.itemDetail = setItemDetail;
		this.price = setPrice;
		this.fileName = setFileName;
		this.count = setCount;
		this.countPrice = setCountPrice;
	}

	public Item(int setId, String setItemName, String setItemDetail, String setType, int setPrice, int setSoldNum, String setFileName) {
		this.id = setId;
		this.itemName = setItemName;
		this.itemDetail = setItemDetail;
		this.type = setType;
		this.price = setPrice;
		this.soldNum = setSoldNum;
		this.fileName = setFileName;
	}

	public Item(int setId, String setItemName, String setItemDetail, int setPrice, int setSoldNum, String setFileName) {
		this.id = setId;
		this.itemName = setItemName;
		this.itemDetail = setItemDetail;
		this.price = setPrice;
		this.soldNum = setSoldNum;
		this.fileName = setFileName;
	}

	public Item(int setId, String setName, String setType, int setPrice) {
		this.id = setId;
		this.itemName = setName;
		this.type = setType;
		this.price = setPrice;
	}

	public Item(int setTotalPrice, String setBuyDate, String setBuyTime, String setDeliveryMethod, int setDeliPrice) {
		this.totalPrice = setTotalPrice;
		this.buyDate = setBuyDate;
		this.buyTime = setBuyTime;
		this.deliveryMethod = setDeliveryMethod;
		this.deliPrice = setDeliPrice;
	}

	public Item(int setSoldNum, int setId) {
		this.soldNum = setSoldNum;
		this.id = setId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDetail() {
		return itemDetail;
	}
	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCountPrice() {
		return countPrice;
	}
	public void setCountPrice(int countPrice) {
		this.countPrice = countPrice;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public int getDeliPrice() {
		return deliPrice;
	}
	public void setDeliPrice(int deliPrice) {
		this.deliPrice = deliPrice;
	}
	public int getSoldNum() {
		return soldNum;
	}
	public void setSoldNum(int soldNum) {
		this.soldNum = soldNum;
	}

}
