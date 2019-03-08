package com.trillium.chaincode.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerMappingController {

	@RequestMapping("/")
	private String swaggerHome() {
		return "redirect:swagger-ui.html";
	}

}