package org.ratpackframework.test

import org.ratpackframework.Action
import org.ratpackframework.bootstrap.RatpackServer
import org.ratpackframework.bootstrap.RatpackServerBuilder
import org.ratpackframework.guice.GuiceBackedHandlerFactory
import org.ratpackframework.guice.internal.DefaultGuiceBackedHandlerFactory
import org.ratpackframework.guice.ModuleRegistry
import org.ratpackframework.routing.Handler
import org.ratpackframework.routing.Handlers
import org.ratpackframework.routing.Routing

import static org.ratpackframework.groovy.Closures.action
import static Handlers.routes
import static org.ratpackframework.routing.Handlers.fsContext

class DefaultRatpackSpec extends RatpackSpec {

  Closure<?> routing = {}
  Closure<?> modules = {}

  void routing(@DelegatesTo(Routing) Closure<?> configurer) {
    this.routing = configurer
  }

  void modules(@DelegatesTo(ModuleRegistry) Closure<?> configurer) {
    this.modules = configurer
  }

  @Override
  RatpackServer createApp() {
    DefaultGuiceBackedHandlerFactory appFactory = createAppFactory()
    def handler = createHandler()
    def modulesAction = createModulesAction()
    def withFsContext = decorateHandler(handler)
    Handler appHandler = appFactory.create(modulesAction, withFsContext)

    RatpackServerBuilder builder = new RatpackServerBuilder(appHandler)
    builder.port = 0
    builder.host = null
    builder.build()
  }

  protected GuiceBackedHandlerFactory createAppFactory() {
    new DefaultGuiceBackedHandlerFactory()
  }

  Handler decorateHandler(Handler handler) {
    fsContext(dirPath, handler)
  }

  protected Action<? super ModuleRegistry> createModulesAction() {
    action(modules)
  }

  protected Handler createHandler() {
    routes(action(routing))
  }

}
