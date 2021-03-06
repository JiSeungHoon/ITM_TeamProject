package com.gsitm.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.*;

import com.gsitm.dao.DepOfUseRoomDAO;
import com.gsitm.dao.EmployeesDAO;
import com.gsitm.dao.ReservationDAO;
import com.gsitm.dto.DepOfUseRoomDTO;
import com.gsitm.dto.EmployeesDTO;
import com.gsitm.dto.ReservationDTO;
import com.gsitm.mail.MailSender;

public class JobAction implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("스케쥴러 Job1 실행: " 
				+ ( new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a")).format(new Date()).toString());
		
		ReservationDAO resDAO = new ReservationDAO();
		
		// --- 예약내역 중 사용완료된 것 비용청구 ---
		Date now = new Date();
		List<ReservationDTO> resList =  resDAO.selectFinishedList(now, "승인완료");
		for(ReservationDTO res : resList){
			System.out.println("비용청구: " + res.getRegInId());

			try{
				resDAO.update(res.getRegInId(), "사용완료");
				
				// 예약자에게 비용청구 메일 발송
				(new MailSender()).send( (new EmployeesDAO()).login(res.getEmpId()).getEmail()  
						, "비용청구", res.getRegInId());
				
				// 각 사용팀 부서장들에게 비용청구 메일 발송
				List<DepOfUseRoomDTO> deps = (new DepOfUseRoomDAO()).select(res.getRegInId());
				for(DepOfUseRoomDTO dep : deps){
					if(!"ETA000".equals(dep.getDepId())){
						EmployeesDTO captainDTO = (new EmployeesDAO()).getCaptianByDepId(dep.getDepId());
						(new MailSender()).send(captainDTO.getEmail(), "비용청구", res.getRegInId());
						System.out.println("청구메일 발송~ " + captainDTO.getDepName());
					}
				}
				
			} catch (Exception e) {
				System.out.println("사용완료처리 실패: " + e.getMessage());
			}
		}
		
		// --- 기간 경과한 미결재 취소 ---
		resList =  resDAO.selectInvalidList(now, "결재대기");
		for(ReservationDTO res : resList){
			try{
				//지연 취소 처리
				resDAO.update(res.getRegInId(), "결재지연취소");
				
				//지연 취소알림메일 발송
				(new MailSender()).send( (new EmployeesDAO()).login(res.getEmpId()).getEmail()  
						, "지연취소", res.getRegInId());
			} catch (Exception e) {
				System.out.println("결재지연취소 실패: " + e.getMessage());
			}
		}
		
		// --- 기간 경과한 미승인 취소 ---
		resList =  resDAO.selectInvalidList(now, "승인대기");
		for(ReservationDTO res : resList){
			try{
				//지연 취소처리
				resDAO.update(res.getRegInId(), "승인지연취소");
				
				//지연 취소알림메일 발송
				(new MailSender()).send( (new EmployeesDAO()).login(res.getEmpId()).getEmail()  
						, "지연취소", res.getRegInId());
			} catch (Exception e) {
				System.out.println("승인지연취소 실패: " + e.getMessage());
			}
		}
	}
}
