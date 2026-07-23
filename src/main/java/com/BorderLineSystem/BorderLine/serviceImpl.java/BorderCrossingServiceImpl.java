
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BorderLineSystem.BorderLine.dto.BorderCrossingDTO;
import com.BorderLineSystem.BorderLine.entity.BorderCrossing;
import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.repository.BorderCrossingRepository;
import com.BorderLineSystem.BorderLine.repository.ImmigrantRepository;
import com.BorderLineSystem.BorderLine.service.BorderCrossingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorderCrossingServiceImpl implements BorderCrossingService {

    private final BorderCrossingRepository borderCrossingRepository;
    private final ImmigrantRepository immigrantRepository;

    @Override
    public BorderCrossingDTO createBorderCrossing(BorderCrossingDTO dto) {

        Immigrant immigrant = immigrantRepository.findById(dto.getImmigrantId())
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        BorderCrossing crossing = BorderCrossing.builder()
                .crossingTime(dto.getCrossingTime())
                .entryOrExit(dto.isEntryOrExit())
                .borderPost(dto.getBorderPost())
                .immigrant(immigrant)
                .build();

        return mapToDTO(borderCrossingRepository.save(crossing));
    }

    @Override
    public List<BorderCrossingDTO> getAllBorderCrossings() {

        return borderCrossingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BorderCrossingDTO getBorderCrossingById(Long id) {

        BorderCrossing crossing = borderCrossingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Border crossing not found"));

        return mapToDTO(crossing);
    }

    @Override
    public BorderCrossingDTO updateBorderCrossing(Long id, BorderCrossingDTO dto) {

        BorderCrossing crossing = borderCrossingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Border crossing not found"));

        Immigrant immigrant = immigrantRepository.findById(dto.getImmigrantId())
                .orElseThrow(() -> new RuntimeException("Immigrant not found"));

        crossing.setCrossingTime(dto.getCrossingTime());
        crossing.setEntryOrExit(dto.isEntryOrExit());
        crossing.setBorderPost(dto.getBorderPost());
        crossing.setImmigrant(immigrant);

        return mapToDTO(borderCrossingRepository.save(crossing));
    }

    @Override
    public void deleteBorderCrossing(Long id) {

        BorderCrossing crossing = borderCrossingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Border crossing not found"));

        borderCrossingRepository.delete(crossing);
    }

    private BorderCrossingDTO mapToDTO(BorderCrossing crossing) {

        return BorderCrossingDTO.builder()
                .id(crossing.getId())
                .crossingTime(crossing.getCrossingTime())
                .entryOrExit(crossing.isEntryOrExit())
                .borderPost(crossing.getBorderPost())
                .immigrantId(crossing.getImmigrant().getId())
                .build();
    }
}
