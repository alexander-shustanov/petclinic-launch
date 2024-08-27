package org.springframework.samples.petclinic.api.owner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for {@link org.springframework.samples.petclinic.owner.Owner}
 */
public record OwnerKeyFieldsDto(@NotBlank String firstName, @NotBlank String lastName,
								@Pattern(message = "Telephone must be a 10-digit number", regexp = "\\d{10}") @NotBlank String telephone) {
}
