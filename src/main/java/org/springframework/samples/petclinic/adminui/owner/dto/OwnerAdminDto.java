package org.springframework.samples.petclinic.adminui.owner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

/**
 * DTO for {@link org.springframework.samples.petclinic.owner.Owner}
 */
public record OwnerAdminDto(Integer id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String address,
							@NotBlank String city,
							@Pattern(message = "Telephone must be a 10-digit number", regexp = "\\d{10}") @NotBlank String telephone,
							List<Integer> petIds) {
}
