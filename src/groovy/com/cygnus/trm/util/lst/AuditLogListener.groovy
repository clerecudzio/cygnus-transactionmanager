package com.cygnus.trm.util.lst

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.Initializable;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;


import com.cygnus.sys.trm.AuditService
import com.cygnus.sys.trm.STAuditTrail
import com.cygnus.sys.trm.STdAuditTrail
import grails.util.Environment


class AuditLogListener extends AuditLog implements PostDeleteEventListener,PreDeleteEventListener, PostInsertEventListener, PostUpdateEventListener, Initializable {


	def auditService = new AuditService()


	private def parseEventModel(eventModel) throws Exception {
		String[] names = eventModel.getPersister().getPropertyNames()
		Object[] state = eventModel.getState()
		def map = makeMap(names, state)
		def returnMap = [:]
		def entity = eventModel.getEntity()

		def entityName = entity.getClass().getName()
		returnMap.put("name",entityName)
		returnMap.put("actor",this.getActor())
		returnMap.put("propertyArray",map)

		return returnMap
	}

	private def makeMap(String[] names, Object[] state)  throws Exception  {
		def map = [:]
		for (int ii = 0; ii < names.length; ii++) {
			map[names[ii]] = state[ii]
		}
		return map
	}

	private def insertAuditTrail(inputMap,domainClassName) throws Exception{
		def stAuditTrail = new STAuditTrail(domainClassName: domainClassName,accessDtm: new Date(),modifiedBy: "cygnus-system",process:this.getActor())
		def sb = new StringBuffer()
		inputMap.each{ value ->
			//null props wont be saved
			if(value.value){
				sb <<  " ${value.key}  ,  ${value.value} \n"

				stAuditTrail.addToTdAuditTrail(new STdAuditTrail(columnName: "${value.key}",columnValue: "${value.value}"))
			}
		}

		log.info "inserting audit trail for ${domainClassName} \n contents : \n$sb "
		saveAuditLog(stAuditTrail)
	}

	private def generateLogging(event) throws Exception{
		def sb = new StringBuffer();
		def parseResult = parseEventModel(event)
		def isAuditable
		sb << parseResult.name +"="
		sb << " "+parseResult.propertyArray.toString()
		if (!(parseResult.name in [
			'com.cygnus.sys.trm.STAuditTrail',
			'com.cygnus.sys.trm.STdAuditTrail',
			'com.cygnus.sys.DomainConfig'
		])){isAuditable = true
		}
		def returnMap = [:]

		returnMap.put("isExecuteLogging",isAuditable)
		returnMap.put("logArray",sb)
		returnMap.put("resultArray",parseResult)

		return returnMap
	}


	public void onPostInsert(final PostInsertEvent event) {
		try{
			def returnMap = generateLogging(event)
			log.info "inserted object : [${returnMap.logArray}]"
			if(returnMap.isExecuteLogging){
				insertAuditTrail(returnMap.resultArray.propertyArray,returnMap.resultArray.name)
			}
		}catch(Exception e){
			log.error "error on execute post insert ${e.getMessage()}"
			e.printStackTrace();
		}
		// logic after insert
		return
	}

	public void onPostUpdate(final PostUpdateEvent event) {
		try{
			def returnMap = generateLogging(event)
			log.info "update object  : [${returnMap.logArray}]"
			if(returnMap.isExecuteLogging){
				insertAuditTrail(returnMap.resultArray.propertyArray,returnMap.resultArray.name)

			}
		}catch(Exception e){
			log.error "error on execute post update ${e.getMessage()}"
			e.printStackTrace();
		}

		// logic after update
		return
	}

	public void onPostDelete(final PostDeleteEvent event) {
		log.info "executing post Delete "
		// logic after delete
		return
	}


	void init() {

		log.info AuditLogListener.class.getCanonicalName() + " initializing AuditLogListener... "
	}

	public void initialize(final Configuration config) {
		log.info "initializing cygnus audit logging and default transaction logging"
		// TODO Auto-generated method stub
		return
	}

	@Override
	public boolean onPreDelete(PreDeleteEvent arg0) {
		log.info "executing pre Delete "+ arg0.toString();
		// TODO Auto-generated method stub
		return false;
	}
}
