
<%@ page import="com.cygnus.sys.trm.STAuditTrail" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="cygnus-forms">
		<g:set var="entityName" value="${message(code: 'STAuditTrail.label', default: 'STAuditTrail')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<parameter name="pageEName" value="domainConfig" />
	</head>
	<body>
		<a href="#show-STAuditTrail" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-STAuditTrail" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list STAuditTrail">
			
				<g:if test="${STAuditTrailInstance?.process}">
				<li class="fieldcontain">
					<span id="process-label" class="property-label"><g:message code="STAuditTrail.process.label" default="Process" /></span>
					
						<span class="property-value" aria-labelledby="process-label"><g:fieldValue bean="${STAuditTrailInstance}" field="process"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${STAuditTrailInstance?.accessDtm}">
				<li class="fieldcontain">
					<span id="accessDtm-label" class="property-label"><g:message code="STAuditTrail.accessDtm.label" default="Access Dtm" /></span>
					
						<span class="property-value" aria-labelledby="accessDtm-label"><g:formatDate date="${STAuditTrailInstance?.accessDtm}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${STAuditTrailInstance?.domainClassName}">
				<li class="fieldcontain">
					<span id="domainClassName-label" class="property-label"><g:message code="STAuditTrail.domainClassName.label" default="Domain Class Name" /></span>
					
						<span class="property-value" aria-labelledby="domainClassName-label"><g:fieldValue bean="${STAuditTrailInstance}" field="domainClassName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${STAuditTrailInstance?.modifiedBy}">
				<li class="fieldcontain">
					<span id="modifiedBy-label" class="property-label"><g:message code="STAuditTrail.modifiedBy.label" default="Modified By" /></span>
					
						<span class="property-value" aria-labelledby="modifiedBy-label"><g:fieldValue bean="${STAuditTrailInstance}" field="modifiedBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${STAuditTrailInstance?.tdAuditTrail}">
				<li class="fieldcontain">
					<span id="tdAuditTrail-label" class="property-label"><g:message code="STAuditTrail.tdAuditTrail.label" default="Td Audit Trail" /></span>
					
						<g:each in="${STAuditTrailInstance.tdAuditTrail}" var="t">
						<span class="property-value" aria-labelledby="tdAuditTrail-label"><g:link controller="STdAuditTrail" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${STAuditTrailInstance?.id}" />
					<g:link class="edit" action="edit" id="${STAuditTrailInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
