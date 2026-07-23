package com.BorderLineSystem.BorderLine.service;

import java.util.List;

import com.BorderLineSystem.BorderLine.dto.VisaApplicationDTO;

public interface VisaApplicationService {

    VisaApplicationDTO createApplication(VisaApplicationDTO applicationDTO);

    List<VisaApplicationDTO> getAllApplications();

    VisaApplicationDTO getApplicationById(Long id);

    VisaApplicationDTO updateApplication(Long id, VisaApplicationDTO applicationDTO);

    void deleteApplication(Long id);
}