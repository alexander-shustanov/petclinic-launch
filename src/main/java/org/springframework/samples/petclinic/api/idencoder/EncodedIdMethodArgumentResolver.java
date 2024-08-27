package org.springframework.samples.petclinic.api.idencoder;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;

public class EncodedIdMethodArgumentResolver extends PathVariableMethodArgumentResolver {

	private final IdEncoderApiRepository idEncoderApiRepository;

	public EncodedIdMethodArgumentResolver(IdEncoderApiRepository idEncoderApiRepository) {
		this.idEncoderApiRepository = idEncoderApiRepository;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(EncodedId.class);
	}


	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		EncodedId ann = parameter.getParameterAnnotation(EncodedId.class);
		return new NamedValueInfo(ann.name(), ann.required(), ValueConstants.DEFAULT_NONE);
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		String id = (String) super.resolveName(name, parameter, request);

		EncodedId ann = parameter.getParameterAnnotation(EncodedId.class);

		return idEncoderApiRepository.findEncoderByName(ann.encoder()).decode(id).getFirst().intValue();

	}
}
