package com.cygnus.sys.trm

/**
 * Audit Trail Detail
 * @author haris
 * Domain for storing column objects on AuditTrail
 */
class STdAuditTrail {
	
	
	String columnName
	String columnValue
	
    static constraints = {
    }
	
	static belongsTo = [tAuditTrail:STAuditTrail]
}
