package com.cygnus.sys.trm

import org.apache.log4j.Logger;
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.apache.commons.logging.LogFactory
import com.cygnus.sys.DomainConfig
/**
 * 
 * @author haris
 *
 */
class AuditService {
	private static final log = LogFactory.getLog(this)
	def sessionFactory
	
	def checkAuditLoggingEnabled(metaClassDomainName){
		def boolean isEnabled = false
		def domainConfig
		try{
			domainConfig = DomainConfig.findByDomainClassName(metaClassDomainName)
			log.info "result of checkAuditLogging ${domainConfig} with ${metaClassDomainName}"

		}catch(Exception ex){
			log.error "error when run checkAuditLoggingEnabled ${ex.getMessage()}"
		}
		return domainConfig? domainConfig.auditEnabled : false
	}

	def insertIntoAuditTrail(GrailsDomainClass domainClass){
		def boolean isExecute = checkAuditLoggingEnabled(domainClass.name)
		isExecute?:{
			try{
				//if domain class logging is enabled
				def stAuditTrail = new STAuditTrail(domainClassName: domainClass.name,accessDtm: new Date(),modifiedBy: "cygnus-system")
				def hibernateMetaClass=sessionFactory.getClassMetadata(domainClass)

				domainClass.getPersistentProperties().each { perProps ->
					def columnProps=hibernateMetaClass.getPropertyColumnNames(perProps.name)

					def stdAuditTrail = new STdAuditTrail(columnName: columnProps[0],columnValue: delegate[perProps.name]  )
					stdAuditTrail.addTo(stAuditTrail)
				}
			}catch(Exception ex){
				log.error "error insert into AuditTrail ${ex.getMessage()}"
			}
		}
	}



}
