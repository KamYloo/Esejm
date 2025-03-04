package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.entity.DeletionToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.repository.DeletionTokenRepository;
import pb.wi.mmw.e_sejm.service.DeleteTokenService;

import java.security.SecureRandom;
import java.time.Instant;

@Service
@AllArgsConstructor
public class DeleteTokenServiceImpl implements DeleteTokenService {

    private final DeletionTokenRepository deletionTokenRepository;

    @Override
    public DeletionToken createDeletionToken(UserEntity user) {
        DeletionToken deletionToken = DeletionToken.builder()
                .token(generateDeletionToken(20))
                .expiryDate(Instant.now().plusSeconds(60 * 5))
                .user(user)
                .build();
        return deletionTokenRepository.save(deletionToken);
    }

    @Override
    public DeletionToken getDeletionTokenByToken(String token) {
        return deletionTokenRepository.findByToken(token).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_TOKEN));
    }

    @Override
    public void deleteDeletionToken(DeletionToken token) {
        deletionTokenRepository.delete(token);
    }

    @Override
    public boolean verifyExpiration(DeletionToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            deletionTokenRepository.delete(token);
            throw new CustomException(BusinessErrorCodes.TOKEN_EXPIRED);
        }
        return true;
    }

    private String generateDeletionToken(int lenght) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < lenght; i++) {
            codeBuilder.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }
}
