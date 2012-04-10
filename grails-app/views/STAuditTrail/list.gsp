
<%@ page import="com.cygnus.sys.trm.STAuditTrail" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="cygnus-forms">
		<g:set var="entityName" value="${message(code: 'STAuditTrail.label', default: 'STAuditTrail')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<parameter name="pageEName" value="domainConfig" />
	</head>
	<body>
		<a href="#list-STAuditTrail" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
	
		<div id="list-STAuditTrail" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="process" title="${message(code: 'STAuditTrail.process.label', default: 'Process')}" />
					
						<g:sortableColumn property="accessDtm" title="${message(code: 'STAuditTrail.accessDtm.label', default: 'Access Dtm')}" />
					
						<g:sortableColumn property="domainClassName" title="${message(code: 'STAuditTrail.domainClassName.label', default: 'Domain Class Name')}" />
					
						<g:sortableColumn property="modifiedBy" title="${message(code: 'STAuditTrail.modifiedBy.label', default: 'Modified By')}" />
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${STAuditTrailInstanceList}" status="i" var="STAuditTrailInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="listOfAuditTrail" controller="STdAuditTrail" id="${STAuditTrailInstance.id}">${fieldValue(bean: STAuditTrailInstance, field: "process")}</g:link></td>
					
						<td><g:formatDate date="${STAuditTrailInstance.accessDtm}" /></td>
					
						<td>${fieldValue(bean: STAuditTrailInstance, field: "domainClassName")}</td>
					
						<td>${fieldValue(bean: STAuditTrailInstance, field: "modifiedBy")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${STAuditTrailInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
