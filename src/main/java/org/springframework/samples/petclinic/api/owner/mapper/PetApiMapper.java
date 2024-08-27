package org.springframework.samples.petclinic.api.owner.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.samples.petclinic.api.owner.dto.PetInfoDto;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PetApiMapper {


	@Autowired
	private PetRepository petRepository;

	@Autowired
	private IdEncoderApiRepository idEncoderApiRepository;

	@Mapping(source = "id", target = "id", qualifiedByName = "encodePetId")
	public abstract PetInfoDto toPetInfoDto(Pet pet);

	@Named("encodePetId")
	protected String encodePetId(Integer id) {
		return idEncoderApiRepository.findEncoderByName("pet").encode(List.of(id.longValue()));
	}

	@Named("decodePetId")
	protected Pet decodePetId(String id) {
		int petIdDecoded = idEncoderApiRepository.findEncoderByName("pet")
			.decode(id).getFirst().intValue();

		return petRepository.getReferenceById(petIdDecoded);
	}
}
