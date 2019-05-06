package es.niegus.quarkusproxy;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("{path:.*}")
public class ProxyResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    @RestClient
    BackendService backendService;

    @GET
    @RolesAllowed({"Echoer", "Subscriber"})
    @Produces(MediaType.TEXT_PLAIN)
    public Object helloGet(@Context SecurityContext sec, @PathParam("path") String path) {
        Principal user = sec.getUserPrincipal();
        String name = user != null ? user.getName() : "anonymous";
        boolean hasJWT = jwt != null;
        String helloReply = String.format("hello + %s, isSecure: %s, authScheme: %s, hasJWT: %s", name, sec.isSecure(), sec.getAuthenticationScheme(), hasJWT);
        System.out.println(helloReply);
        return proxyCall(path);
    }

//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    public String helloPost() {
//        return proxyCall();
//    }
//
//    @PUT
//    @Produces(MediaType.TEXT_PLAIN)
//    public String helloPut() {
//        return proxyCall();
//    }
//
//    @DELETE
//    @Produces(MediaType.TEXT_PLAIN)
//    public String helloDelete() {
//        return proxyCall();
//    }
//
//    @PATCH
//    @Produces(MediaType.TEXT_PLAIN)
//    public String helloPatch() {
//        return proxyCall();
//    }

    private String proxyCall(String path) {
        return backendService.getBackend(path);
    }
}