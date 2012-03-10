package com.cygnus.sys.trm

class STdAuditTrail {
	STAuditTrail tAuditTrail
	
	String columnName
	String columnValue
	
    static constraints = {
    }
	
	static belongsTo = [tAuditTrail:STAuditTrail]
}
