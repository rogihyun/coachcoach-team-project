<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../header.jsp"/>

  <h1>고객센터 </h1>
  <a href='form'>새 공지사항 </a> <br>
  <table border='1' width="500" height="100">
  <tr>
    <th>번호</th>
    <th>제목</th>
    <th>등록일</th>
    <th>조회수</th>
  </tr>
  
  <c:forEach items="${list}" var="item">
  <tr>
    <td>${item.no}</td> 
    <td><a href='detail?no=${item.no}'>=> ${item.title}</a></td> 
    <td>${item.date}</td>
    <td>${item.viewCount}</td>
  </tr>
</c:forEach>
  
  </table>


<jsp:include page="../../footer.jsp"/>
    