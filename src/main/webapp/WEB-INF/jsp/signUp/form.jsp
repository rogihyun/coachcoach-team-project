<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="../header.jsp"/>

<h1>회원 가입</h1>
<form action='add' method='post' enctype='multipart/form-data'>
이름: <input name='name' type='text'><br>
성별: <input name='gender' type='text'><br>
생년월일: <input name= 'birth' type='date'><br>
전화: <input name='tel' type='tel'><br>
이메일: <input name='email' type='email'><br>
암호: <input name='password' type='password'><br>
사진: <input name='photoFile' type='file'><br>
<button>가입하기</button>
</form>

<jsp:include page="../footer.jsp"/>
    