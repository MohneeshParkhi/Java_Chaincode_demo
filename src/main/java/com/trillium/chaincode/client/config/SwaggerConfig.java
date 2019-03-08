package com.trillium.chaincode.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trillium.chaincode.client.TrilliumChaincodeClientServiceApplication;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    /**
     * For creating Documentation by using Docket Bean.
     * <p> <b>Documentation Type  </b>  : Swagger 2 </p>
     * <p> <b>Base Package        </b>  : Root package of API </p>
     * <p> <b>Path                </b>  : All path </p> 
     * 
     * @return {@link Docket}
     */
    @Bean
    public Docket api()
    {
        String basePackage = TrilliumChaincodeClientServiceApplication.class.getPackage().getName();

        return new Docket(DocumentationType.SWAGGER_2)
            .select().apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any()).build();
    }
}