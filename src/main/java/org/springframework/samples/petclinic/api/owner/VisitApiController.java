package org.springframework.samples.petclinic.api.owner;

import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.api.owner.dto.VisitInfoDto;
import org.springframework.samples.petclinic.api.owner.mapper.VisitApiMapper;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.samples.petclinic.owner.VisitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/visits")
public class VisitApiController {

	private final VisitRepository visitRepository;

	private final VisitApiMapper visitApiMapper;

	public VisitApiController(VisitRepository visitRepository,
							  VisitApiMapper visitApiMapper) {
		this.visitRepository = visitRepository;
		this.visitApiMapper = visitApiMapper;
	}

	@GetMapping("/{id}")
	public VisitInfoDto getOne(@PathVariable Integer id) {
		Optional<Visit> visitOptional = visitRepository.findById(id);
		return visitApiMapper.toDto(visitOptional.orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
	}
}

