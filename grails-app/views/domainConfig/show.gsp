
<%@ page import="com.cygnus.sys.DomainConfig" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="cygnus-forms">
		<g:set var="entityName" value="${message(code: 'domainConfig.label', default: 'DomainConfig')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-domainConfig" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-domainConfig" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list domainConfig">
			
				<g:if test="${domainConfigInstance?.domainClassName}">
				<li class="fieldcontain">
					<span id="domainClassName-label" class="property-label"><g:message code="domainConfig.domainClassName.label" default="Domain Class Name" /></span>
					
						<span class="property-value" aria-labelledby="domainClassName-label"><g:fieldValue bean="${domainConfigInstance}" field="domainClassName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${domainConfigInstance?.auditEnabled}">
				<li class="fieldcontain">
					<span id="auditEnabled-label" class="property-label"><g:message code="domainConfig.auditEnabled.label" default="Audit Enabled" /></span>
					
						<span class="property-value" aria-labelledby="auditEnabled-label"><g:formatBoolean boolean="${domainConfigInstance?.auditEnabled}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${domainConfigInstance?.softDeleteEnabled}">
				<li class="fieldcontain">
					<span id="softDeleteEnabled-label" class="property-label"><g:message code="domainConfig.softDeleteEnabled.label" default="Soft Delete Enabled" /></span>
					
						<span class="property-value" aria-labelledby="softDeleteEnabled-label"><g:formatBoolean boolean="${domainConfigInstance?.softDeleteEnabled}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${domainConfigInstance?.id}" />
					<g:link class="edit" action="edit" id="${domainConfigInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
