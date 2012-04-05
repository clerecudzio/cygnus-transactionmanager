package com.cygnus.trm.util.lst

import groovy.lang.Closure;

import org.apache.commons.logging.Log;
import org.hibernate.SessionFactory;
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.web.context.request.RequestContextHolder;
import org.hibernate.SessionFactory
import com.cygnus.sys.trm.AuditService
import com.cygnus.sys.trm.STAuditTrail
import com.cygnus.sys.trm.STdAuditTrail
import org.hibernate.Session


/**
 * This Class contains copy-pasted method from audit logging method by shawn hartsock
 * credits to him : 
 * https://hartsock.ci.cloudbees.com/job/grails-audit-logging/ws/
 * @author haris
 *
 */
class AuditLog {
	public static final Log log = LogFactory.getLog(AuditLogListener.class);
	boolean verbose = true // in Config.groovy auditLog.verbose = true
	SessionFactory sessionFactory
	Closure actorClosure
	def transactional = true
	String sessionAttribute
	String actorKey
	Long truncateLength
	def auditService = new AuditService();
	
	/**
	* if verbose is set to 'true' then you get a log event on
	* each individually changed column/field sent to the database
	* with a record of the old value and the new value.
	*/
   void setVerbose(final boolean verbose) {
	   this.verbose = verbose
   }
	
	void setActorClosure(Closure closure) {
		closure.delegate = this
		closure.properties.putAt("log", this.log)
		this.actorClosure = closure
	}

	String getActor() {
		def actor = null
		try {
			if (actorClosure) {
				def attr = RequestContextHolder?.getRequestAttributes()
				def session = attr?.session
				if (attr && session) {
					actor = actorClosure.call(attr, session)
				}
				else {
					// no session or attributes mean this is invoked from a Service, Quartz Job, or other headless-operation
					actor = 'system'
				}
			}
		} catch (Exception ex) {
			log.error "The auditLog.actorClosure threw this exception: ${ex.message}"
			ex.printStackTrace()
			log.error "The auditLog.actorClosure will be disabled now."
			actorClosure = null
		}
		return actor?.toString()
	}
	
	
	def saveAuditLog = { STAuditTrail audit ->
	
		log.info audit
		try {
			// NOTE: you simply cannot use the session that GORM is using for some
			// audit log events. That's because some audit events occur *after* a
			// transaction has committed. Late session save actions can invalidate
			// sessions or you can essentially roll-back your audit log saves. This is
			// why you have to open your own session and transaction on some
			// transactional databases and not others.
			Session session = sessionFactory.openSession()
			log.trace "opened new session for audit log persistence"
			def trans = null
			if (transactional) {
				trans = session.beginTransaction()
				log.trace " + began transaction "
			}
			if(auditService.checkAuditLoggingEnabled(audit.domainClassName)){
				
			def saved = session.merge(audit)
			log.debug " + saved log entry id:'${saved.id}'."
			session.flush()
			log.trace " + flushed session"
			}
			if (transactional) {
				trans?.commit()
				log.trace " + committed transaction"
				
			}
			session.close()
			log.trace "session closed"
		} catch (org.hibernate.HibernateException ex) {
			log.error "AuditLogEvent save has failed!"
			log.error ex.message
			log.error audit.errors
		}
		return
	}


}
