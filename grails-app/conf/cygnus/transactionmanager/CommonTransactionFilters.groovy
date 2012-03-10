package cygnus.transactionmanager

class CommonTransactionFilters {

	def sessionFactory
	/**
	 * Filters for All transaction
	 * - Injected session
	 * 
	 */
	def filters = {

		startTransaction(controller:'com.cygnus.trx.*', action:'*') {
			before = 
			{ 
				sessionFactory.getCurrentSession.beginTransaction()
				}
		}
	}
}
