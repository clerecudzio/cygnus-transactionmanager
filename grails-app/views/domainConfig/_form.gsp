<%@ page import="com.cygnus.sys.DomainConfig" %>



<div class="fieldcontain ${hasErrors(bean: domainConfigInstance, field: 'domainClassName', 'error')} ">
	<label for="domainClassName">
		<g:message code="domainConfig.domainClassName.label" default="Domain Class Name" />
		
	</label>
	<g:textField name="domainClassName" value="${domainConfigInstance?.domainClassName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: domainConfigInstance, field: 'auditEnabled', 'error')} ">
	<label for="auditEnabled">
		<g:message code="domainConfig.auditEnabled.label" default="Audit Enabled" />
		
	</label>
	<g:checkBox name="auditEnabled" value="${domainConfigInstance?.auditEnabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: domainConfigInstance, field: 'softDeleteEnabled', 'error')} ">
	<label for="softDeleteEnabled">
		<g:message code="domainConfig.softDeleteEnabled.label" default="Soft Delete Enabled" />
		
	</label>
	<g:checkBox name="softDeleteEnabled" value="${domainConfigInstance?.softDeleteEnabled}" />
</div>

