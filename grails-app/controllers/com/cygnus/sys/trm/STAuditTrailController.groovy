package com.cygnus.sys.trm

import org.springframework.dao.DataIntegrityViolationException

class STAuditTrailController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [STAuditTrailInstanceList: STAuditTrail.list(params), STAuditTrailInstanceTotal: STAuditTrail.count()]
    }

    
}
