<%@ page import="com.cygnus.sys.trm.STAuditTrail" %>



<div class="fieldcontain ${hasErrors(bean: STAuditTrailInstance, field: 'process', 'error')} ">
	<label for="process">
		<g:message code="STAuditTrail.process.label" default="Process" />
		
	</label>
	<g:textField name="process" value="${STAuditTrailInstance?.process}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: STAuditTrailInstance, field: 'accessDtm', 'error')} required">
	<label for="accessDtm">
		<g:message code="STAuditTrail.accessDtm.label" default="Access Dtm" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="accessDtm" precision="day"  value="${STAuditTrailInstance?.accessDtm}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: STAuditTrailInstance, field: 'domainClassName', 'error')} ">
	<label for="domainClassName">
		<g:message code="STAuditTrail.domainClassName.label" default="Domain Class Name" />
		
	</label>
	<g:textField name="domainClassName" value="${STAuditTrailInstance?.domainClassName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: STAuditTrailInstance, field: 'modifiedBy', 'error')} ">
	<label for="modifiedBy">
		<g:message code="STAuditTrail.modifiedBy.label" default="Modified By" />
		
	</label>
	<g:textField name="modifiedBy" value="${STAuditTrailInstance?.modifiedBy}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: STAuditTrailInstance, field: 'tdAuditTrail', 'error')} ">
	<label for="tdAuditTrail">
		<g:message code="STAuditTrail.tdAuditTrail.label" default="Td Audit Trail" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${STAuditTrailInstance?.tdAuditTrail?}" var="t">
    <li><g:link controller="STdAuditTrail" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="STdAuditTrail" action="create" params="['STAuditTrail.id': STAuditTrailInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'STdAuditTrail.label', default: 'STdAuditTrail')])}</g:link>
</li>
</ul>

</div>

