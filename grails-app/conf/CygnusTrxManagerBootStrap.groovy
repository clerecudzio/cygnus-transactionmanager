import com.cygnus.sys.DomainConfig
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass


class CygnusTrxManagerBootStrap {
	def grailsApplication

	def init = { servletContext ->

		environments{
			development{
				ExpandoMetaClass.enableGlobally()
				//insert all tables into domainConfig for identification
				

				grailsApplication.domainClasses.each{ gc ->
					def domainClass = gc.getClazz()
					new DomainConfig(domainClassName: gc.clazz.name).save(failOnError:true)
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
