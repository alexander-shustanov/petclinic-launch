package org.springframework.samples.petclinic.api.owner.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.samples.petclinic.api.owner.dto.VisitCreateDto;
import org.springframework.samples.petclinic.api.owner.dto.VisitInfoDto;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.Visit;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = PetApiMapper.class)
public abstract class VisitApiMapper {

	@Autowired
	private IdEncoderApiRepository idEncoderApiRepository;

	@Autowired
	private PetRepository petRepository;

	@Mapping(source = "id", target = "id", qualifiedByName = "encodeVisitId")
	public abstract VisitInfoDto toVisitInfoDto(Visit visit);

	@Named("encodeVisitId")
	protected String encodeVisitId(Integer id) {
		return idEncoderApiRepository.findEncoderByName("visit").encode(List.of(id.longValue()));
	}

	@Mapping(source = "petId", target = "pet", qualifiedByName = "decodePetId")
	public abstract Visit toEntity(VisitCreateDto visitCreateDto);
}
