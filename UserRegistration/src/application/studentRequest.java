package application;

import java.sql.Date;

public class studentRequest {
	private Integer studentID;
	private String studentName;
	private String Dept;
	private String Session;
	private Integer request_no;
	private String approvedStatus;
	private Date return_date;
	
	public studentRequest(Integer studentID, String studentName, String Dept, String Session, Integer request_no,String approvedStatus, Date return_date) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.Dept = Dept;
		this.Session = Session;
		this.request_no = request_no;
		this.approvedStatus = approvedStatus;
		this.return_date = return_date;
	}
	
	public studentRequest(Integer studentID, String studentName, Integer request_no, String approvedStatus) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.request_no = request_no;
		this.approvedStatus = approvedStatus;
	}
	public studentRequest(Integer studentID, String studentName, Integer request_no, String approvedStatus, Date return_date) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.request_no = request_no;
		this.approvedStatus = approvedStatus;
		this.return_date = return_date;
	}
	public studentRequest(Integer studentID, String studentName, String Dept, String Session, Integer request_no) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.Dept = Dept;
		this.Session = Session;
		this.request_no = request_no;
	}
	
	public Integer getStudentID() {
		return studentID;
	}
	public String getStudentName() {
		return studentName;
	}
	public String getDept() {
		return Dept;
	}
	public String getSession() {
		return Session;
	}
	public Integer getRequest_no() {
		return request_no;
	}
	public String getApprovedStatus() {
		return approvedStatus;
	}
	public Date getReturn_date() {
		return return_date;
	}
}
