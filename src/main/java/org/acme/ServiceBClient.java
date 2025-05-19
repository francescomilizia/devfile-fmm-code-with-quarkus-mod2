package org.acme;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/cpu")
@RegisterRestClient(configKey="cpu-burn")
public interface ServiceBClient {

    @GET
    @Path("/burn")
    String callEndpoint();
}
