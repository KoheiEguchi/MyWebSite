package beans;

public class User {
	private int id;
	private String loginId;
	private String password;
	private String userName;
	private String address;
	private String fileName;
	private String createDate;
	private String createTime;
	private String updateDate;
	private String updateTime;

	public User() {
		this.loginId = "";
		this.password = "";
		this.userName = "";
		this.address = "";
	}

	public User(int setId, String setUserName, String address) {
		this.id = setId;
		this.userName = setUserName;
		this.address = address;
	}

	public User(int setId, String setUserName, String setAddress, String setLoginId,
			String setCreateDate, String setCreateTime, String setUpdateDate, String setUpdateTime) {
		this.id = setId;
		this.userName = setUserName;
		this.address = setAddress;
		this.loginId = setLoginId;
		this.createDate = setCreateDate;
		this.createTime = setCreateTime;
		this.updateDate = setUpdateDate;
		this.updateTime = setUpdateTime;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
