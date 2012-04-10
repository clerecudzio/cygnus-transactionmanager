
<%@ page import="com.cygnus.sys.trm.STdAuditTrail" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="cygnus-forms">
		<g:set var="entityName" value="${message(code: 'STdAuditTrail.label', default: 'STdAuditTrail')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="pageEName" value="domainConfig" />
	</head>
	<body>
		<a href="#list-STdAuditTrail" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-STdAuditTrail" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="columnName" title="${message(code: 'STdAuditTrail.columnName.label', default: 'Column Name')}" />
					
						<g:sortableColumn property="columnValue" title="${message(code: 'STdAuditTrail.columnValue.label', default: 'Column Value')}" />
					
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${STdAuditTrailInstanceList}" status="i" var="STdAuditTrailInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: STdAuditTrailInstance, field: "columnName")}</td>
					
						<td>${fieldValue(bean: STdAuditTrailInstance, field: "columnValue")}</td>
					
					
					</tr>
				</g:each>
				</tbody>
			</table>
			
		</div>
	</body>
</html>
