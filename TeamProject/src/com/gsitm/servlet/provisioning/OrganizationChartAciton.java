package com.gsitm.servlet.provisioning;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gsitm.dao.ProvisioningDAO;
import com.gsitm.dto.EmployeesDTO;


@WebServlet("/organizationchart")
public class OrganizationChartAciton extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		//해당 부서의 직원들을 조회하기 위하여 부서ID를 받아옴
		String dep_id = request.getParameter("dep_id");
		ProvisioningDAO pDao = new ProvisioningDAO();
		List<EmployeesDTO> employeesList = pDao.selectDepartmentsEmployees(dep_id);

		//해당 부서의 직원리스트
		request.setAttribute("employeesList", employeesList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./view/provisioning/organizationChart.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String employeesList = request.getParameter("employeesList");
		String empId = request.getParameter("empId");
		String empName = request.getParameter("empName");
		System.out.println("organization서블릿 : " + request.getParameter("empId"));///////
		request.setAttribute("empId", empId);
		request.setAttribute("empName", empName);
		request.setAttribute("employeesList", employeesList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./view/provisioning/provisioning.jsp");
		dispatcher.forward(request, response);*/
	}
}
