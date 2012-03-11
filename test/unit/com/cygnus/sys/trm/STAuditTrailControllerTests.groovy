package com.cygnus.sys.trm



import org.junit.*
import grails.test.mixin.*

@TestFor(STAuditTrailController)
@Mock(STAuditTrail)
class STAuditTrailControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/STAuditTrail/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.STAuditTrailInstanceList.size() == 0
        assert model.STAuditTrailInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.STAuditTrailInstance != null
    }

    void testSave() {
        controller.save()

        assert model.STAuditTrailInstance != null
        assert view == '/STAuditTrail/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/STAuditTrail/show/1'
        assert controller.flash.message != null
        assert STAuditTrail.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/STAuditTrail/list'


        populateValidParams(params)
        def STAuditTrail = new STAuditTrail(params)

        assert STAuditTrail.save() != null

        params.id = STAuditTrail.id

        def model = controller.show()

        assert model.STAuditTrailInstance == STAuditTrail
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/STAuditTrail/list'


        populateValidParams(params)
        def STAuditTrail = new STAuditTrail(params)

        assert STAuditTrail.save() != null

        params.id = STAuditTrail.id

        def model = controller.edit()

        assert model.STAuditTrailInstance == STAuditTrail
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/STAuditTrail/list'

        response.reset()


        populateValidParams(params)
        def STAuditTrail = new STAuditTrail(params)

        assert STAuditTrail.save() != null

        // test invalid parameters in update
        params.id = STAuditTrail.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/STAuditTrail/edit"
        assert model.STAuditTrailInstance != null

        STAuditTrail.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/STAuditTrail/show/$STAuditTrail.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        STAuditTrail.clearErrors()

        populateValidParams(params)
        params.id = STAuditTrail.id
        params.version = -1
        controller.update()

        assert view == "/STAuditTrail/edit"
        assert model.STAuditTrailInstance != null
        assert model.STAuditTrailInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/STAuditTrail/list'

        response.reset()

        populateValidParams(params)
        def STAuditTrail = new STAuditTrail(params)

        assert STAuditTrail.save() != null
        assert STAuditTrail.count() == 1

        params.id = STAuditTrail.id

        controller.delete()

        assert STAuditTrail.count() == 0
        assert STAuditTrail.get(STAuditTrail.id) == null
        assert response.redirectedUrl == '/STAuditTrail/list'
    }
}
