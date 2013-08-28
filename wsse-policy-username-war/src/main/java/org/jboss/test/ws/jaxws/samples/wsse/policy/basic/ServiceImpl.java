package org.jboss.test.ws.jaxws.samples.wsse.policy.basic;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import org.jboss.test.ws.jaxws.samples.wsse.policy.wsdl.ServiceIface;

import org.jboss.ws.api.annotation.EndpointConfig;

@Stateless
@WebService
(
   portName = "SecurityServicePort",
   serviceName = "SecurityService",
   wsdlLocation = "WEB-INF/wsdl/SecurityService.wsdl",
   targetNamespace = "http://www.jboss.org/jbossws/ws-extensions/wssecuritypolicy"
)
@EndpointConfig(configFile = "WEB-INF/jaxws-endpoint-config.xml", configName = "Custom WS-Security Endpoint")
public class ServiceImpl implements ServiceIface
{
   @Inject
   HelloService helloservice;
   
   public String sayHello()
   {
      return helloservice.sayHello();
   }
}
