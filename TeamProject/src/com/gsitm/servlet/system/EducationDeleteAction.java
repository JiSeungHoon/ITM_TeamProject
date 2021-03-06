package com.gsitm.servlet.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gsitm.dao.RoomDAO;
import com.gsitm.dto.EmployeesDTO;


@WebServlet("/education/deleteaction")
public class EducationDeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//인코딩 설정
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String roomId = request.getParameter("roomId");
		String password = request.getParameter("deletePwd");
		HttpSession session = request.getSession();
		EmployeesDTO employDTO = (EmployeesDTO) session.getAttribute("Employee");
		String employPwd = employDTO.getPassword();

		PrintWriter out = response.getWriter();

		//권한체크
		if(!("system".equals(request.getSession().getAttribute("Authority")))){
			out.println("<script type='text/javascript'>alert('권한이 없습니다.');");
			out.println("history.back();</script>"); //
			return;
		}

		//회의실이 예약이 되어 있는지 체크
		List<String> regStateList = (new RoomDAO()).useRoomCheck(roomId);

		if(regStateList.size()>0){
			out.println("<script type='text/javascript'>alert('회의실이 예약이 되어 있어 삭제할 수 없습니다.');");
			out.println("location.href='../conference/manageaction';</script>"); //
			System.out.println("회의실이 예약 되어 있음");
			return;
		}

		System.out.println("직원 비밀번호"+employDTO.getPassword());
		if(password.equals(employPwd)){
			try{
				(new RoomDAO()).update(roomId, "disable");
				out.println("<script type='text/javascript'>alert('교육실 미사용 처리가 완료 되었습니다.');");
				out.println("location.href='../education/manageaction';</script>"); //
				System.out.println("교육실 미사용 성공");
			}catch(Exception e){
				out.println("<script type='text/javascript'>alert('교육실 미사용 처리가 실패하였습니다.\\n" +
						e.getMessage().replace("'", "").replace("\n", "") + "');");
				out.println("location.href='../education/manageaction';</script>");
			}

		}else{
			out.println("<script type='text/javascript'>alert('비밀번호가 일치하지않아 처리 되지 않았습니다.');");
			out.println("location.href='../education/manageaction';</script>"); //
			System.out.println("교육실 미사용 실패");
		}
		//response.sendRedirect("../education/manageaction");
	}
}
