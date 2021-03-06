<%--
author  : 김준호
since   : 2017. 5. 6.
version : 1.0
subject : 회의실 상세정보 페이지
description :
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회의실 상세정보</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/common.css">
		<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.min.js"></script>
<style>
#container {
		margin: auto;
		width: 800px;
	}
	/* 제목 스타일*/
	#header {
		text-align: center;
		margin-top: 100px;
  	  	top: 30px;
	}
	/* 좌측 상세정보 출력 스타일 */
	#contents {
		margin-top: 100px;
		margin-left: 30px;
		width: 400px;
		height: 200px;
		float: left;
	}
	/* 우측 이미지 스타일 */
	#sidebar {
		margin-top: 95px;
		margin-right: 70px;
		width: 100px;
		height: 100px;
		text-align: center;
		float: left;
		position: relative;
	}
	/* 돌아가기 버튼 스타일 */
	#backBtn {
		width: 370px;
		height: 30px;
		background-color: grey;
		border: 1px solid grey;
		color: white;
	}
	#slider {
		width:330px;
		height:200px;
		border: 2px solid grey;
		box-shadow: 5px 5px 5px 0px grey;

	}
	.slide-wrap {
		width:330px;
		height:200px;
		position:relative;
		overflow:hidden;
		padding : 0px;
	}
	ul.slide-list {
		position:absolute;
		left: 0px;
		padding-left:0px;
		margin-top:0px;
	}
	ul.slide-list li {
		position:relative;
		float:left;
		width:330px;
		height:200px;
		list-style-type: none;
	}
	ul.slide-list li img {
		width:330px;
		height: 200px;
	}
	/* 이전 이미지 버튼 스타일 */
	#prev {
		position:absolute;
		left:10px;
		top:85px;
	 	z-index:20;
	 	width: 30px;
	    height: 30px;
	    font-size:15px;
	    font-weight: bold;
	    font-family: 'Nanum Gothic';
	    color: white;
	    text-align: center;
	    background: grey;
	    border: solid 2px white;
	    border-radius: 50px;
 	}
 	/* 다음 이미지 버튼 스타일 */
 	#next {
		position:absolute;
		right:-220px;
		top:85px;
		z-index:20;
		width: 30px;
	    height: 30px;
	    font-size:15px;
	    font-weight: bold;
	    font-family: 'Nanum Gothic';
	    color: white;
	    text-align: center;
	    background: grey;
	    border: solid 2px white;
	    border-radius: 50px;
 	}
 	/* 제목선 */
 	.hr {
		height: 2px;
 	}
 	/* 회의실 및 교육실 정보 출력 테이블 스타일 */
 	#showTableInfo {
		border-collapse:collapse;
		width:370px;
		height: 170px;
		border: 1px solid #e9e9e9;
 	}
 	#showTableInfo th {
 		text-align: left;
		border-bottom: 1px solid #ccc;
    	background: #f3f6f7;
 	}
 	#showTableInfo td {
		text-align: center;
		width: 150px;
	    border-bottom: 1px solid #ccc;
 	}
 	#showTableInfo .firstTd {
		background: #e9e9e9;
		width: 100px;
		text-align: left;
 	}
 	#contents_wrapper{
 		width:800px;
 		height: 400px;
 	}
 	#div_button{
 		margin-top:10px;
 	}
</style>
<script type="text/javascript">
	function fn_rollToEx(containerID, slideID){
		// 롤링할 객체를 변수에 담음
		var el = $('#'+containerID).find('#'+slideID);
		var lastChild;
		var speed = 3000;
		var timer = 0;

		el.data('prev', $('#'+containerID).find('.prev'));  //이전버튼을 data()메서드를 사용하여 저장
		el.data('next', $('#'+containerID).find('.next'));  //다음버튼을 data()메서드를 사용하여 저장
		el.data('size', el.children().outerWidth());        //롤링객체의 자식요소의 넓이를 저장
		el.data('len', el.children().length);               //롤링객체의 전체요소 개수
		el.data('animating',false);
		el.css('width',el.data('size')*el.data('len'));     //롤링객체의 전체넓이 지정

		//el에 첨부된 prev 데이타를 클릭이벤트에 바인드
		el.data('prev').bind({
			click:function(e){
				e.preventDefault();
				movePrevSlide();
			}
		});
		//el에 첨부된 next 데이타를 클릭이벤트에 바인드
		el.data('next').bind({
			click:function(e){
				e.preventDefault();
				moveNextSlide();
			}
		});
		function movePrevSlide(){
			if(!el.data('animating')){
				//롤링객체의 끝에서 요소를 선택하여 복사한후 변수에 저장
				var lastItem = el.children().eq(-2).nextAll().clone(true);
				lastItem.prependTo(el);     //복사된 요소를 롤링객체의 앞에 붙여놓음
				el.children().eq(-2).nextAll().remove();    //선택된 요소는 끝에서 제거
				el.css('left','-'+(el.data('size')*1+'px'));    //롤링객체의 left위치값을 재설정

				el.data('animating',true);  //애니메이션 중복을 막기 위해 첨부된 animating 데이타를 true로 설정

				el.animate({'left': '0px'},'normal',function(){     //롤링객체를 left:0만큼 애니메이션 시킴
					el.data('animating',false);
				});
			}
			return false;
		}
		function moveNextSlide(){
			if(!el.data('animating')){
				el.data('animating',true);

				el.animate({'left':'-'+(el.data('size')*1)+'px'},'normal',function(){   //롤링객체를 애니메이션 시킴
					//롤링객체의 앞에서 요소를 선택하여 복사한후 변수에 저장
					var firstChild = el.children().filter(':lt('+1+')').clone(true);
					firstChild.appendTo(el);    //복사된 요소를 롤링객체의 끝에 붙여놓음
					el.children().filter(':lt('+1+')').remove();    //선택된 요소를 앞에서 제거
					el.css('left','0px');   	//롤링객체의 left위치값을 재설정

					el.data('animating',false);
				});
			}
			return false;
		}
	}
</script>
</head>
<body>
<div id="container">
	<div id="header" class="head">
		<!-- 사용자가 선택한 건물의 회의실명 출력 -->
		<hr class="hr">
		<h2>${roomDetailInfo.buildName} > ${roomDetailInfo.roomName} 상세정보</h2>
		<hr class="hr">
	</div>
	<div id="contents_wrapper" class="head">
		<div id="contents">
			<table id="showTableInfo">
			<!-- 사용자가 선택한 회의실 정보 출력 -->
			<c:if test="${roomDetailInfo != null}">
				<tr><td class="firstTd">· 회의실 위치 </td><td> <c:out value="${roomDetailInfo.position}"/> </td></tr>
				<tr><td class="firstTd">· 회의실 크기 </td><td> <c:out value="${roomDetailInfo.roomSize}"/> </td></tr>
				<tr><td class="firstTd">· 회의실 수용인원 </td><td> <c:out value="${roomDetailInfo.numberOfEmp}명"/> </td></tr>
				<tr><td class="firstTd">· 비치물품 </td><td>
				<!-- 사용자가 선택한 회의실에 비치물품이 없을 경우 -->
				<c:if test="${empty roomFixtureList}">
				없음</td>
				</c:if>
				<!-- 사용자가 선택한 회의실에 비치물품이 있을 경우 -->
				<c:if test="${roomFixtureList != null}">
					<c:forEach var="roomFixtureList" items="${roomFixtureList}" varStatus="status">
						${roomFixtureList.name}
						<!-- 출력할 기자재가 마지막이 아닐 경우 콤마(,) 출력 -->
						<c:if test="${not status.last}">
						,
						</c:if>
						<!-- 출력할 기자재가 마지막일 경우 개행 -->
						<c:if test="${status.last}">
							<br>
						</c:if>
					</c:forEach>
					</td>
				</c:if>
				<tr><td class="firstTd">· N/W사용여부 </td><td> <c:out value="${roomDetailInfo.netFlag}"/></td></tr>
				<tr><td class="firstTd">· 사용요금 </td><td> <c:out value="${roomDetailInfo.fee}원"/></td></tr>
			</c:if>
			</table>
			<div id="div_button">
				<input type="button" class="btn" id="backBtn" value="돌아가기" onClick="window.close()"/>
			</div>
		</div>
		<c:if test="${roomPhotoList != null}">
		<div id="sidebar">
			<div id="slider">
				<button id="prev" class="prev"><</button>
				<div class="slide-wrap">
					<ul id="photo" class="slide-list">
							<!-- 사용자가 선택한 회의실 사진 출력 -->
							<c:forEach var="roomPhotoList" items="${roomPhotoList}">
								<li>
									<img src="${pageContext.servletContext.contextPath}/resources/image/${roomPhotoList.photoName}"/>
								</li>
							</c:forEach>
					</ul>
				</div>
				<button id="next" class="next">></button>
			</div>
			<!-- 이미지 슬라이더 함수 호출 -->
			<script type="text/javascript">
				fn_rollToEx('slider', 'photo');
			</script>
		</div>
		</c:if>
	</div>
</div>
</body>
</html>