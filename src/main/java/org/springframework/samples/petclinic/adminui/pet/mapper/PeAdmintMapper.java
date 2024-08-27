package org.springframework.samples.petclinic.adminui.pet.mapper;

import org.mapstruct.*;
import org.springframework.samples.petclinic.adminui.pet.dto.PetAdminDto;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PeAdmintMapper {
	@Mapping(source = "ownerId", target = "owner.id")
	@Mapping(source = "typeId", target = "type.id")
	Pet toEntity(PetAdminDto petAdminDto);

	@InheritInverseConfiguration(name = "toEntity")
	PetAdminDto toDto(Pet pet);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "ownerId", target = "owner")
	@Mapping(source = "typeId", target = "type")
	Pet partialUpdate(PetAdminDto petAdminDto, @MappingTarget Pet pet);

	default PetType createPetType(Integer typeId) {
		if (typeId == null) {
			return null;
		}
		PetType petType = new PetType();
		petType.setId(typeId);
		return petType;
	}

	default Owner createOwner(Integer ownerId) {
		if (ownerId == null) {
			return null;
		}
		Owner owner = new Owner();
		owner.setId(ownerId);
		return owner;
	}

	@InheritConfiguration(name = "partialUpdate")
	Pet updateWithNull(PetAdminDto petAdminDto, @MappingTarget Pet pet);
}
