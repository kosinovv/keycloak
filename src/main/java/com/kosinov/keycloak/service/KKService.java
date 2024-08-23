package com.kosinov.keycloak.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kosinov.keycloak.dto.UserDTO;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KKService {

    private final Keycloak keycloak;

    @Value("${spring.keycloak.realm}")
    private String realm;

    public void addUser(UserDTO userDTO) {
        log.info(String.format("KKService addUser: %s",userDTO.getUserName()));
        CredentialRepresentation credential = createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(user);
        if (response.getStatus() == 201) {
            addRealmRoleToUser(userDTO.getUserName(), userDTO.getRole());
        }
        org.keycloak.admin.client.CreatedResponseUtil.getCreatedId(response);
        log.info(String.format("KKService пользователь %s добавлен",userDTO.getUserName()));
    }

    private void addRealmRoleToUser(String userName, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(userName);
        UserResource userResource = realmResource.users().get(users.get(0).getId());
        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
        RoleMappingResource roleMappingResource = userResource.roles();
        roleMappingResource.realmLevel().add(Collections.singletonList(role));
    }

    private UsersResource getUsersResource() {
        keycloak.tokenManager().getAccessToken();
        return keycloak.realm(realm).users();
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public void changePassword(UserDTO userDTO) {
        log.info(String.format("KKService changePassword для пользователя %s",userDTO.getUserName()));
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(userDTO.getUserName());
        UserResource userResource = realmResource.users().get(users.get(0).getId());
        CredentialRepresentation credential = createPasswordCredentials(userDTO.getPassword());
        userResource.resetPassword(credential);
        log.info(("KKService пароль изменен"));
    }

    public void deleteUser(UserDTO userDTO) {
        log.info(String.format("KKService deleteUser: %s",userDTO.getUserName()));
        if (userDTO.getUserName() != null && !userDTO.getUserName().equals("")) {
            RealmResource realmResource = keycloak.realm(realm);
            List<UserRepresentation> users = realmResource.users().search(userDTO.getUserName());
            UserResource userResource = realmResource.users().get(users.get(0).getId());
            userResource.remove();
            log.info(String.format("KKService пользователь %s удален", userDTO.getUserName()));
        } else {
            log.error("KKService не указано имя пользователя для поиска");
        }
    }

    public List<String> listUser() {
        log.info("KKService listUser");
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().list();
        List<String> usersList = new ArrayList<>();
        for(UserRepresentation user : users){
            usersList.add(user.getUsername());
        }
        return usersList;
    }

    public UserDTO findUser(String userName) {
        UserDTO user = new UserDTO();
        if (userName != null && !userName.equals("")) {
            log.info(String.format("KKService findUser: %s", userName));
            RealmResource realmResource = keycloak.realm(realm);
            List<UserRepresentation> users = realmResource.users().search(userName);
            user.setUserName(users.get(0).getUsername());
            user.setFirstName(users.get(0).getFirstName());
            user.setLastName(users.get(0).getLastName());
            user.setEmail(users.get(0).getEmail());
            if (user.getUserName() != null) {
                log.info(String.format("KKService пользователь %s найден", userName));
            }
        } else {
            log.error("KKService не указано имя пользователя для поиска");
        }
        return user;
    }


    public void saveUser(UserDTO userDTO) {
      try {
        log.info(String.format("KKService saveUser: %s",userDTO.getUserName()));
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(userDTO.getUserName());
        UserResource userResource = realmResource.users().get(users.get(0).getId());
        UserRepresentation user = userResource.toRepresentation();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        userResource.update(user);
        log.info(String.format("KKService данные пользователя %s обновлены",userDTO.getUserName()));
      } catch (Exception exception) {
        log.error(exception.getMessage());
      }
    }

}