package org.webfabric.gae


import org.junit.{Before, After}
import com.google.appengine.tools.development.testing.{LocalServiceTestHelper, LocalDatastoreServiceTestConfig}
import com.google.appengine.api.datastore.{DatastoreService, DatastoreServiceFactory}

class LocalDatastore {
  val localServices = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)
  var datastoreService: DatastoreService = null

  @Before
  def setUp {
    localServices.setUp
    datastoreService = DatastoreServiceFactory.getDatastoreService
  }

  @After
  def tearDown() {
    localServices.tearDown
  }
}