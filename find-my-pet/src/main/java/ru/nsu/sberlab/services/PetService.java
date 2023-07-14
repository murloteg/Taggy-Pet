package ru.nsu.sberlab.services;

import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.sberlab.models.dto.PetInfoDto;
import ru.nsu.sberlab.models.mappers.PetInfoDtoMapper;
import ru.nsu.sberlab.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PetInfoDtoMapper petInfoDtoMapper;
    private final PropertyResolverUtils propertyResolverUtils;

    public List<PetInfoDto> petsList(Pageable pageable) {
        return petRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(petInfoDtoMapper)
                .toList();
    }

    public PetInfoDto getPetByChipId(String chipId) {
        return petRepository.findByChipId(chipId)
                .map(petInfoDtoMapper)
                .orElse(null);
    }

    @Transactional
    public void deletePet(String chipId) { // TODO: add this feature later
        petRepository.deleteByChipId(chipId);
    }

    private String message(String property) {
        return propertyResolverUtils.resolve(property, Locale.getDefault());
    }
}