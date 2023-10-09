package com.example.carrentingservicebackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.COLLECTION_JSON)
public class HateoasConfiguration {
}
