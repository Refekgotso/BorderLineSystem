
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BorderLineSystem.BorderLine.dto.ImmigrantDTO;
import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.repository.ImmigrantRepository;
import com.BorderLineSystem.BorderLine.service.ImmigrantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImmigrantServiceImpl implements ImmigrantService {

    private final ImmigrantRepository immigrantRepository;

    @Override
    public ImmigrantDTO createImmigrant(ImmigrantDTO dto) {

        Immigrant immigrant = mapToEntity(dto);

        return mapToDTO(immigrantRepository.save(immigrant));
    }

    @Override
    public List<ImmigrantDTO> getAllImmigrants() {

        return immigrantRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ImmigrantDTO getImmigrantById(Long id) {

        Immigrant immigrant = immigrantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        return mapToDTO(immigrant);
    }

    @Override
    public ImmigrantDTO updateImmigrant(Long id, ImmigrantDTO dto) {

        Immigrant immigrant = immigrantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        immigrant.setFullName(dto.getFullName());
        immigrant.setPassportNumber(dto.getPassportNumber());
        immigrant.setNationality(dto.getNationality());
        immigrant.setDateOfBirth(dto.getDateOfBirth());
        immigrant.setGender(dto.getGender());
        immigrant.setIdType(dto.getIdType());
        immigrant.setIdNumber(dto.getIdNumber());

        return mapToDTO(immigrantRepository.save(immigrant));
    }

    @Override
    public void deleteImmigrant(Long id) {

        Immigrant immigrant = immigrantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        immigrantRepository.delete(immigrant);
    }

    private ImmigrantDTO mapToDTO(Immigrant immigrant) {

        return ImmigrantDTO.builder()
                .id(immigrant.getId())
                .fullName(immigrant.getFullName())
                .passportNumber(immigrant.getPassportNumber())
                .nationality(immigrant.getNationality())
                .dateOfBirth(immigrant.getDateOfBirth())
                .gender(immigrant.getGender())
                .idType(immigrant.getIdType())
                .idNumber(immigrant.getIdNumber())
                .build();
    }

    private Immigrant mapToEntity(ImmigrantDTO dto) {

        return Immigrant.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .passportNumber(dto.getPassportNumber())
                .nationality(dto.getNationality())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .idType(dto.getIdType())
                .idNumber(dto.getIdNumber())
                .build();
    }
}