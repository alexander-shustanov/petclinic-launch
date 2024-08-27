package org.springframework.samples.petclinic.api.idencoder;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("petclinic.api.id")
public class IdEncoderConfigurationProperties {

	private Map<String, IdEncoder> encoder;

	public Map<String, IdEncoder> getEncoder() {
		return encoder;
	}

	public void setEncoder(Map<String, IdEncoder> encoder) {
		this.encoder = encoder;
	}

	public static class IdEncoder {
		private String alphabet;
		private Integer minLength;

		public String getAlphabet() {
			return alphabet;
		}

		public void setAlphabet(String alphabet) {
			this.alphabet = alphabet;
		}

		public Integer getMinLength() {
			return minLength;
		}

		public void setMinLength(Integer minLength) {
			this.minLength = minLength;
		}
	}
}
