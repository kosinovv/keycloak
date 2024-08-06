package com.kosinov.keycloak.service;

import com.kosinov.keycloak.exception.UserNotFound;
import com.kosinov.keycloak.dto.UserUpdateDTO;
import com.kosinov.keycloak.model.User;
import com.kosinov.keycloak.dto.UserDTO;
import com.kosinov.keycloak.repository.UserCachedRepository;
import com.kosinov.keycloak.repository.UserRepository;
import com.kosinov.keycloak.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KKService {

    //Раздел сотрудников

    private final UserRepository userRepository;
    private final UserCachedRepository userCachedRepository;
    private final UserMapper userMapper;

    public UserDTO addUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUserName(),
                userDTO.getLongName(),
                userDTO.getCorrectDate());
        userRepository.save(user);
        return userDTO;
    }

    public User getUser(String userName) {
        return findInCacheOrDbByUserName(userName);
    }

    public UserDTO findUser(String userName) {
        User findedUser = findInCacheOrDbByUserName(userName);
        return userMapper.toDto(findedUser);
    }

    public UserDTO deleteUser(String userName) {
        User userForDelete = findInCacheOrDbByUserName(userName);
        userRepository.deleteById(userForDelete.getId());
        return userMapper.toDto(userForDelete);
    }

    public UserDTO updateEmployee(UserUpdateDTO userUpdateDTO) {
        User userForUpdate = findInCacheOrDbByUserName(userUpdateDTO.getUserName());
        userMapper.update(userUpdateDTO, userForUpdate);
        userRepository.save(userForUpdate);
        return userMapper.toDto(userForUpdate);
    }

    private User findInCacheOrDbByUserName(String userName) {
        Optional<User> userFromCache = userCachedRepository.findByUserName(userName);

        if (userFromCache.isPresent()) {
            return userFromCache.get();
        }

        User userFromDb = userRepository.findByUserName(userName);
        if (userFromDb == null) {
            throw new UserNotFound(String.format("Пользователь %s не найден", userName));
        }

        userCachedRepository.save(userFromDb);
        return userFromDb;
    }
}
