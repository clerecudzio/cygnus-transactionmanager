package com.cygnus.sys.trm

import org.springframework.dao.DataIntegrityViolationException

class STdAuditTrailController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def listOfAuditTrail(){
		def STdAuditTrailInstanceList = STdAuditTrail.findAllByTAuditTrail(STAuditTrail.get(params.id))
		
	
		render(view:"list",model:	[STdAuditTrailInstanceList: STdAuditTrailInstanceList]) 
	}

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [STdAuditTrailInstanceList: STdAuditTrail.list(params), STdAuditTrailInstanceTotal: STdAuditTrail.count()]
    }

}
