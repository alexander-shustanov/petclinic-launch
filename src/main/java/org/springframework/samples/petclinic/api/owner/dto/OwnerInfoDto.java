package org.springframework.samples.petclinic.api.owner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for {@link org.springframework.samples.petclinic.owner.Owner}
 */
public record OwnerInfoDto(String id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String address,
						   @NotBlank String city,
						   @Pattern(message = "Telephone must be a 10-digit number", regexp = "\\d{10}") @NotBlank String telephone) {
}
