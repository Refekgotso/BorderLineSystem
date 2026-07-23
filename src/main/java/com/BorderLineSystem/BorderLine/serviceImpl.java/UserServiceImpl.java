import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.BorderLineSystem.BorderLine.dto.UserDTO;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.repository.UserRepository;
import com.BorderLineSystem.BorderLine.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO dto) {

        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(dto.getRole())
                .enabled(dto.isEnabled())
                .build();

        return mapToDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setEnabled(dto.isEnabled());

        return mapToDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    private UserDTO mapToDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }
    

}
