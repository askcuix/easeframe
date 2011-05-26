package com.easeframe.demo.miniservice.rs.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Required;

import com.easeframe.demo.miniservice.rs.dto.UserDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 使用Jersey Client的User REST客户端.
 * 
 * @author Chris
 *
 */
public class UserResourceClient {

	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}

	public List<UserDTO> getAllUser() {
		return client.path("/users").accept(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>() {
		});
	}

	public UserDTO getUser(Long id) {
		return client.path("/users/" + id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
	}

	public URI createUser(UserDTO user) {
		ClientResponse response = client.path("/users").entity(user, MediaType.APPLICATION_JSON).post(
				ClientResponse.class);
		if (201 == response.getStatus()) {
			return response.getLocation();
		} else {
			throw new UniformInterfaceException(response);
		}
	}
}
