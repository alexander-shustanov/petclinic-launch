package org.springframework.samples.petclinic.api.owner.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.samples.petclinic.api.owner.dto.OwnerInfoDto;
import org.springframework.samples.petclinic.api.owner.dto.OwnerKeyFieldDto;
import org.springframework.samples.petclinic.owner.Owner;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OwnerApiMapper {

	@Autowired
	private IdEncoderApiRepository idEncoderApiRepository;

	public abstract Owner toEntity(OwnerKeyFieldDto ownerKeyFieldDto);

	public abstract OwnerKeyFieldDto toOwnerKeyFieldDto(Owner owner);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public abstract Owner partialUpdate(OwnerKeyFieldDto ownerKeyFieldDto, @MappingTarget Owner owner);

	@Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
	public abstract OwnerInfoDto toOwnerInfoDto(Owner owner);

	@Named("encodeId")
	protected String encodeId(Integer ownerId) {
		return idEncoderApiRepository.findEncoderByName("owner").encode(List.of(ownerId.longValue()));
	}
}
