package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("/invoke")
public class Invoker {
    private static final Logger LOG = Logger.getLogger(Invoker.class);

	
    @Inject
    @RestClient
    ServiceBClient serviceBClient;


	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("Invoking CPU burn service...");
    	
    	String response=serviceBClient.callEndpoint();
        
    	LOG.info("Invoked CPU burn service...");

        return "Invocato BE. Resp: "+response;
    }
}
