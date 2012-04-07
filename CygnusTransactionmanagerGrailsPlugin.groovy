import com.cygnus.trm.util.lst.AuditLogListener
import org.codehaus.groovy.grails.orm.hibernate.HibernateEventListeners

class CygnusTransactionmanagerGrailsPlugin{

	// the plugin version
	def version = "0.1"
	// the version or versions of Grails the plugin is designed for
	def grailsVersion = "2.0 > *"
	// the other plugins this plugin depends on
	def dependsOn = [:]
	// resources that are excluded from plugin packaging
	def pluginExcludes = [
		"grails-app/views/error.gsp"
	]

	// TODO Fill in these fields
	def title = "Cygnus Transaction Manager Plugin" // Headline display name of the plugin
	def author = "Haris Ibrahim"
	def authorEmail = "clerecudzio@gmail.com"
	def description = '''\
Cygnus Transaction Manager Plugin.
'''
	def loadAfter = ['core', 'hibernate']
	// URL to the plugin's documentation
	def documentation = "http://grails.org/plugin/cygnus-transactionmanager"

	// Extra (optional) plugin metadata

	// License: one of 'APACHE', 'GPL2', 'GPL3'
	//    def license = "APACHE"

	// Details of company behind the plugin (if there is one)
	//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

	// Any additional developers beyond the author specified above.
	//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

	// Location of the plugin's issue tracker.
	//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

	// Online location of the plugin's browseable source code.
	//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

	def doWithWebDescriptor = { xml ->
		// TODO Implement additions to web.xml (optional), this event occurs before
	}

	def doWithSpring = {
		// TODO Implement runtime spring config (optional)
		
		
	}

	def doWithDynamicMethods = { ctx ->
		// TODO Implement registering dynamic methods to classes (optional)
	
	}
	/**
	 * Credit to :
	 * http://stackoverflow.com/questions/1956115/hooking-into-grails-domain-object-save
	 * @param listeners
	 * @param listener
	 * @param type
	 * @return
	 */
	private addEventTypeListener(listeners, listener, type) {
		def typeProperty = "${type}EventListeners"
		def typeListeners = listeners."${typeProperty}"
		def expandedTypeListeners = new Object[typeListeners.length + 1]
		System.arraycopy(typeListeners, 0, expandedTypeListeners, 0, typeListeners.length)
		expandedTypeListeners[-1] = listener
		listeners."${typeProperty}" = expandedTypeListeners
	}
	
	def doWithApplicationContext = { applicationContext ->
		// TODO Implement post initialization spring config (optional)
		def listeners = applicationContext.sessionFactory.eventListeners
		def listener = new AuditLogListener()
		listener.sessionFactory = applicationContext.getBean('sessionFactory')
		[
			'postInsert',
			'postUpdate',
			'postDelete'
		].each({
			addEventTypeListener(listeners, listener, it)
		})
		//Atomikos configuration

		application.config.grails.plugin.atomikos.uts = [
					'com.atomikos.icatch.console_file_name': 'tm.out',
					'com.atomikos.icatch.log_base_name': 'tmlog',
					'com.atomikos.icatch.tm_unique_name': 'myTransactionManager',
					'com.atomikos.icatch.console_log_level': 'ERROR',
					'com.atomikos.icatch.log_base_dir': 'target/atomikos',
					'com.atomikos.icatch.output_dir': 'target/atomikos',
					'com.atomikos.icatch.force_shutdown_on_vm_exit': 'true']

	}

	def onChange = { event ->
		// TODO Implement code that is executed when any artefact that this plugin is
		// watching is modified and reloaded. The event contains: event.source,
		// event.application, event.manager, event.ctx, and event.plugin.
	}

	def onConfigChange = { event ->
		// TODO Implement code that is executed when the project configuration changes.
		// The event is the same as for 'onChange'.
	}

	def onShutdown = { event ->
		// TODO Implement code that is executed when the application shuts down (optional)
	}
}
