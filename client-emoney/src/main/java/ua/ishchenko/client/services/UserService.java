package ua.ishchenko.client.services;


import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import ua.ishchenko.common.wsbeans.User;
import ua.ishchenko.common.wsbeans.WSResultCode;

import javax.ws.rs.*;
import java.util.List;

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
    void getUsers(MethodCallback<List<String>> callback);

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
