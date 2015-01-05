/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.config.internal.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ratpack.config.internal.source.env.Environment;
import ratpack.launch.ServerConfig;

import javax.net.ssl.SSLContext;

public class ConfigurationModule extends SimpleModule {
  public ConfigurationModule(Environment environment) {
    super("ratpack-config");
    addDeserializer(ServerConfig.class, new ServerConfigDeserializer(environment));
    addDeserializer(SSLContext.class, new SSLContextDeserializer());
  }
}