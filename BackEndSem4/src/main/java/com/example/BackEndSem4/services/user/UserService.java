package com.example.BackEndSem4.services.user;

import com.example.BackEndSem4.components.JwtTokenUtils;
import com.example.BackEndSem4.dtos.UserDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Role;
import com.example.BackEndSem4.models.User;
import com.example.BackEndSem4.repositories.RoleRepository;
import com.example.BackEndSem4.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new DataNotFoundException("Phone number already exists.");
        }
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new DataNotFoundException("Email already exists.");
        }
        Role role = roleRepository.findRoleByName(Role.USER);
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .birthday(userDTO.getBirthday())
                .address(userDTO.getAddress())
                .gender(userDTO.getGender())
                .active(true)
                .build();
        newUser.setRole(role);
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }

    @Override
    public User createUserByAdmin(UserDTO userDTO) throws Exception {
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new DataNotFoundException("Phone number already exists.");
        }
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new DataNotFoundException("Email already exists.");
        }

        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .birthday(userDTO.getBirthday())
                .address(userDTO.getAddress())
                .gender(userDTO.getGender())
                .active(true)
                .build();
        if (userDTO.getRoleId() != null) {
            Optional<Role> optionalRole = roleRepository.findById(userDTO.getRoleId());
            optionalRole.ifPresent(newUser::setRole);
        }else {
            Role role = roleRepository.findRoleByName(Role.USER);
            newUser.setRole(role);
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }


    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Wrong phone number or password.");
        }
        User existingUser = optionalUser.get();
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Wrong phone number or password.");
        }

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException("Role does not exist.");
        }
        if (!optionalUser.get().isActive()) {
            throw new DataNotFoundException("User is locked.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber,password,
                existingUser.getAuthorities()
        );
        //authenticate with Java spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(existingUser);
    }

    @Override
    public User getUserDetailFromToken(String token) throws Exception {
        if(jwtTokenUtils.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String phoneNumber = jwtTokenUtils.extractPhoneNumber(token);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        if(user.isPresent()) {
            return user.get();
        }else {
            throw new Exception("User not found.");
        }
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserDTO userUpdateDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("User not found."));

        // Check if the phoneNumber is being  change and  if  it already exists for  another user
        String newPhoneNumber = userUpdateDTO.getPhoneNumber();
        if(!existingUser.getPhoneNumber().equals(newPhoneNumber) && userRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new DataNotFoundException("Phone number already exists.");
        }

        // Update user information based on the DTO
        if (userUpdateDTO.getFullName() != null) {
            existingUser.setFullName(userUpdateDTO.getFullName());
        }
        if (userUpdateDTO.getEmail() != null) {
            existingUser.setEmail(userUpdateDTO.getEmail());
        }
        if (newPhoneNumber != null) {
            existingUser.setPhoneNumber(newPhoneNumber);
        }
        if (userUpdateDTO.getAddress() != null) {
            existingUser.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getBirthday() != null) {
            existingUser.setBirthday(userUpdateDTO.getBirthday());
        }
        if (userUpdateDTO.getGender() != null) {
            existingUser.setGender(userUpdateDTO.getGender());
        }


        // Update the password if it is provider in the DTO
        if (userUpdateDTO.getPassword() != null
                && !userUpdateDTO.getPassword().isEmpty()) {
            String newPassword = userUpdateDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }

        return userRepository.save(existingUser);
    }


    @Override
    @Transactional
    public User updateUserAdmin(Long userId, UserDTO userUpdateDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("User not found."));

        // Check if the phoneNumber is being  change and  if  it already exists for  another user
        String newPhoneNumber = userUpdateDTO.getPhoneNumber();
        if(!existingUser.getPhoneNumber().equals(newPhoneNumber) && userRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new DataNotFoundException("Phone number already exists.");
        }

        // Update user information based on the DTO
        if (userUpdateDTO.getFullName() != null) {
            existingUser.setFullName(userUpdateDTO.getFullName());
        }
        if (userUpdateDTO.getEmail() != null) {
            existingUser.setEmail(userUpdateDTO.getEmail());
        }
        if (newPhoneNumber != null) {
            existingUser.setPhoneNumber(newPhoneNumber);
        }
        if (userUpdateDTO.getAddress() != null) {
            existingUser.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getBirthday() != null) {
            existingUser.setBirthday(userUpdateDTO.getBirthday());
        }
        if (userUpdateDTO.getGender() != null) {
            existingUser.setGender(userUpdateDTO.getGender());
        }

        if (userUpdateDTO.getRoleId() != null) {
            Optional<Role> optionalRole = roleRepository.findById(userUpdateDTO.getRoleId());
            optionalRole.ifPresent(existingUser::setRole);
        }

        existingUser.setActive(userUpdateDTO.isActive());

        // Update the password if it is provider in the DTO
        if (userUpdateDTO.getPassword() != null
                && !userUpdateDTO.getPassword().isEmpty()) {
            String newPassword = userUpdateDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("User not found."));

        existingUser.setActive(false);
        userRepository.save(existingUser);
//        userRepository.delete(existingUser);
    }


    @Override
    public Page<User> getUsersAll(String keyword, Pageable pageable) {
        return userRepository.getUserAll(keyword, pageable);
    }

    @Override
    public User getUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }else {
            throw new Exception("User not found");
        }
    }
}
