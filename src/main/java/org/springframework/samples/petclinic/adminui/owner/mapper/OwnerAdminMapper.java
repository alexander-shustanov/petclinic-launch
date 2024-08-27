package org.springframework.samples.petclinic.adminui.owner.mapper;

import org.mapstruct.*;
import org.springframework.samples.petclinic.adminui.owner.dto.OwnerAdminDto;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerAdminMapper {
	Owner toEntity(OwnerAdminDto ownerAdminDto);

	@Mapping(target = "petIds", expression = "java(petsToPetIds(owner.getPets()))")
	OwnerAdminDto toDto(Owner owner);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Owner partialUpdate(OwnerAdminDto ownerAdminDto, @MappingTarget Owner owner);

	default List<Integer> petsToPetIds(List<Pet> pets) {
		return pets.stream().map(Pet::getId).collect(Collectors.toList());
	}

	Owner updateWithNull(OwnerAdminDto ownerAdminDto, @MappingTarget Owner owner);
}
