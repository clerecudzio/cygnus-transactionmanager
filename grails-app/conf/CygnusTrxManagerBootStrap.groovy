import com.cygnus.sys.DomainConfig
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass


class CygnusTrxManagerBootStrap {
	def grailsApplication

	def init = { servletContext ->

		environments{
			development{
				ExpandoMetaClass.enableGlobally()
				//insert all tables into domainConfig for identification
				def notAudited = [
					'com.cygnus.sys.trm.STAuditTrail',
					'com.cygnus.sys.trm.STdAuditTrail'
				]

				grailsApplication.domainClasses.each{ gc ->
					def domainClass = gc.getClazz()
					if(gc.clazz.name in notAudited) new DomainConfig(domainClassName: gc.clazz.name, auditEnabled:false).save(failOnError:true)

					else	new DomainConfig(domainClassName: gc.clazz.name).save(failOnError:true)
				}



			}
			test{

			}
			production{

			}
		}
	}

	def destroy = { servletContext ->

	}
}
