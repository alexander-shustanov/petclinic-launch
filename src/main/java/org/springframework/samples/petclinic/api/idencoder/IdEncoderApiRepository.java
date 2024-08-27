package org.springframework.samples.petclinic.api.idencoder;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.sqids.Sqids;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IdEncoderApiRepository implements InitializingBean {

	private final Map<String, Sqids> idEncoderMaps = new ConcurrentHashMap<>();

	private final IdEncoderConfigurationProperties idEncoderConfigurationProperties;

	public IdEncoderApiRepository(IdEncoderConfigurationProperties idEncoderConfigurationProperties) {
		this.idEncoderConfigurationProperties = idEncoderConfigurationProperties;
	}

	public Sqids findEncoderByName(String name) {
		return idEncoderMaps.get(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		idEncoderConfigurationProperties.getEncoder()
			.forEach((s, idEncoder) -> idEncoderMaps.put(s,
				Sqids.builder().alphabet(idEncoder.getAlphabet())
					.minLength(idEncoder.getMinLength()).build()));
	}
}
