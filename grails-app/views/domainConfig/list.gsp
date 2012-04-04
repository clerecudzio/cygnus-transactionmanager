
<%@ page import="com.cygnus.sys.DomainConfig" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="cygnus-forms">
		<g:set var="entityName" value="${message(code: 'domainConfig.label', default: 'DomainConfig')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-domainConfig" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-domainConfig" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="domainClassName" title="${message(code: 'domainConfig.domainClassName.label', default: 'Domain Class Name')}" />
					
						<g:sortableColumn property="auditEnabled" title="${message(code: 'domainConfig.auditEnabled.label', default: 'Audit Enabled')}" />
					
						<g:sortableColumn property="softDeleteEnabled" title="${message(code: 'domainConfig.softDeleteEnabled.label', default: 'Soft Delete Enabled')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${domainConfigInstanceList}" status="i" var="domainConfigInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${domainConfigInstance.id}">${fieldValue(bean: domainConfigInstance, field: "domainClassName")}</g:link></td>
					
						<td><g:formatBoolean boolean="${domainConfigInstance.auditEnabled}" /></td>
					
						<td><g:formatBoolean boolean="${domainConfigInstance.softDeleteEnabled}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${domainConfigInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
