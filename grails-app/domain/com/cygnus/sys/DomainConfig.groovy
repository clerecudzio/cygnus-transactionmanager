package com.cygnus.sys

class DomainConfig {
	
	String domainClassName
	
	boolean auditEnabled = true
	boolean softDeleteEnabled = true

    static constraints = {
		domainClassName unique:true
    }
	
	
}
