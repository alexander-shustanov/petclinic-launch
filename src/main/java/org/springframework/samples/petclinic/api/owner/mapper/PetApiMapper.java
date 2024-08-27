package org.springframework.samples.petclinic.api.owner.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.samples.petclinic.api.owner.dto.PetInfoDto;
import org.springframework.samples.petclinic.owner.Pet;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PetApiMapper {

	@Autowired
	private IdEncoderApiRepository idEncoderApiRepository;

	@Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
	public abstract PetInfoDto toPetInfoDto(Pet pet);

	@Named("encodeId")
	protected String encodeId(Integer id) {
		return idEncoderApiRepository.findEncoderByName("pet").encode(List.of(id.longValue()));
	}


}
