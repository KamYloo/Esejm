package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.entity.PasswordResetToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.repository.PasswordResetTokenRepository;
import pb.wi.mmw.e_sejm.service.PasswordResetTokenService;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken createToken(UserEntity user) {

        PasswordResetToken foundedToken = passwordResetTokenRepository.findByUser(user);
        if (foundedToken != null) {
            passwordResetTokenRepository.delete(foundedToken);
        }

        PasswordResetToken token = PasswordResetToken.builder()
                .token(generateToken(32))
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();
        return passwordResetTokenRepository.save(token);
    }

    @Override
    public PasswordResetToken getTokenByUser(UserEntity user) {
        return passwordResetTokenRepository.findByUser(user);
    }

    @Override
    public boolean tokenExpired(PasswordResetToken token) {
        return LocalDateTime.now().isAfter(token.getExpiryDate());
    }

    @Override
    public void deleteToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }

    private String generateToken(int lenght) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < lenght; i++) {
            codeBuilder.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }
}
