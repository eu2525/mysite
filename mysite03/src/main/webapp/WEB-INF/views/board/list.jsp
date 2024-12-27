<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="/mysite02/board" method="post">
					<input type="text" id="keyword" name="keyword" value="${keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>			
					
					<!-- List 받아서 출력하는 부분 -->
					<c:forEach items="${list }" var="vo" >
						<tr>
							<td>[${vo.id }] </td>
							<td style="text-align:left; padding-left:${vo.depth * 20}px">
							    <c:if test="${vo.depth != 0}">
	                                <img src="${pageContext.request.contextPath}/assets/images/reply.png">
                            	</c:if>
								<a href="${pageContext.request.contextPath }/board?a=view&titleId=${vo.id }"> ${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:if test="${vo.userId == authUser.id }">
								<td><a href="${pageContext.request.contextPath }/board?a=delete&titleId=${vo.id }" class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<!-- 첫페이지이면 이전 버튼 없어야 하고 끝페이지면 다음 버튼과 끝 페이지가 없어야함 -->
						<c:if test='${currentPage != 1}'>
							<li><a href="${pageContext.request.contextPath }/board?pageIdx=${currentPage - 1}&keyword=${keyword }">◀</a></li>
							<li><a href="${pageContext.request.contextPath }/board?pageIdx=1&keyword=${keyword }">1</a></li>
						</c:if>
						<c:if test='${currentPage > 2}'>
							<li>...</li>
							<li><a href="${pageContext.request.contextPath }/board?pageIdx=${currentPage - 1}&keyword=${keyword }">${currentPage - 1}</a></li>									
						</c:if>					
						<li class="selected">${currentPage }</li>
						<c:if test='${totalPage - currentPage > 1}'>
							<li><a href="${pageContext.request.contextPath }/board?pageIdx=${currentPage + 1}&keyword=${keyword }">${currentPage + 1}</a></li>
							<li>...</li>
						</c:if>			
						
						<c:if test='${currentPage != totalPage}'>
							<li><a href="${pageContext.request.contextPath }/board?pageIdx=${totalPage}&keyword=${keyword }">${totalPage }</a></li>
							<li><a href="${pageContext.request.contextPath }/board?pageIdx=${currentPage + 1}&keyword=${keyword }">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<!-- 로그인시 글쓰기 버튼 활성화 -->
				<c:if test='${not empty authUser }'>
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?a=write" id="new-book">글쓰기</a>
					</div>	
		    	</c:if>
			    			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>