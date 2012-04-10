
<%@ page import="com.cygnus.sys.trm.STdAuditTrail" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="cygnus-forms">
		<g:set var="entityName" value="${message(code: 'STdAuditTrail.label', default: 'STdAuditTrail')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="pageEName" value="domainConfig" />
	</head>
	<body>
		<a href="#show-STdAuditTrail" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-STdAuditTrail" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list STdAuditTrail">
			
				<g:if test="${STdAuditTrailInstance?.columnName}">
				<li class="fieldcontain">
					<span id="columnName-label" class="property-label"><g:message code="STdAuditTrail.columnName.label" default="Column Name" /></span>
					
						<span class="property-value" aria-labelledby="columnName-label"><g:fieldValue bean="${STdAuditTrailInstance}" field="columnName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${STdAuditTrailInstance?.columnValue}">
				<li class="fieldcontain">
					<span id="columnValue-label" class="property-label"><g:message code="STdAuditTrail.columnValue.label" default="Column Value" /></span>
					
						<span class="property-value" aria-labelledby="columnValue-label"><g:fieldValue bean="${STdAuditTrailInstance}" field="columnValue"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${STdAuditTrailInstance?.tAuditTrail}">
				<li class="fieldcontain">
					<span id="tAuditTrail-label" class="property-label"><g:message code="STdAuditTrail.tAuditTrail.label" default="TA udit Trail" /></span>
					
						<span class="property-value" aria-labelledby="tAuditTrail-label"><g:link controller="STAuditTrail" action="show" id="${STdAuditTrailInstance?.tAuditTrail?.id}">${STdAuditTrailInstance?.tAuditTrail?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${STdAuditTrailInstance?.id}" />
					<g:link class="edit" action="edit" id="${STdAuditTrailInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
