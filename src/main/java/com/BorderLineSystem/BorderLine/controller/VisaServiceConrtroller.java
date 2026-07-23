package com.BorderLineSystem.BorderLine.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.BorderLineSystem.BorderLine.entity.Visa;
import com.BorderLineSystem.BorderLine.dto.VisaDTO;
import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.repository.ImmigrantRepository;
import com.BorderLineSystem.BorderLine.repository.VisaRepository;
import com.BorderLineSystem.BorderLine.service.VisaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisaServiceConrtroller implements VisaService {

    private final VisaRepository visaRepository;
    private final ImmigrantRepository immigrantRepository;

    @Override
    public VisaDTO createVisa(VisaDTO dto) {

        Immigrant immigrant = immigrantRepository.findById(dto.getImmigrantId())
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        Visa visa = Visa.builder()
                .type(dto.getType())
                .durationDays(dto.getDurationDays())
                .issueDate(dto.getIssueDate())
                .expiryDate(dto.getExpiryDate())
                .immigrant(immigrant)
                .build();

        return mapToDTO(visaRepository.save(visa));
    }

    @Override
    public List<VisaDTO> getAllVisas() {

        return visaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VisaDTO getVisaById(Long id) {

        Visa visa = visaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visa not found"));

        return mapToDTO(visa);
    }

    @Override
    public VisaDTO updateVisa(Long id, VisaDTO dto) {

        Visa visa = visaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visa not found"));

        Immigrant immigrant = immigrantRepository.findById(dto.getImmigrantId())
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        visa.setType(dto.getType());
        visa.setDurationDays(dto.getDurationDays());
        visa.setIssueDate(dto.getIssueDate());
        visa.setExpiryDate(dto.getExpiryDate());
        visa.setImmigrant(immigrant);

        return mapToDTO(visaRepository.save(visa));
    }

    @Override
    public void deleteVisa(Long id) {

        Visa visa = visaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visa not found"));

        visaRepository.delete(visa);
    }

    private VisaDTO mapToDTO(Visa visa) {

        return VisaDTO.builder()
                .id(visa.getId())
                .type(visa.getType())
                .durationDays(visa.getDurationDays())
                .issueDate(visa.getIssueDate())
                .expiryDate(visa.getExpiryDate())
                .immigrantId(visa.getImmigrant().getId())
                .build();
    }
}