package org.springframework.samples.petclinic.api.owner.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/**
 * DTO for {@link org.springframework.samples.petclinic.owner.Visit}
 */
public record VisitCreateDto(LocalDate date, @NotBlank String description,
							 String petId, String petOwnerId) {
}
