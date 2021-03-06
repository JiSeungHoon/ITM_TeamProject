package com.gsitm.servlet.sign;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gsitm.dao.EmployeesDAO;
import com.gsitm.dao.ReservationDAO;
import com.gsitm.dao.RoomDAO;
import com.gsitm.dto.EmployeesDTO;
import com.gsitm.dto.ReservationDTO;
import com.gsitm.dto.RoomDTO;

/**
 *
 * @프로그램   : SignAction.java
 * @작성일     : 2017. 5. 18.
 * @작성자     : 지승훈
 * @기능       : 예약한 사용자들의 결재를 하기위한 리스트 제공
 * @버전       : 1.0
 *
 */
@WebServlet("/sign")
public class SignAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//인코딩 설정
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		//로그인 체크
        if(request.getSession().getAttribute("Employee") == null){
          response.sendRedirect( request.getContextPath() + "/login");
          return;
        }
        
        //권한체크
        if(((EmployeesDTO)request.getSession().getAttribute("Employee")).getCaptionDepId() == null){
        	out.println("<script type='text/javascript'>alert('권한이 없습니다.');");
			out.println("history.back();</script>");
	    	return;
        }
        
        request.setCharacterEncoding("UTF-8");
        String startDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
		String finishDate =  (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        String roomId = request.getParameter("roomDivision");
        System.out.println("roomId : " + roomId);
        if(roomId==null || roomId.equals("default")){
        	roomId = "%%";
        }else if(roomId.equals("ER")){
        	roomId = "ER%";
        }else if(roomId.equals("CR")){
        	roomId = "CR%";
        }

        List<ReservationDTO> reservationCrList = (new ReservationDAO()).select(startDate, finishDate, roomId,"결재대기");
		List<RoomDTO> roomList = new LinkedList<RoomDTO>();
		List<String> empNameList = new LinkedList<String>();

		for(ReservationDTO res : reservationCrList){
			RoomDTO room = (new RoomDAO()).selectRoomInfo(res.getRoomId());
			EmployeesDTO empName = (new EmployeesDAO()).login(res.getEmpId());
			roomList.add(room);
			empNameList.add(empName.getEmpName());
		}

		HttpSession session = request.getSession();

		request.setAttribute("reservationList",reservationCrList );
		request.setAttribute("roomList", roomList);
		request.setAttribute("empNameList", empNameList);


		RequestDispatcher dispatcher = request.getRequestDispatcher("./view/sign/signManage.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("sign : get()");

		 request.setCharacterEncoding("UTF-8");
	        String startDate = request.getParameter("startDate");
			String finishDate =  request.getParameter("finishDate");
	        String roomId = request.getParameter("roomDivision");
	        System.out.println("buildName : " + roomId);
	        if(roomId==null || roomId.equals("default")){
	        	roomId = "%%";
	        }else if(roomId.equals("ER")){
	        	roomId = "ER%";
	        }else if(roomId.equals("CR")){
	        	roomId = "CR%";
	        }

	        List<ReservationDTO> reservationCrList = (new ReservationDAO()).select(startDate, finishDate, roomId,"결재대기");
			List<RoomDTO> roomList = new LinkedList<RoomDTO>();
			List<String> empNameList = new LinkedList<String>();

			for(ReservationDTO res : reservationCrList){
				RoomDTO room = (new RoomDAO()).selectRoomInfo(res.getRoomId());
				EmployeesDTO empName = (new EmployeesDAO()).login(res.getEmpId());
				roomList.add(room);
				empNameList.add(empName.getEmpName());
			}

			HttpSession session = request.getSession();

			request.setAttribute("reservationList",reservationCrList );
			request.setAttribute("roomList", roomList);
			request.setAttribute("empNameList", empNameList);
			request.setAttribute("searchClick", "click");

			RequestDispatcher dispatcher = request.getRequestDispatcher("./view/sign/signManage.jsp");
			dispatcher.forward(request, response);
	}

}
