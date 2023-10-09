package com.example.carrentingservicebackend.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    @Scope("prototype")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
