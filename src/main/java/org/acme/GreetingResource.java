package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/invoke")
public class GreetingResource {

	
    @Inject
    @RestClient
    ServiceBClient serviceBClient;


	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
    	
    	String response=serviceBClient.callEndpoint();
    	
        return "Invocato BE. Resp: "+response;
    }
}
