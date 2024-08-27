package org.springframework.samples.petclinic.api.owner.dto;

import java.time.LocalDate;

/**
 * DTO for {@link org.springframework.samples.petclinic.owner.Pet}
 */
public record PetInfoDto(String id, String name, LocalDate birthDate, String typeName) {
}
