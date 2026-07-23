import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BorderLineSystem.BorderLine.dto.VisaApplicationDTO;
import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.entity.VisaApplication;
import com.BorderLineSystem.BorderLine.repository.ImmigrantRepository;
import com.BorderLineSystem.BorderLine.repository.VisaApplicationRepository;
import com.BorderLineSystem.BorderLine.service.VisaApplicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisaApplicationServiceImpl implements VisaApplicationService {

    private final VisaApplicationRepository applicationRepository;
    private final ImmigrantRepository immigrantRepository;

    @Override
    public VisaApplicationDTO createApplication(VisaApplicationDTO dto) {

        Immigrant immigrant = immigrantRepository.findById(dto.getImmigrantId())
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        VisaApplication application = VisaApplication.builder()
                .requestedVisaType(dto.getRequestedVisaType())
                .submissionDate(dto.getSubmissionDate())
                .status(dto.getStatus())
                .immigrant(immigrant)
                .build();

        return mapToDTO(applicationRepository.save(application));
    }

    @Override
    public List<VisaApplicationDTO> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VisaApplicationDTO getApplicationById(Long id) {

        VisaApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        return mapToDTO(application);
    }

    @Override
    public VisaApplicationDTO updateApplication(Long id, VisaApplicationDTO dto) {

        VisaApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Immigrant immigrant = immigrantRepository.findById(dto.getImmigrantId())
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        application.setRequestedVisaType(dto.getRequestedVisaType());
        application.setSubmissionDate(dto.getSubmissionDate());
        application.setStatus(dto.getStatus());
        application.setImmigrant(immigrant);

        return mapToDTO(applicationRepository.save(application));
    }

    @Override
    public void deleteApplication(Long id) {

        VisaApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        applicationRepository.delete(application);
    }

    private VisaApplicationDTO mapToDTO(VisaApplication application) {

        return VisaApplicationDTO.builder()
                .id(application.getId())
                .requestedVisaType(application.getRequestedVisaType())
                .submissionDate(application.getSubmissionDate())
                .status(application.getStatus())
                .immigrantId(application.getImmigrant().getId())
                .build();
    }
}
