package org.springframework.samples.petclinic.adminui.pet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.samples.petclinic.adminui.pet.dto.PetAdminDto;
import org.springframework.samples.petclinic.adminui.pet.dto.PetFilter;
import org.springframework.samples.petclinic.adminui.pet.mapper.PeAdmintMapper;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adminui/pets")
public class PetAdminResource {

	private final PetRepository petRepository;

	private final PeAdmintMapper peAdmintMapper;

	private final ObjectMapper objectMapper;

	public PetAdminResource(PetRepository petRepository,
							PeAdmintMapper peAdmintMapper,
							ObjectMapper objectMapper) {
		this.petRepository = petRepository;
		this.peAdmintMapper = peAdmintMapper;
		this.objectMapper = objectMapper;
	}

	@GetMapping
	public Page<PetAdminDto> getList(@Nullable @ModelAttribute PetFilter filter, Pageable pageable) {
		Specification<Pet> spec = filter.toSpecification();
		Page<Pet> pets = petRepository.findAll(spec, pageable);
		return pets.map(peAdmintMapper::toDto);
	}

	@GetMapping("/{id}")
	public PetAdminDto getOne(@PathVariable Integer id) {
		Optional<Pet> petOptional = petRepository.findById(id);
		return peAdmintMapper.toDto(petOptional.orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
	}

	@GetMapping("/by-ids")
	public List<PetAdminDto> getMany(@RequestParam List<Integer> ids) {
		List<Pet> pets = petRepository.findAllById(ids);
		return pets.stream()
			.map(peAdmintMapper::toDto)
			.toList();
	}

	@PostMapping
	public PetAdminDto create(@RequestBody PetAdminDto dto) {
		Pet pet = peAdmintMapper.toEntity(dto);
		Pet resultPet = petRepository.save(pet);
		return peAdmintMapper.toDto(resultPet);
	}

	@PatchMapping("/{id}")
	public PetAdminDto patch(@PathVariable Integer id, @RequestBody JsonNode patchNode) throws IOException {
		Pet pet = petRepository.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

		PetAdminDto petAdminDto = peAdmintMapper.toDto(pet);
		objectMapper.readerForUpdating(petAdminDto).readValue(patchNode);
		peAdmintMapper.updateWithNull(petAdminDto, pet);

		Pet resultPet = petRepository.save(pet);
		return peAdmintMapper.toDto(resultPet);
	}

	@PatchMapping
	public List<Integer> patchMany(@RequestParam List<Integer> ids, @RequestBody JsonNode patchNode) throws IOException {
		Collection<Pet> pets = petRepository.findAllById(ids);

		for (Pet pet : pets) {
			PetAdminDto petAdminDto = peAdmintMapper.toDto(pet);
			objectMapper.readerForUpdating(petAdminDto).readValue(patchNode);
			peAdmintMapper.updateWithNull(petAdminDto, pet);
		}

		List<Pet> resultPets = petRepository.saveAll(pets);
		return resultPets.stream()
			.map(Pet::getId)
			.toList();
	}

	@DeleteMapping("/{id}")
	public PetAdminDto delete(@PathVariable Integer id) {
		Pet pet = petRepository.findById(id).orElse(null);
		if (pet != null) {
			petRepository.delete(pet);
		}
		return peAdmintMapper.toDto(pet);
	}

	@DeleteMapping
	public void deleteMany(@RequestParam List<Integer> ids) {
		petRepository.deleteAllById(ids);
	}
}
