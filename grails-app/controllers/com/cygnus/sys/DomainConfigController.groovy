package com.cygnus.sys

import org.springframework.dao.DataIntegrityViolationException

class DomainConfigController {
	def universalSearchService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [domainConfigInstanceList: DomainConfig.list(params), domainConfigInstanceTotal: DomainConfig.count()]
    }

    def create() {
        [domainConfigInstance: new DomainConfig(params)]
    }

    def save() {
        def domainConfigInstance = new DomainConfig(params)
        if (!domainConfigInstance.save(flush: true)) {
            render(view: "create", model: [domainConfigInstance: domainConfigInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), domainConfigInstance.id])
        redirect(action: "show", id: domainConfigInstance.id)
    }

    def show() {
        def domainConfigInstance = DomainConfig.get(params.id)
        if (!domainConfigInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), params.id])
            redirect(action: "list")
            return
        }

        [domainConfigInstance: domainConfigInstance]
    }

    def edit() {
        def domainConfigInstance = DomainConfig.get(params.id)
        if (!domainConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), params.id])
            redirect(action: "list")
            return
        }

        [domainConfigInstance: domainConfigInstance]
    }

    def update() {
        def domainConfigInstance = DomainConfig.get(params.id)
        if (!domainConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (domainConfigInstance.version > version) {
                domainConfigInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'domainConfig.label', default: 'DomainConfig')] as Object[],
                          "Another user has updated this DomainConfig while you were editing")
                render(view: "edit", model: [domainConfigInstance: domainConfigInstance])
                return
            }
        }

        domainConfigInstance.properties = params

        if (!domainConfigInstance.save(flush: true)) {
            render(view: "edit", model: [domainConfigInstance: domainConfigInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), domainConfigInstance.id])
        redirect(action: "show", id: domainConfigInstance.id)
    }

    def delete() {
        def domainConfigInstance = DomainConfig.get(params.id)
        if (!domainConfigInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), params.id])
            redirect(action: "list")
            return
        }

        try {
            domainConfigInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'domainConfig.label', default: 'DomainConfig'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	def cygnusFilteredSearch(){
		
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		log.info "params = "+params.toString();
		def result = universalSearchService.generateResult(params)
		render (view:"list",model: [domainConfigInstanceList: result.resultList, domainConfigInstanceTotal: result.resultListSize])
	}
}
