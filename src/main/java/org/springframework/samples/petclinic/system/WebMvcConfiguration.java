package org.springframework.samples.petclinic.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.api.idencoder.EncodedIdMethodArgumentResolver;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

	private final IdEncoderApiRepository idEncoderApiRepository;

	public WebMvcConfiguration(IdEncoderApiRepository idEncoderApiRepository) {
		this.idEncoderApiRepository = idEncoderApiRepository;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new EncodedIdMethodArgumentResolver(idEncoderApiRepository));
	}
}
