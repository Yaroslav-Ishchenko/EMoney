package ua.ishchenko.client.services;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import ua.ishchenko.common.wsbeans.User;
import ua.ishchenko.common.wsbeans.WSResultCode;

@Path("/users")
public interface UserService extends RestService {

    @POST
    void createUser(User user, MethodCallback<WSResultCode> callback);

    @POST
    @Path("/list")
    void createUsers(List<User> users, MethodCallback<WSResultCode> callback);

    @GET
    @Path("/{id}")
    void findById(@PathParam("id") Long id, MethodCallback<User> callback);

    @GET
    //@Consumes(MediaType.APPLICATION_JSON)
    void getUsers(MethodCallback<List<User>> callback);

    @PUT
    void updateUserById(User user, MethodCallback<WSResultCode> callback);

    @DELETE
    @Path("/{id}")
    void deleteUserById(@PathParam("id") Long id,
                        MethodCallback<WSResultCode> callback);

    @DELETE
    void deleteUsers(MethodCallback<WSResultCode> callback);

    @Path("/{userid}/withdraw")
    @POST
    void withdraw(@PathParam("userid") Long userid,
                  @QueryParam("amount") Long withdrawAmount,
                  MethodCallback<WSResultCode> callback);

    @Path("/{userid}/deposit")
    @POST
    void deposit(@PathParam("userid") Long userid,
                 @QueryParam("amount") Long depositAmount,
                 MethodCallback<WSResultCode> callback);
}
