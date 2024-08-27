package org.springframework.samples.petclinic.api.owner;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.samples.petclinic.api.idencoder.EncodedId;
import org.springframework.samples.petclinic.api.idencoder.IdEncoderApiRepository;
import org.springframework.samples.petclinic.api.owner.dto.OwnerInfoDto;
import org.springframework.samples.petclinic.api.owner.dto.OwnerKeyFieldDto;
import org.springframework.samples.petclinic.api.owner.dto.PetInfoDto;
import org.springframework.samples.petclinic.api.owner.dto.VisitInfoDto;
import org.springframework.samples.petclinic.api.owner.mapper.OwnerApiMapper;
import org.springframework.samples.petclinic.api.owner.mapper.PetApiMapper;
import org.springframework.samples.petclinic.api.owner.mapper.VisitApiMapper;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerApiController {

	private final OwnerRepository ownerRepository;

	private final OwnerApiMapper ownerApiMapper;

	private final PetRepository petRepository;

	private final PetApiMapper petApiMapper;

	private final IdEncoderApiRepository idEncoderApiRepository;

	private final VisitRepository visitRepository;

	private final VisitApiMapper visitApiMapper;

	public OwnerApiController(OwnerRepository ownerRepository,
							  OwnerApiMapper ownerApiMapper,
							  PetRepository petRepository,
							  PetApiMapper petApiMapper,
							  IdEncoderApiRepository idEncoderApiRepository,
							  VisitRepository visitRepository,
							  VisitApiMapper visitApiMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerApiMapper = ownerApiMapper;
		this.petRepository = petRepository;
		this.petApiMapper = petApiMapper;
		this.idEncoderApiRepository = idEncoderApiRepository;
		this.visitRepository = visitRepository;
		this.visitApiMapper = visitApiMapper;
	}

	@PostMapping("/by-key-fields")
	public List<OwnerInfoDto> findByTelephoneIn(@RequestBody List<OwnerKeyFieldDto> keyFieldDtos) {

		if (keyFieldDtos.size() > 100) {
			throw new IllegalArgumentException("Too many entities");
		}

		List<String> telephones = keyFieldDtos.stream().map(OwnerKeyFieldDto::telephone).toList();

		HashSet<OwnerKeyFieldDto> ownerKeyFieldDtosSet = new HashSet<>(keyFieldDtos);
		List<Owner> owners = ownerRepository.findByTelephoneIn(telephones).stream()
			.filter(o -> ownerKeyFieldDtosSet.contains(ownerApiMapper.toOwnerKeyFieldDto(o)))
			.toList();

		return owners.stream()
			.map(ownerApiMapper::toOwnerInfoDto)
			.toList();
	}

	@GetMapping("/{ownerId}/pets")
	public Slice<PetInfoDto> findByOwner_Id(@EncodedId(encoder = "owner") Integer ownerId,
											Pageable pageable) {

		Slice<Pet> pets = petRepository.findByOwner_Id(ownerId, pageable);

		return pets.map(petApiMapper::toPetInfoDto);
	}

	@GetMapping("/{ownerId}/pets/{petId}/visits")
	public Slice<VisitInfoDto> findAllByPet_Id(@EncodedId(encoder = "pet") Integer petId,
											   Pageable pageable) {
		Slice<Visit> visits = visitRepository.findAllByPet_Id(petId, pageable);

		return visits.map(visitApiMapper::toDto);
	}
}

