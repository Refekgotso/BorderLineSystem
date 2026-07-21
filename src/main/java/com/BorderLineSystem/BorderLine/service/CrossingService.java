package com.borderlines.service;

import com.borderlines.model.BorderCrossing;
import com.borderlines.model.Immigrant;
import com.borderlines.model.User;
import com.borderlines.model.Visa;
import com.borderlines.repository.CrossingRepository;
import com.borderlines.repository.ImmigrantRepository;
import com.borderlines.repository.VisaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrossingService {

    private final CrossingRepository crossingRepository;
    private final ImmigrantRepository immigrantRepository;
    private final VisaRepository visaRepository;
    private final AlertService alertService;

    @Transactional
    public BorderCrossing recordCrossing(BorderCrossing crossing, Long immigrantId, User recordedBy) {
        Immigrant immigrant = immigrantRepository.findById(immigrantId)
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        crossing.setImmigrant(immigrant);
        crossing.setRecordedBy(recordedBy);
        crossing.setCrossingTime(LocalDateTime.now());

        // If it's an entry, validate visa
        if (crossing.getEntryOrExit()) {
            validateVisaForEntry(immigrant, crossing);
        }

        BorderCrossing saved = crossingRepository.save(crossing);

        // Check for overstay after recording
        if (crossing.getEntryOrExit()) {
            checkForOverstay(immigrant);
        }

        return saved;
    }

    private void validateVisaForEntry(Immigrant immigrant, BorderCrossing crossing) {
        List<Visa> activeVisas = visaRepository.findByImmigrantAndStatus(immigrant, Visa.VisaStatus.ACTIVE);
        if (activeVisas.isEmpty()) {
            throw new RuntimeException("Immigrant has no active visa. Entry not allowed.");
        }

        Visa visa = activeVisas.get(0);
        if (visa.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Visa has expired. Entry not allowed.");
        }

        crossing.setVisa(visa);
    }

    private void checkForOverstay(Immigrant immigrant) {
        List<BorderCrossing> entries = crossingRepository.findByImmigrantAndEntryOrExit(immigrant, true);
        List<BorderCrossing> exits = crossingRepository.findByImmigrantAndEntryOrExit(immigrant, false);

        // If there's an entry without a corresponding exit, check for overstay
        if (entries.size() > exits.size()) {
            BorderCrossing lastEntry = entries.get(entries.size() - 1);
            Visa visa = lastEntry.getVisa();

            if (visa != null) {
                LocalDate expectedExitDate = visa.getExpiryDate();
                if (expectedExitDate.isBefore(LocalDate.now())) {
                    long daysOverstayed = ChronoUnit.DAYS.between(expectedExitDate, LocalDate.now());
                    alertService.createOverstayAlert(immigrant, daysOverstayed);
                }
            }
        }
    }

    public Page<BorderCrossing> getAllCrossings(Pageable pageable) {
        return crossingRepository.findAll(pageable);
    }

    public List<BorderCrossing> getCrossingsForImmigrant(Long immigrantId) {
        Immigrant immigrant = immigrantRepository.findById(immigrantId)
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));
        return crossingRepository.findByImmigrant(immigrant);
    }

    public BorderCrossing getCrossingById(Long id) {
        return crossingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crossing not found"));
    }
}