<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, user-scalable=no">
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"/>
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
    <title>Twoch saver</title>
</head>

<body>

<!-- Page: status  -->
<div id="status" data-role="page" is="page">
    <div role="main" class="ui-content" is="content">
        <a class="ui-btn" href="/list">Back</a>
        <a class="ui-btn" href="#" onclick="location.reload();">Refresh</a>
        <table data-role="table" data-mode="columntoggle"
               class="ui-responsive ui-shadow gk-decorate ui-table ui-table-columntoggle" id="show_table"
               is="jqm-table">
            <thead>
            <tr>
                <th data-priority="1" data-colstart="1" class="ui-table-priority-1">Путь</th>
                <th data-priority="1" data-colstart="2" class="ui-table-priority-1">Файл</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${data}" var="show" varStatus="loopStatus">
                <tr>
                    <td class="ui-table-priority-1"><c:out value="${show.url}"/></td>
                    <td class="ui-table-priority-1">
                        <img style="width : 100%; height : auto;" src="<c:out value="${show.preparedUrl}"/>">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
