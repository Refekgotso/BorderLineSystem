package com.BorderLineSystem.BorderLine.service;

import java.util.List;

import com.BorderLineSystem.BorderLine.dto.BorderCrossingDTO;

public interface BorderCrossingService {

    BorderCrossingDTO createBorderCrossing(BorderCrossingDTO dto);

    List<BorderCrossingDTO> getAllBorderCrossings();

    BorderCrossingDTO getBorderCrossingById(Long id);

    BorderCrossingDTO updateBorderCrossing(Long id, BorderCrossingDTO dto);

    void deleteBorderCrossing(Long id);
}