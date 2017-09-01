<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
</head>
<body>
<h2>这是新的一个页面！</h2>
<div>
    ${studentlist}
    ${studentlist[0].name}
    <c:forEach var="i" items="${studentlist}">
        1
    </c:forEach>
</div>

<div>
    <%--<form method="post" action="/student/create.do">--%>
    <%--用户名：<input id="username" name="username" value="">--%>
    <%--密码：<input id="password" name="password" value="">--%>
    <%--<input type="submit" value="提交">--%>
    <%--</form>--%>

        用户名：<input id="username" name="username" type="text">
        密码：<input id="password" name="password" type="text">
        <input type="button" value="提交" onclick="create()">
</div>


</body>
<script src="<%=request.getContextPath()%>/static/js/jquery/jquery-2.1.0.min.js" type="text/javascript"></script>
<script>
    function create() {
        var username = $("input[name='username']").val();
        var password = $("#password").val();
        $.ajax({
            type: 'POST',
            url: "../student/create.do",
            data: {"username": username, "password": password},
            success: function (data) {
            },
        });
    }

</script>
</html>
