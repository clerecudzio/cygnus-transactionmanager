package com.cygnus.sys.trm



import org.junit.*
import grails.test.mixin.*

@TestFor(STdAuditTrailController)
@Mock(STdAuditTrail)
class STdAuditTrailControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/STdAuditTrail/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.STdAuditTrailInstanceList.size() == 0
        assert model.STdAuditTrailInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.STdAuditTrailInstance != null
    }

    void testSave() {
        controller.save()

        assert model.STdAuditTrailInstance != null
        assert view == '/STdAuditTrail/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/STdAuditTrail/show/1'
        assert controller.flash.message != null
        assert STdAuditTrail.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/STdAuditTrail/list'


        populateValidParams(params)
        def STdAuditTrail = new STdAuditTrail(params)

        assert STdAuditTrail.save() != null

        params.id = STdAuditTrail.id

        def model = controller.show()

        assert model.STdAuditTrailInstance == STdAuditTrail
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/STdAuditTrail/list'


        populateValidParams(params)
        def STdAuditTrail = new STdAuditTrail(params)

        assert STdAuditTrail.save() != null

        params.id = STdAuditTrail.id

        def model = controller.edit()

        assert model.STdAuditTrailInstance == STdAuditTrail
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/STdAuditTrail/list'

        response.reset()


        populateValidParams(params)
        def STdAuditTrail = new STdAuditTrail(params)

        assert STdAuditTrail.save() != null

        // test invalid parameters in update
        params.id = STdAuditTrail.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/STdAuditTrail/edit"
        assert model.STdAuditTrailInstance != null

        STdAuditTrail.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/STdAuditTrail/show/$STdAuditTrail.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        STdAuditTrail.clearErrors()

        populateValidParams(params)
        params.id = STdAuditTrail.id
        params.version = -1
        controller.update()

        assert view == "/STdAuditTrail/edit"
        assert model.STdAuditTrailInstance != null
        assert model.STdAuditTrailInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/STdAuditTrail/list'

        response.reset()

        populateValidParams(params)
        def STdAuditTrail = new STdAuditTrail(params)

        assert STdAuditTrail.save() != null
        assert STdAuditTrail.count() == 1

        params.id = STdAuditTrail.id

        controller.delete()

        assert STdAuditTrail.count() == 0
        assert STdAuditTrail.get(STdAuditTrail.id) == null
        assert response.redirectedUrl == '/STdAuditTrail/list'
    }
}
