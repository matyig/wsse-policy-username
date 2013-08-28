package org.jboss.test.ws.jaxws.samples.wsse.policy.basic;

import javax.ejb.Stateless;

@Stateless
public class HelloService {
   
   public String sayHello() {
      return "injected and secured HelloWorld";
   }
   
}
