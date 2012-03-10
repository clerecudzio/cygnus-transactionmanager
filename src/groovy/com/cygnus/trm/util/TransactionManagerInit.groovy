package com.cygnus.trm.util
import com.cygnus.sys.trm.AuditService
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.ApplicationContext
import org.apache.commons.logging.LogFactory

/**
 * Class for intercepting all method calls required for logging, works from plugins doWithDynamicMethod section
 * @author haris.ibrahim
 *
 */
class TransactionManagerInit {
	private static final log = LogFactory.getLog(this)
	def grailsApplication = ApplicationHolder.application
	def aService = new AuditService()
	
	/**
	 * Intercept invokeMethod to all application activities and insert to AuditTrail
	 * @param ctx ApplicationContext
	 * @return
	 * 
	 * Format :
	 * FullClassName - -->
	 * execute [fullDomainClassName : actionName] -> [tableName: tableName] [arguments: arguments]
	 * [propertyName : columnName : propertyType] = [propertyValue] , [propertyName : columnName :propertyType] = [propertyValue], ...
	 */
	def doWithDynamicMethodInit(ApplicationContext ctx) {
	
	

		grailsApplication.domainClasses.each { gc ->
			//iterating all domain classes
			def sessionFactory = ctx.sessionFactory
			def domainClass = gc.getClazz()
			def inputMap = [:]
			//adding function on every method calls on domain classes
			domainClass.metaClass.invokeMethod = {String name, args ->
				//lock only save and update methods
				def isExecutable = name in [
					'save',
					'update',
					'beforeInsert',
					'getAt'
				]
				def isAuditTrail = name in ['save','update']

				if(isExecutable){
					//if methods are executable, get variables needed for print
					def sb = new StringBuffer()
					sb << "\n"

					//obtain table name from domainClass's metaClass
					def hibernateMetaClass=sessionFactory.getClassMetadata(domainClass)
					def tableName = hibernateMetaClass.getTableName()
					sb << "execute [${delegate.class.name} : $name] -> [tableName: $tableName] [arguments: $args] \n"

					def dgdc = new DefaultGrailsDomainClass(delegate.class)

					//for each persistent method, obtain propertyName,columnName and value to insert
					dgdc.persistentProperties.each{ property ->
						def columnProps=hibernateMetaClass.getPropertyColumnNames(property.name)
						sb <<  "["+property.name+" : "+columnProps[0]+" : "+property.type+" ]"  + " = ["+delegate[property.name]+ "]\n"
						inputMap.put(property.name, delegate[property.name])
						
					}
					delegate.log.info "--> $sb"

				}
				def metaMethod = delegate.class.metaClass.getMetaMethod(name, args)
				if (!metaMethod) {
					delegate.log.warn "-- Method not found: $name($args)"
					return
				}

				try {
					//before invoking original method, check and insert auditTrail
					if(name in ['save','update']){
						if(aService.checkAuditLoggingEnabled(delegate.class.name)){
							log.info "insert audit trail for ${delegate.class.name}"
						}else{
							log.info "audit trail disabled for ${delegate.class.name}"
						}
					}
					
					def result = metaMethod.invoke(delegate, args)
					//					if (name == 'save' || name == 'update') delegate.log.info "<< ${delegate.class.name}.$name() result: $result"

					return result
				} catch (ex) {
					delegate.log.error "-- Exception occurred in $name: $ex.message"
					throw ex
				}
			}

		
		}
	}






}
