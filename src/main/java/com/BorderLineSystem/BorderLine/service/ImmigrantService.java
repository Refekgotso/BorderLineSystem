package com.BorderLineSystem.BorderLine.service;

import java.util.List;

import com.BorderLineSystem.BorderLine.dto.ImmigrantDTO;

public interface ImmigrantService {

    ImmigrantDTO createImmigrant(ImmigrantDTO immigrantDTO);

    List<ImmigrantDTO> getAllImmigrants();

    ImmigrantDTO getImmigrantById(Long id);

    ImmigrantDTO updateImmigrant(Long id, ImmigrantDTO immigrantDTO);

    void deleteImmigrant(Long id);
}