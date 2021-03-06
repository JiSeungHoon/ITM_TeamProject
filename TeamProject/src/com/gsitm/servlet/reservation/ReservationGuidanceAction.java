package com.gsitm.servlet.reservation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
*
* @프로그램명 : ConferenceReserveDeleteAction.java
* @작성일     : 2017. 5. 14.
* @작성자     : 김준호
* @기능       : 예약 안내 서블릿
* @버전       : 1.0
*
*/
@WebServlet("/reservation/guidance")
public class ReservationGuidanceAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("reservation guidance action : get()");
		RequestDispatcher dispatcher = request.getRequestDispatcher("../view/reservation/reservationGuidance.jsp");
	     dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
