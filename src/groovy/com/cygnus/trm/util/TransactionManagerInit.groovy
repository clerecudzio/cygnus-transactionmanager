package com.cygnus.trm.util
import com.cygnus.sys.trm.AuditService

import org.hibernate.SessionFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.ApplicationContext
/**
 * Class for intercepting all method calls required for logging, works from plugins doWithDynamicMethod section
 * @author haris.ibrahim
 *
 */
class TransactionManagerInit {
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

			//			domainClass.metaClass.'static'.invokeMethod = { String name, args ->
			//
			//					delegate.log.info "executing static on $name"
			//					def metaMethod = domainClass.metaClass.getStaticMetaMethod(name, args)
			//
			//					if(metaMethod) metaMethod.invoke(delegate, args)
			//			}

			//adding function on every method calls on domain classes
			domainClass.metaClass.invokeMethod = {String name, args ->
				//lock only save and update methods
				def isExecutable = name in [
					'save',
					'update'
				]
				def isAuditTrail = name in ['save', 'update']
				def hibernateMetaClass=sessionFactory.getClassMetadata(domainClass)
				def tableName = hibernateMetaClass.getTableName()

				if(isExecutable){
					//if methods are executable, get variables needed for print
					def sb = new StringBuffer()
					sb << "\n\n"

					//obtain table name from domainClass's metaClass
					sb << "execute [${delegate.class.name} : $name] -> [tableName: $tableName] [arguments: $args] \n"

					def dgdc = new DefaultGrailsDomainClass(delegate.class)

					//for each persistent method, obtain propertyName,columnName and value to insert
					dgdc.persistentProperties.each{ property ->
						if(delegate[property.name]){
							def columnProps=hibernateMetaClass.getPropertyColumnNames(property.name)
							sb <<  "["+property.name+" : "+columnProps[0]+" : "+property.type+" ]"  + " = "+delegate[property.name] 
							sb << " ,"
							inputMap.put(property.name, delegate[property.name])
						}
					}
					delegate.log.info "--> $sb \n\n"

				}
				def metaMethod = delegate.class.metaClass.getMetaMethod(name, args)
				if (!metaMethod) {
					delegate.log.warn "-- Method not found: $name($args)"
					return
				}

				try {
					//before invoking original method, check and insert auditTrail
					if(name in ['save', 'update']){
						delegate.log.info "inserting audit trail for ${tableName} \n"
						aService.insertIntoAuditTrail(inputMap,tableName,delegate.class.name,'system-bootstrapping')

					}

					def result = metaMethod.invoke(delegate, args)
					//					delegate.log.info "xxx ${delegate.class.name}.$name() result: $result"

					return result
				} catch (ex) {
					delegate.log.error "-- Exception occurred in $name: $ex.message"
					throw ex
				}
			}


		}
	}






}
