package com.kosinov.keycloak.service;

import java.util.Collections;
import java.util.List;

import com.kosinov.keycloak.dto.PasswordDTO;
import com.kosinov.keycloak.dto.UserDTO;
import com.kosinov.keycloak.dto.UserDeleteDTO;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class KKService {

    private final Keycloak keycloak;

    @Value("${spring.keycloak.realm}")
    private String realm;

    public void addUser(UserDTO userDTO) {
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

    public void changePassword(PasswordDTO passwordDTO) {
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(passwordDTO.getUserName());
        UserResource userResource = realmResource.users().get(users.get(0).getId());
        CredentialRepresentation credential = createPasswordCredentials(passwordDTO.getPassword());
        userResource.resetPassword(credential);
    }

    public void deleteUser(UserDeleteDTO userDeleteDTO) {
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(userDeleteDTO.getUserName());
        UserResource userResource = realmResource.users().get(users.get(0).getId());
        userResource.remove();
    }

}