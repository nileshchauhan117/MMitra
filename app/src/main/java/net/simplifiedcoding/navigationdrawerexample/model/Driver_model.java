package net.simplifiedcoding.navigationdrawerexample.model;

public class Driver_model {

	private String truck_subcategory;
	private String truck_body_desc;
	private String truck_body_id;
	private String emirates_id;
	private String vehicle_reg_no;
	private String vehicle_reg_date;
	private String License_no;
	private String driver_id;
	private String reg_date;
	private String Name;
	private String mobile_no;
	private String additional_mobile_no;
	private String driver_photo;
	private String IsOnDuty;
	private String isfree;
	private String active_flag;
	private String created_date;
	private String country_code;
	private String created_by_name;
	private String truck_id;
	private String gps;
	private String dob;

	public Driver_model()
	{
		// TODO Auto-generated constructor stub
	}

	public Driver_model(String truck_subcategory , String truck_body_desc, String truck_body_id, String emirates_id, String vehicle_reg_no,
                        String vehicle_reg_date, String License_no, String driver_id, String reg_date, String Name, String mobile_no,
                        String additional_mobile_no, String driver_photo, String IsOnDuty, String isfree, String active_flag,
                        String created_date, String country_code, String created_by_name, String truck_id,String gps, String dob) {
		super();
		this.truck_subcategory = truck_subcategory;
		this.truck_body_desc = truck_body_desc;
		this.truck_body_id = truck_body_id;
		this.emirates_id = emirates_id;
		this.vehicle_reg_no = vehicle_reg_no;
		this.vehicle_reg_date = vehicle_reg_date;
		this.License_no = License_no;
		this.driver_id = driver_id;
		this.reg_date = reg_date;
		this.Name = Name;
		this.mobile_no = mobile_no;
		this.additional_mobile_no = additional_mobile_no;
		this.driver_photo = driver_photo;
		this.IsOnDuty = IsOnDuty;
		this.isfree = isfree;
		this.active_flag = active_flag;
		this.created_date = created_date;
		this.country_code = country_code;
		this.created_by_name = created_by_name;
		this.truck_id=truck_id;
		this.gps=gps;
		this.dob = dob;

	}

	public String gettruck_subcategory() {
		return truck_subcategory;
	}
	public void settruck_subcategory(String truck_subcategory) {
		this.truck_subcategory = truck_subcategory;
	}


	public String gettruck_body_desc() {
		return truck_body_desc;
	}
	public void settruck_body_desc(String truck_body_desc) {
		this.truck_body_desc = truck_body_desc;
	}

	public String gettruckid() {
		return truck_id;
	}
	public void settruckid(String truck_id) {
		this.truck_id = truck_id;
	}

	public String getgps() {
		return gps;
	}
	public void setgps(String truck_id) {
		this.gps = gps;
	}

	public String gettruck_body_id() {
		return truck_body_id;
	}
	public void settruck_body_id(String truck_body_id) {
		this.truck_body_id = truck_body_id;
	}

	public String getemirates_id() {
		return emirates_id;
	}
	public void setemirates_id(String emirates_id) {
		this.emirates_id = emirates_id;
	}

	public String getvehicle_reg_no() {
		return vehicle_reg_no;
	}
	public void setvehicle_reg_no(String vehicle_reg_no) {
		this.vehicle_reg_no = vehicle_reg_no;
	}

	public String getvehicle_reg_date() {
		return vehicle_reg_date;
	}
	public void setvehicle_reg_date(String vehicle_reg_date) {
		this.vehicle_reg_date = vehicle_reg_date;
	}

	public String getLicense_no() {
		return License_no;
	}
	public void setLicense_no(String License_no) {
		this.License_no = License_no;
	}

	public String getdriver_id() {
		return driver_id;
	}
	public void setdriver_id(String driver_id) {
		this.driver_id = driver_id;
	}


	public String getreg_date() {
		return reg_date;
	}
	public void setreg_date(String reg_date) {
		this.reg_date = reg_date;
	}


	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name;
	}



	public String getmobile_no() {
		return mobile_no;
	}
	public void setmobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getadditional_mobile_no() {
		return additional_mobile_no;
	}
	public void setadditional_mobile_no(String additional_mobile_no) {
		this.additional_mobile_no = additional_mobile_no;
	}


	public String getdriver_photo() {
		return driver_photo;
	}
	public void setdriver_photo(String driver_photo) {
		this.driver_photo = driver_photo;
	}


	public String getIsOnDuty() {
		return IsOnDuty;
	}
	public void setIsOnDuty(String IsOnDuty) {
		this.IsOnDuty = IsOnDuty;
	}

	public String getisfree() {
		return isfree;
	}
	public void setisfree(String isfree) {
		this.isfree = isfree;
	}

	public String getactive_flag() {
		return active_flag;
	}
	public void setactive_flag(String active_flag) {
		this.active_flag = active_flag;
	}

	public String getcreated_date() {
		return created_date;
	}
	public void setcreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getcountry_code() {
		return country_code;
	}
	public void setcountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getcreated_by_name() {
		return created_by_name;
	}
	public void setcreated_by_name(String created_by_name) {
		this.created_by_name = created_by_name;
	}

	public String getdob() {
		return dob;
	}
	public void setdob(String dob) {
		this.dob = dob;
	}

}
