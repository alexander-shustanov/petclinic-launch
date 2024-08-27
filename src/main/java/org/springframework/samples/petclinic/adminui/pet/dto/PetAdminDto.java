package org.springframework.samples.petclinic.adminui.pet.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link org.springframework.samples.petclinic.owner.Pet}
 */
public class PetAdminDto {
	private final Integer id;
	private final String name;
	private final LocalDate birthDate;
	private final Integer typeId;
	private final Integer ownerId;

	public PetAdminDto(Integer id, String name, LocalDate birthDate, Integer typeId, Integer ownerId) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.typeId = typeId;
		this.ownerId = ownerId;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PetAdminDto entity = (PetAdminDto) o;
		return Objects.equals(this.id, entity.id) &&
			   Objects.equals(this.name, entity.name) &&
			   Objects.equals(this.birthDate, entity.birthDate) &&
			   Objects.equals(this.typeId, entity.typeId) &&
			   Objects.equals(this.ownerId, entity.ownerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, birthDate, typeId, ownerId);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			   "id = " + id + ", " +
			   "name = " + name + ", " +
			   "birthDate = " + birthDate + ", " +
			   "typeId = " + typeId + ", " +
			   "ownerId = " + ownerId + ")";
	}
}
