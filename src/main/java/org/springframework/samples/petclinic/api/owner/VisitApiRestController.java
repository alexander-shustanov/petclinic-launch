package org.springframework.samples.petclinic.api.owner;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.api.idencoder.EncodedId;
import org.springframework.samples.petclinic.api.owner.dto.VisitCreateDto;
import org.springframework.samples.petclinic.api.owner.dto.VisitInfoDto;
import org.springframework.samples.petclinic.api.owner.mapper.VisitApiMapper;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.samples.petclinic.owner.VisitRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/visits")
public class VisitApiRestController {

	private final VisitRepository visitRepository;

	private final VisitApiMapper visitApiMapper;

	public VisitApiRestController(VisitRepository visitRepository,
								  VisitApiMapper visitApiMapper) {
		this.visitRepository = visitRepository;
		this.visitApiMapper = visitApiMapper;
	}

	@GetMapping("/{id}")
	public VisitInfoDto getOne(@EncodedId(encoder = "visit") Integer id) {
		Optional<Visit> visitOptional = visitRepository.findById(id);
		return visitApiMapper.toVisitInfoDto(visitOptional.orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
	}

	@PostMapping
	public VisitInfoDto create(@RequestBody @Valid VisitCreateDto dto) {
		Visit visit = visitApiMapper.toEntity(dto);
		Visit resultVisit = visitRepository.save(visit);
		return visitApiMapper.toVisitInfoDto(resultVisit);
	}
}

