<%@ page import="com.cygnus.sys.trm.STdAuditTrail" %>



<div class="fieldcontain ${hasErrors(bean: STdAuditTrailInstance, field: 'columnName', 'error')} ">
	<label for="columnName">
		<g:message code="STdAuditTrail.columnName.label" default="Column Name" />
		
	</label>
	<g:textField name="columnName" value="${STdAuditTrailInstance?.columnName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: STdAuditTrailInstance, field: 'columnValue', 'error')} ">
	<label for="columnValue">
		<g:message code="STdAuditTrail.columnValue.label" default="Column Value" />
		
	</label>
	<g:textField name="columnValue" value="${STdAuditTrailInstance?.columnValue}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: STdAuditTrailInstance, field: 'tAuditTrail', 'error')} required">
	<label for="tAuditTrail">
		<g:message code="STdAuditTrail.tAuditTrail.label" default="TA udit Trail" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tAuditTrail" name="tAuditTrail.id" from="${com.cygnus.sys.trm.STAuditTrail.list()}" optionKey="id" required="" value="${STdAuditTrailInstance?.tAuditTrail?.id}" class="many-to-one"/>
</div>

