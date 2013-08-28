package org.jboss.test.ws.jaxws.samples.wsse.policy.username.client;

import java.util.Map;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.BindingProvider;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.jboss.test.ws.jaxws.samples.wsse.policy.wsdl.SecurityService;
import org.jboss.test.ws.jaxws.samples.wsse.policy.wsdl.ServiceIface;

/**
 * Hello world!
 *
 */
public class UsernameTokenClient {

   public static void main(String[] args) {
      SecurityService service = new SecurityService();
      ServiceIface port = service.getSecurityServicePort();

      String endpointurl = "https://localhost:8443/wsse-policy-username/SecurityService/ServiceImpl";      
      final Map<String, Object> rq = ((BindingProvider) port).getRequestContext();
      rq.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointurl);
      
      //rq.put("ws-security.username", "joe");  // joe - authentication failure
      rq.put("ws-security.username", "kermit"); // kermit - authentication ok
      rq.put("ws-security.callback-handler", UsernamePasswordCallback.class.getName());      
      
      // Configure client https transport
      final Client client = ClientProxy.getClient(port);
      final HTTPConduit conduit = (HTTPConduit) client.getConduit();
      TLSClientParameters tcp = new TLSClientParameters();
      tcp.setDisableCNCheck(true);

      TrustManager[] trustAllCerts = new TrustManager[]{
         new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
               return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
         }
      };
      tcp.setTrustManagers(trustAllCerts);
      conduit.setTlsClientParameters(tcp);

      HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
      httpClientPolicy.setConnectionTimeout(36000);
      httpClientPolicy.setAllowChunking(false);
      httpClientPolicy.setReceiveTimeout(32000);
      conduit.setClient(httpClientPolicy);

      System.out.println(port.sayHello());
   }
}
