package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.wi.mmw.e_sejm.entity.RefreshTokenEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.repository.RefreshTokenRepositroy;
import pb.wi.mmw.e_sejm.repository.UserRepository;
import pb.wi.mmw.e_sejm.service.RefreshTokenService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepositroy refreshTokenRepo;
    private final UserRepository userRepository;

    public RefreshTokenEntity createRefreshToken(String email){
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("User not found"));
        Optional<RefreshTokenEntity> existingToken = refreshTokenRepo.findByUser(userEntity);
        if (existingToken.isPresent()) {
            return existingToken.get();
        }
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .user(userEntity)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(12000000)) // 200 min
                .build();
        return refreshTokenRepo.save(token);
    }

    @Transactional
    public void deleteByUserEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("User not found"));

        refreshTokenRepo.deleteByUser(userEntity);
    }
    public RefreshTokenEntity findByToken(String token){
        return refreshTokenRepo.findByToken(token)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.BAD_JWT_TOKEN));
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new CustomException(BusinessErrorCodes.TOKEN_EXPIRED);
        }
        return token;
    }
}
