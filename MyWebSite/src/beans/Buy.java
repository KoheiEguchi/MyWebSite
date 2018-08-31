package beans;

import java.io.Serializable;

public class Buy  implements Serializable {
	private int id;
	private int buyerId;
	private int buyId;
	private int itemId;
	private int count;
	private int allPrice;
	private int totalPrice;
	private String deliveryMethod;
	private int deliPrice;
	private String deliAddress;
	private String buyDate;
	private String buyTime;
	private String name;
	private String deliConfirm;

	public Buy() {
	}

	public Buy(int setTotalPrice, String setBuyDate, String setBuyTime) {
		this.totalPrice = setTotalPrice;
		this.buyDate = setBuyDate;
		this.buyTime = setBuyTime;
	}

	public Buy(int setBuyerId, int setTotalPrice, String setDeliveryMethod, String setBuyDate, String setBuyTime, int setBuyId, String setDeliConfirm) {
		this.buyerId = setBuyerId;
		this.totalPrice = setTotalPrice;
		this.deliveryMethod = setDeliveryMethod;
		this.buyDate = setBuyDate;
		this.buyTime = setBuyTime;
		this.buyId = setBuyId;
		this.deliConfirm = setDeliConfirm;
	}

	public Buy(int setBuyerId, int setBuyId, int setTotalPrice, String setName) {
		this.buyerId = setBuyerId;
		this.buyId = setBuyId;
		this.totalPrice = setTotalPrice;
		this.name = setName;
	}

	public Buy(int setId, int setBuyerId, int setBuyId, int setItemId, int setCount, int setTotalPrice, String setDeliveryMethod) {
		this.id = setId;
		this.buyerId = setBuyerId;
		this.buyId = setBuyId;
		this.itemId = setItemId;
		this.count = setCount;
		this.totalPrice = setTotalPrice;
		this.deliveryMethod = setDeliveryMethod;
	}

	public Buy(int setId) {
		this.id = setId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public int getBuyId() {
		return buyId;
	}
	public void setBuyId(int buyId) {
		this.buyId = buyId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(int allPrice) {
		this.allPrice = allPrice;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
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
	public String getDeliAddress() {
		return deliAddress;
	}
	public void setDeliAddress(String deliAddress) {
		this.deliAddress = deliAddress;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeliConfirm() {
		return deliConfirm;
	}
	public void setDeliConfirm(String deliConfirm) {
		this.deliConfirm = deliConfirm;
	}

}
