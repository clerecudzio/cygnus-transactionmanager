package com.cygnus.sys



import org.junit.*
import grails.test.mixin.*

@TestFor(DomainConfigController)
@Mock(DomainConfig)
class DomainConfigControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/domainConfig/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.domainConfigInstanceList.size() == 0
        assert model.domainConfigInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.domainConfigInstance != null
    }

    void testSave() {
        controller.save()

        assert model.domainConfigInstance != null
        assert view == '/domainConfig/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/domainConfig/show/1'
        assert controller.flash.message != null
        assert DomainConfig.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/domainConfig/list'


        populateValidParams(params)
        def domainConfig = new DomainConfig(params)

        assert domainConfig.save() != null

        params.id = domainConfig.id

        def model = controller.show()

        assert model.domainConfigInstance == domainConfig
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/domainConfig/list'


        populateValidParams(params)
        def domainConfig = new DomainConfig(params)

        assert domainConfig.save() != null

        params.id = domainConfig.id

        def model = controller.edit()

        assert model.domainConfigInstance == domainConfig
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/domainConfig/list'

        response.reset()


        populateValidParams(params)
        def domainConfig = new DomainConfig(params)

        assert domainConfig.save() != null

        // test invalid parameters in update
        params.id = domainConfig.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/domainConfig/edit"
        assert model.domainConfigInstance != null

        domainConfig.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/domainConfig/show/$domainConfig.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        domainConfig.clearErrors()

        populateValidParams(params)
        params.id = domainConfig.id
        params.version = -1
        controller.update()

        assert view == "/domainConfig/edit"
        assert model.domainConfigInstance != null
        assert model.domainConfigInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/domainConfig/list'

        response.reset()

        populateValidParams(params)
        def domainConfig = new DomainConfig(params)

        assert domainConfig.save() != null
        assert DomainConfig.count() == 1

        params.id = domainConfig.id

        controller.delete()

        assert DomainConfig.count() == 0
        assert DomainConfig.get(domainConfig.id) == null
        assert response.redirectedUrl == '/domainConfig/list'
    }
}
