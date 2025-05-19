package org.acme;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/api")
@RegisterRestClient(configKey="be-service")
public interface ServiceBClient {

    @GET
    @Path("/cpu/burn")
    String callEndpoint();
}
