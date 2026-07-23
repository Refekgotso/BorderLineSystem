package com.BorderLineSystem.BorderLine.service;

import java.util.List;

import com.BorderLineSystem.BorderLine.dto.VisaDTO;

public interface VisaService {

    VisaDTO createVisa(VisaDTO visaDTO);

    List<VisaDTO> getAllVisas();

    VisaDTO getVisaById(Long id);

    VisaDTO updateVisa(Long id, VisaDTO visaDTO);

    void deleteVisa(Long id);
}