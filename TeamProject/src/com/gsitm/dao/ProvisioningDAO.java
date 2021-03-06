package com.gsitm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.gsitm.common.ConstString;
import com.gsitm.dto.DepartmentsDTO;
import com.gsitm.dto.EmployeesDTO;

public class ProvisioningDAO {

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(ConstString.JDBC_DRIVER);
			conn = DriverManager.getConnection(ConstString.DB_SERVER, ConstString.DB_USER, ConstString.DB_PASSWD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 *
	 * @작성일  : 2017. 5. 10.
	 * @작성자  : 김준호
	 * @기능    : 해당 부서의 모든 직원들의 데이터를 저장하는 메소드
	 * @메소드명 : selectDepartmentsEmployees
	 * @param : dep_id (부서아이디)
	 * @return : employeesList (해당 부서의 직원리스트)
	 */
	public List<EmployeesDTO> selectDepartmentsEmployees(String dep_id){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EmployeesDTO> employeesList = new ArrayList<EmployeesDTO>();

		//파라미터로 받은 부서아이디에 속한 직원을 찾는 쿼리
		String sql = "select * from EMPLOYEES where DEP_ID like ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dep_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EmployeesDTO eDto = new EmployeesDTO();
				eDto.setEmpId(rs.getString("EMP_ID"));
				eDto.setPassword(rs.getString("PASSWORD"));
				eDto.setEmpName(rs.getString("EMP_NAME"));
				eDto.setCaptionDepId(rs.getString("CAPTAIN_DEP_ID"));
				eDto.setAuthority(rs.getString("AUTHORITY"));
				eDto.setPositionName(rs.getString("POSITION_NAME"));
				eDto.setDepId(rs.getString("DEP_ID"));
				eDto.setDepName(rs.getString("DEP_NAME"));
				eDto.setEmpPhoto(rs.getString("EMP_PHOTO"));
				eDto.setEmail(rs.getString("EMAIL"));
				employeesList.add(eDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
		System.out.println("employeesList : " + employeesList.size());
		return employeesList;
	}
	/**
	 *
	 * @작성일  : 2017. 5. 11.
	 * @작성자  : 김준호
	 * @기능    : 교육실 관리자를 조회하는 메소드
	 * @메소드명 : selectEducationAdmin
	 * @return : eDto (교육실 관리자 정보)
	 */
	public EmployeesDTO selectEducationAdmin(){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeesDTO eDto = new EmployeesDTO();

		//교육실 관리자를 조회하는 쿼리
		String sql = "select * from EMPLOYEES where AUTHORITY = 'education'";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eDto.setEmpId(rs.getString("EMP_ID"));
				eDto.setPassword(rs.getString("PASSWORD"));
				eDto.setEmpName(rs.getString("EMP_NAME"));
				eDto.setCaptionDepId(rs.getString("CAPTAIN_DEP_ID"));
				eDto.setAuthority(rs.getString("AUTHORITY"));
				eDto.setPositionName(rs.getString("POSITION_NAME"));
				eDto.setDepId(rs.getString("DEP_ID"));
				eDto.setDepName(rs.getString("DEP_NAME"));
				eDto.setEmpPhoto(rs.getString("EMP_PHOTO"));
				eDto.setEmail(rs.getString("EMAIL"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
		return eDto;
	}
	/**
	 *
	 * @작성일  : 2017. 5. 11.
	 * @작성자  : 김준호
	 * @기능    : 회의실 관리자를 조회하는 메소드
	 * @메소드명 : selectConferenceAdmin
	 * @return : eDto (회의실 관리자 정보)
	 */
	public EmployeesDTO selectConferenceAdmin(){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeesDTO eDto = new EmployeesDTO();

		//회의실 관리자를 조회하는 쿼리
		String sql = "select * from EMPLOYEES where AUTHORITY = 'conference'";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eDto.setEmpId(rs.getString("EMP_ID"));
				eDto.setPassword(rs.getString("PASSWORD"));
				eDto.setEmpName(rs.getString("EMP_NAME"));
				eDto.setCaptionDepId(rs.getString("CAPTAIN_DEP_ID"));
				eDto.setAuthority(rs.getString("AUTHORITY"));
				eDto.setPositionName(rs.getString("POSITION_NAME"));
				eDto.setDepId(rs.getString("DEP_ID"));
				eDto.setDepName(rs.getString("DEP_NAME"));
				eDto.setEmpPhoto(rs.getString("EMP_PHOTO"));
				eDto.setEmail(rs.getString("EMAIL"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
		return eDto;
	}
	/**
	 *
	 * @작성일  : 2017. 5. 11.
	 * @작성자  : 김준호
	 * @기능    : 시스템 관리자를 조회하는 메소드
	 * @메소드명 : selectSystemAdmin
	 * @return : eDto (시스템 관리자 정보)
	 */
	public EmployeesDTO selectSystemAdmin(){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeesDTO eDto = new EmployeesDTO();

		//시스템 관리자를 조회하는 쿼리
		String sql = "select * from EMPLOYEES where AUTHORITY = 'system'";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				eDto.setEmpId(rs.getString("EMP_ID"));
				eDto.setPassword(rs.getString("PASSWORD"));
				eDto.setEmpName(rs.getString("EMP_NAME"));
				eDto.setCaptionDepId(rs.getString("CAPTAIN_DEP_ID"));
				eDto.setAuthority(rs.getString("AUTHORITY"));
				eDto.setPositionName(rs.getString("POSITION_NAME"));
				eDto.setDepId(rs.getString("DEP_ID"));
				eDto.setDepName(rs.getString("DEP_NAME"));
				eDto.setEmpPhoto(rs.getString("EMP_PHOTO"));
				eDto.setEmail(rs.getString("EMAIL"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
		return eDto;
	}
	/**
	 *
	 * @작성일  : 2017. 5. 11.
	 * @작성자  : 김준호
	 * @기능    : 기존 회의실 관리자의 권한을 일반 사용자 권한으로 변경
	 * @메소드명 : updateOldConferenceAdmin
	 * @param : oldConfAdminEmpId (기존 회의실 관리자 ID)
	 */
	public void updateOldConferenceAdmin(String oldConfAdminEmpId){
		Connection con = getConnection();
		PreparedStatement pstmt = null;

		//기존의 회의실 관리자를 일반사용자 권한으로 업데이트하는 쿼리
		String sql = "update EMPLOYEES set AUTHORITY = 'user' where EMP_ID = ? and not(AUTHORITY like 'system')";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, oldConfAdminEmpId);
			int result = pstmt.executeUpdate();
			if(result == 0)
				throw new RuntimeException("권한을 변경할 수 없는 사용자입니다.");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
	}
	/**
	 *
	 * @작성일  : 2017. 5. 11.
	 * @작성자  : 김준호
	 * @기능    : 기존 교육실 관리자의 권한을 일반 사용자 권한으로 변경
	 * @메소드명 : updateOldEducationAdmin
	 * @param : oldConfAdminEmpId (기존 교육실 관리자 ID)
	 */
	public void updateOldEducationAdmin(String oldEduAdminEmpId){
		Connection con = getConnection();
		PreparedStatement pstmt = null;

		//기존의 교육실 관리자를 일반사용자 권한으로 업데이트하는 쿼리
		String sql = "update EMPLOYEES set AUTHORITY = 'user' where EMP_ID = ? and not(AUTHORITY like 'system')";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, oldEduAdminEmpId);
			int result = pstmt.executeUpdate();
			if(result == 0)
				throw new RuntimeException("권한을 변경할 수 없는 사용자입니다.");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
	}
	/**
	 *
	 * @작성일  : 2017. 5. 11.
	 * @작성자  : 김준호
	 * @기능    : 새로 지정한 사원을 교육실 관리자로 변경하는 메소드
	 * @메소드명 : updateNewEducationAdmin
	 * @param : newEduAdminEmpId (교육실 관리자로 새로 지정할 사원 ID)
	 * @return : eDto
	 */
	public void updateNewEducationAdmin(String newEduAdminEmpId){
		Connection con = getConnection();
		PreparedStatement pstmt = null;

		//파라미터로 받은 직원을 교육실 관리자로 업데이트하는 쿼리
		String sql = "update EMPLOYEES set AUTHORITY = 'education' where EMP_ID = ? and AUTHORITY like 'user'";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newEduAdminEmpId);
			int result = pstmt.executeUpdate();
			if(result == 0)
				throw new RuntimeException("권한을 변경할 수 없는 사용자입니다.");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
	}
	/**
	 *
	 * @작성일  : 2017. 5. 11.
	 * @작성자  : 김준호
	 * @기능    : 새로 지정한 사원을 회의실 관리자로 변경하는 메소드
	 * @메소드명 : updateNewConferenceAdmin
	 * @param newConfAdminEmpId
	 * @return : eDto
	 */
	public void updateNewConferenceAdmin(String newConfAdminEmpId){
		Connection con = getConnection();
		PreparedStatement pstmt = null;

		//파라미터로 받은 직원을 회의실 관리자로 업데이트하는 쿼리
		String sql = "update EMPLOYEES set AUTHORITY = 'conference' where EMP_ID = ? and AUTHORITY like 'user'";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newConfAdminEmpId);
			int result = pstmt.executeUpdate();
			if(result == 0)
				throw new RuntimeException("권한을 변경할 수 없는 사용자입니다.");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			ProvisioningDAO.close(con, pstmt);
		}
	}

	private static void close(Connection con, PreparedStatement pstmt) {
		try {
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
