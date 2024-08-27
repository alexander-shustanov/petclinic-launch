package org.springframework.samples.petclinic.adminui.owner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.samples.petclinic.adminui.owner.dto.OwnerAdminDto;
import org.springframework.samples.petclinic.adminui.owner.dto.OwnerAdminFilter;
import org.springframework.samples.petclinic.adminui.owner.mapper.OwnerAdminMapper;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adminui/owners")
public class OwnerAdminResource {

	private final OwnerRepository ownerRepository;

	private final OwnerAdminMapper ownerAdminMapper;

	private final ObjectMapper objectMapper;

	public OwnerAdminResource(OwnerRepository ownerRepository,
							  OwnerAdminMapper ownerAdminMapper,
							  ObjectMapper objectMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerAdminMapper = ownerAdminMapper;
		this.objectMapper = objectMapper;
	}

	@GetMapping
	public Page<OwnerAdminDto> getList(@Nullable @ModelAttribute OwnerAdminFilter filter, Pageable pageable) {
		Specification<Owner> spec = filter.toSpecification();
		Page<Owner> owners = ownerRepository.findAll(spec, pageable);
		return owners.map(ownerAdminMapper::toDto);
	}

	@GetMapping("/{id}")
	public OwnerAdminDto getOne(@PathVariable Integer id) {
		Optional<Owner> ownerOptional = ownerRepository.findById(id);
		return ownerAdminMapper.toDto(ownerOptional.orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
	}

	@GetMapping("/by-ids")
	public List<OwnerAdminDto> getMany(@RequestParam List<Integer> ids) {
		List<Owner> owners = ownerRepository.findAllById(ids);
		return owners.stream()
			.map(ownerAdminMapper::toDto)
			.toList();
	}

	@PostMapping
	public OwnerAdminDto create(@RequestBody @Valid OwnerAdminDto dto) {
		Owner owner = ownerAdminMapper.toEntity(dto);
		Owner resultOwner = ownerRepository.save(owner);
		return ownerAdminMapper.toDto(resultOwner);
	}

	@PatchMapping("/{id}")
	public OwnerAdminDto patch(@PathVariable Integer id, @RequestBody JsonNode patchNode) throws IOException {
		Owner owner = ownerRepository.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

		OwnerAdminDto ownerAdminDto = ownerAdminMapper.toDto(owner);
		objectMapper.readerForUpdating(ownerAdminDto).readValue(patchNode);
		ownerAdminMapper.updateWithNull(ownerAdminDto, owner);

		Owner resultOwner = ownerRepository.save(owner);
		return ownerAdminMapper.toDto(resultOwner);
	}

	@PatchMapping
	public List<Integer> patchMany(@RequestParam @Valid List<Integer> ids, @RequestBody JsonNode patchNode) throws IOException {
		Collection<Owner> owners = ownerRepository.findAllById(ids);

		for (Owner owner : owners) {
			OwnerAdminDto ownerAdminDto = ownerAdminMapper.toDto(owner);
			objectMapper.readerForUpdating(ownerAdminDto).readValue(patchNode);
			ownerAdminMapper.updateWithNull(ownerAdminDto, owner);
		}

		List<Owner> resultOwners = ownerRepository.saveAll(owners);
		return resultOwners.stream()
			.map(Owner::getId)
			.toList();
	}

	@DeleteMapping("/{id}")
	public OwnerAdminDto delete(@PathVariable Integer id) {
		Owner owner = ownerRepository.findById(id).orElse(null);
		if (owner != null) {
			ownerRepository.delete(owner);
		}
		return ownerAdminMapper.toDto(owner);
	}

	@DeleteMapping
	public void deleteMany(@RequestParam List<Integer> ids) {
		ownerRepository.deleteAllById(ids);
	}
}
