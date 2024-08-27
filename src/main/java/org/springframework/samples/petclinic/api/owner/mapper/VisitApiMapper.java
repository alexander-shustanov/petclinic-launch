package org.springframework.samples.petclinic.api.owner.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.samples.petclinic.api.owner.dto.VisitInfoDto;
import org.springframework.samples.petclinic.owner.Visit;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class VisitApiMapper {
	@Autowired
	private IdEncoderApiRepository idEncoderApiRepository;

	@Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
	public abstract VisitInfoDto toDto(Visit visit);

	@Named("encodeId")
	protected String encodeId(Integer id) {
		return idEncoderApiRepository.findEncoderByName("visit").encode(List.of(id.longValue()));
	}
}
