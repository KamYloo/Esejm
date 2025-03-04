package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.entity.RoleEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.repository.RolesRepository;
import pb.wi.mmw.e_sejm.repository.UserRepository;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String nick = email.contains("@") ? email.substring(0, email.indexOf('@')) : email;

        userRepository.findByEmail(email)
                .ifPresent(emaill -> {
                    throw new CustomException(BusinessErrorCodes.EMAIL_IS_USED);
                });

        userRepository.findByNickName(nick)
                .ifPresent(nickk -> {
                    throw new CustomException(BusinessErrorCodes.NICKNAME_IS_USED);
                });

        RoleEntity role = rolesRepository.findByName("USER").orElseThrow(
                () -> new RuntimeException("Role not found"));



        UserEntity user = UserEntity.builder()
                .firstName((String) attributes.getOrDefault("given_name", ""))
                .lastName((String) attributes.getOrDefault("family_name", ""))
                .nickName(nick)
                .email(email)
                .roles(Set.of(role))
                .accountLocked(false)
                .enable(true)
                .build();
        UserEntity savedUser = userRepository.save(user);


        return new DefaultOAuth2User(
                savedUser.getRoles().stream()
                        .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                        .collect(Collectors.toSet()),
                attributes,
                "email"
        );
    }
}
