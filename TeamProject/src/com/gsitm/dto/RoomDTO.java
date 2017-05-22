package com.gsitm.dto;

/**
 *
 * @프로그램   : RoomDTO.java
 * @작성일     : 2017. 5. 4.
 * @작성자     : 지승훈
 * @기능       : 회의실/교육실 정보 테이블을 위한 DTO
 * @버전       : 1.0
 *
 */
public class RoomDTO {
	private String roomId;		 //회의실_교육실ID
	private String roomName;	 //회의실이름
	private String position;	 //회의실 위치
	private String buildName;    //건물명
	private int    fee;			 //사용요금
	private String roomSize;	 //회의실 크기
	private int numberOfEmp;	 //수용인원
	private String netFlag;	 	 //NW사용여부
	private String empId;		 //직원ID

	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String bulidName) {
		this.buildName = bulidName;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}
	public int getNumberOfEmp() {
		return numberOfEmp;
	}
	public void setNumberOfEmp(int numberOfEmp) {
		this.numberOfEmp = numberOfEmp;
	}
	public String getNetFlag() {
		return netFlag;
	}
	public void setNetFlag(String netFlag) {
		this.netFlag = netFlag;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
}