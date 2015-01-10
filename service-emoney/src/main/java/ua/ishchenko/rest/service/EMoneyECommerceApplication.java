package ua.ishchenko.rest.service;

import org.glassfish.jersey.jackson.JacksonFeature;

import org.glassfish.jersey.server.ResourceConfig;
/**
 * Registers the components to be used by the JAX-RS application  
 * 
 * @author jaros
 *
 */
public class EMoneyECommerceApplication extends ResourceConfig {

    /**
	* Register JAX-RS application components.
	*/	
	public EMoneyECommerceApplication(){
		register(JacksonFeature.class);	
		register(UserRestService.class);
		
	}
}
