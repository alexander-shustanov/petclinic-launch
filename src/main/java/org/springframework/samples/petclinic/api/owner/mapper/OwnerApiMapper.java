package org.springframework.samples.petclinic.api.owner.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.api.owner.dto.OwnerKeyFieldsDto;
import org.springframework.samples.petclinic.api.owner.dto.OwnerInfoDto;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.samples.petclinic.owner.Owner;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OwnerApiMapper {

	@Autowired
	private IdEncoderApiRepository encoderApiRepository;

	@Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
	public abstract OwnerInfoDto toOwnerInfoDto(Owner owner);

	public abstract Owner toEntity(OwnerKeyFieldsDto ownerKeyFieldsDto);

	public abstract OwnerKeyFieldsDto toOwnerKeyFieldsDto(Owner owner);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public abstract Owner partialUpdate(OwnerKeyFieldsDto ownerKeyFieldsDto, @MappingTarget Owner owner);

	@Named("encodeId")
	protected String encodeId(Integer id) {
		return encoderApiRepository.findEncoderByName("owner").encode(List.of(id.longValue()));
	}
}
