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
	def sessionFactory
	def withTransaction

	def checkAuditLoggingEnabled(metaClassDomainName){
		def boolean isEnabled = false
		def domainConfig
		try{
			domainConfig = DomainConfig.findByDomainClassName(metaClassDomainName)
			log.info "result of checkAuditLogging ${domainConfig} with ${metaClassDomainName}"
		}catch(Exception ex){
			log.error "error when run checkAuditLoggingEnabled ${ex.getMessage()}"
		}
		return domainConfig? true : false
	}

	def insertIntoAuditTrail(Map inputMap,domainClassName,fullClassName,processInfo){
		try{
			def sb = new StringBuffer();
			//if domain class logging is enabled
			if(checkAuditLoggingEnabled(fullClassName)){
				STAuditTrail.withTransaction{
					def stAuditTrail = new STAuditTrail(domainClassName: domainClassName,accessDtm: new Date(),modifiedBy: "cygnus-system",process:processInfo)

					inputMap.each{ value ->

						sb << "value = " + value.key +" , "+ value.value

						stAuditTrail.addToTdAuditTrail(new STdAuditTrail(columnName: "${value.key}",columnValue: "${value.value}"))

					}
					log.info "Logging $domainClassName \n contents: $sb"
					stAuditTrail.save(failOnError:true)
				}
			}
		}catch(Exception ex){
			log.error "error insert into AuditTrail ${ex.getMessage()}"
			throw ex

		}
	}



}
