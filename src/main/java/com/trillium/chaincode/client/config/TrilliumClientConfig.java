package com.trillium.chaincode.client.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrilliumClientConfig
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }

}
