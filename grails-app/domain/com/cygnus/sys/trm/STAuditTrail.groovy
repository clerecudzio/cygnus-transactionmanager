package com.cygnus.sys.trm

import java.sql.Timestamp

class STAuditTrail {
	String domainClassName
	String modifiedBy
	String process
	Date accessDtm
	
	STdAuditTrail tdAuditTrail
	
	static constraints = {
	}
	
	
	static hasMany  = [tdAuditTrail:STdAuditTrail]
}
