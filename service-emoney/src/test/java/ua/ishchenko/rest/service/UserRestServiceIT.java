package ua.ishchenko.rest.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

public class UserRestServiceIT {

	@Test
	public void testGetUser() throws JsonGenerationException,
			JsonMappingException, IOException {

//		ClientConfig clientConfig = new ClientConfig();
//		clientConfig.register(JacksonFeature.class);
//
//		Client client = ClientBuilder.newClient(clientConfig);
//
//		WebTarget webTarget = client
//				.target("http://127.0.0.1:8888/emoney-service-0.0.1-SNAPSHOT/users/5400");
//
//		Builder request = webTarget.request(MediaType.APPLICATION_JSON);

//		Response response = request.get();
//		Assert.assertTrue(response.getStatus() == 200);

//		User user = response.readEntity(User.class);

//		ObjectMapper mapper = new ObjectMapper();
//		System.out
//				.print("\nReceived user from database *************************** "
//						+ mapper.writerWithDefaultPrettyPrinter()
//								.writeValueAsString(user));

	}
	
	@Test
	public void testGetUsers() throws JsonGenerationException,
			JsonMappingException, IOException {

//		ClientConfig clientConfig = new ClientConfig();
//		clientConfig.register(JacksonFeature.class);
//
//		Client client = ClientBuilder.newClient(clientConfig);
//
//		WebTarget webTarget = client
//				.target("http://127.0.0.1:8888/emoney-service-0.0.1-SNAPSHOT/users");
//
//		Builder request = webTarget.request();
//		request.header("Content-type", MediaType.APPLICATION_JSON);
//
//		Response response = request.get();
//		Assert.assertTrue(response.getStatus() == 200);
//
//		List<User> users = response
//				.readEntity(new GenericType<List<User>>() {
//				});
//
//		ObjectMapper mapper = new ObjectMapper();
//		System.out.print(mapper.writerWithDefaultPrettyPrinter()
//				.writeValueAsString(users));
//
//		Assert.assertTrue("At least one user is present",
//				users.size() > 0);
	}
	
}
