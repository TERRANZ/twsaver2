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
        <a class="ui-btn" href="/">Back</a>
        <a class="ui-btn" href="#" onclick="location.reload();">Refresh</a>
        <table data-role="table" data-mode="columntoggle"
               class="ui-responsive ui-shadow gk-decorate ui-table ui-table-columntoggle" id="statistics"
               is="jqm-table">
            <thead>
            <tr>
                <th data-priority="1" data-colstart="1" class="ui-table-priority-1">Архив</th>
                <th data-priority="1" data-colstart="2" class="ui-table-priority-1">Сообщений</th>
                <th data-priority="1" data-colstart="2" class="ui-table-priority-1">Файлов</th>
                <th data-priority="1" data-colstart="3" class="ui-table-priority-1">Осталось</th>
                <th data-priority="1" data-colstart="4" class="ui-table-priority-1">Ссылка</th>
                <th data-priority="1" data-colstart="5" class="ui-table-priority-1">Обновить</th>
                <th data-priority="1" data-colstart="6" class="ui-table-priority-1">Завершено</th>
                <th data-priority="1" data-colstart="7" class="ui-table-priority-1">Обновлений</th>
                <th data-priority="1" data-colstart="8" class="ui-table-priority-1">Добавлено</th>
                <th data-priority="1" data-colstart="9" class="ui-table-priority-1">Обновлено</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${data}" var="stat" varStatus="loopStatus">
                <tr>
                    <th class="ui-table-priority-1">
                        <a href="/show?url=<c:out value="${stat.bt}"/>"><c:out value="${stat.bt}"/></a>
                    </th>
                    <td class="ui-table-priority-1"><c:out value="${stat.messages}"/></td>
                    <td class="ui-table-priority-1"><c:out value="${stat.count}"/></td>
                    <td class="ui-table-priority-1"><c:out value="${stat.remaining}"/></td>
                    <td class="ui-table-priority-1">
                        <a href="/pack?url=<c:out value="${stat.bt}"/>">Архив</a>
                    </td>
                    <td class="ui-table-priority-1">
                        <a href="/start?url=<c:out value="${stat.bt}"/>">Обновить</a>
                    </td>
                    <td class="ui-table-priority-1"><c:out value="${stat.finished}"/></td>
                    <td class="ui-table-priority-1"><c:out value="${stat.checked}"/></td>
                    <td class="ui-table-priority-1"><c:out value="${stat.added}"/></td>
                    <td class="ui-table-priority-1"><c:out value="${stat.updated}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
