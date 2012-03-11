package com.cygnus.sys.trm

class STdAuditTrail {
	
	
	String columnName
	String columnValue
	
    static constraints = {
    }
	
	static belongsTo = [tAuditTrail:STAuditTrail]
}
