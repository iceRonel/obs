package com.ronel.obs;

import com.ronel.obs.post.JsonPlaceHolderService;
import com.ronel.obs.post.Post;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@SpringBootApplication
public class ObsApplication {

	Logger logger = LoggerFactory.getLogger(ObsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ObsApplication.class, args);
	}

	@Bean
	JsonPlaceHolderService jsonPlaceHolderService(){

		RestClient restClient  = RestClient.create("https://jsonplaceholder.typicode.com");

		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(JsonPlaceHolderService.class);
	}


	/*@Bean
	CommandLineRunner commandLineRunner(JsonPlaceHolderService jsonPlaceHolderService, ObservationRegistry observationRegistry){
		return args -> {
			Observation.createNotStarted("posts.load-all-posts",observationRegistry)
					.lowCardinalityKeyValue("author","Ronel")
					.contextualName("post.load-all-posts")
					.observe(jsonPlaceHolderService::findAll);

		};*/

	@Bean
	@Observed(name = "posts.load-all-posts",contextualName = "post.find-all")
	CommandLineRunner commandLineRunner(JsonPlaceHolderService jsonPlaceHolderService){
		return args -> {
			jsonPlaceHolderService.findAll();

		};
	}
}
