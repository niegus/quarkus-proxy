package es.niegus.quarkusproxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface BackendService {

    @GET
    @Path("{var:.*}")
    String getBackend(@PathParam("var") String path);

}
