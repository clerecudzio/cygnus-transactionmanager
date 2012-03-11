package com.cygnus.sys.trm

import java.sql.Timestamp

/**
 * Audit Trail Header
 * @author haris
 * For storing audit trail main information
 */
class STAuditTrail {
	String domainClassName
	String modifiedBy
	String process
	Date accessDtm
	
	
	
	static constraints = {
		process nullable:true
	}
	
	
	static hasMany  = [tdAuditTrail:STdAuditTrail]
}
