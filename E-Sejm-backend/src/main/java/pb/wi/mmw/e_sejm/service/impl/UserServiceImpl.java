package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.request.UserEditRequest;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.entity.MpEntity;
import pb.wi.mmw.e_sejm.entity.PartyEntity;
import pb.wi.mmw.e_sejm.entity.RoleEntity;
import pb.wi.mmw.e_sejm.dto.request.EditUserRequest;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.*;
import pb.wi.mmw.e_sejm.service.FileStorageService;
import pb.wi.mmw.e_sejm.service.UserService;

import java.security.Principal;
import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final RefreshTokenRepositroy refreshTokenRepositroy;
    private final Mapper<UserEntity, UserDto> mapper;
    private final FileStorageService fileStorageService;
    private final MpRepository mpRepository;
    private final Mapper<MpEntity, MpDto> mpMapper;
    private final Mapper<PartyEntity, PartyDto> partyMapper;
    private final PartyRepository partyRepository;


    @Value("${application.file.cdn}")
    private String cdnBaseUrl;

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(mapper::mapTo)
                .toList();
    }

    @Override
    public UserDto editUser(Long id, UserEditRequest userDto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.INCORRECT_USER_ID));
        updateUser(user, userDto);
        user = userRepository.save(user);
        return mapper.mapTo(user);
    }

    @Override
    public String updateUserImageProfile(MultipartFile file, Principal principal) {
        //TODO validacja plików tylko png i jpg
            UserEntity user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String imagePath = fileStorageService.updateFile(file, user.getProfileImage(), "userImages/");
            user.setProfileImage(imagePath);
            userRepository.save(user);
            return cdnBaseUrl+imagePath;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.INCORRECT_USER_ID));
        refreshTokenRepositroy.deleteByUser(userEntity);
        userRepository.deleteById(id);
        fileStorageService.deleteFile(userEntity.getProfileImage());
    }

    @Override
    public void addMp(Integer mpID, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(BusinessErrorCodes.INCORRECT_USER));
        Set<MpEntity> mpList = userRepository.findFavoriteMPsByUser(userEntity);
        MpEntity mpEntity = mpRepository.findById(mpID).orElseThrow(() -> new CustomException(BusinessErrorCodes.NOT_FOUND_MP));
        mpList.add(mpEntity);
        userEntity.setFavoriteMPs(mpList);
        userRepository.save(userEntity);
    }

    @Override
    public List<MpDto> getFavoriteMp(String nick) {
        UserEntity userEntity = userRepository.findByNickName(nick).orElseThrow(() -> new CustomException(BusinessErrorCodes.INCORRECT_USER));
        Set<MpEntity> mpEntities = userRepository.findFavoriteMPsByUser(userEntity);
        return mpEntities.stream().map(mpMapper::mapTo).collect(Collectors.toList());
    }

//    @Override
//    public List<MpDto> getFavoriteMpsOfUser(Long id) {
//        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.INCORRECT_USER));
//        Set<MpEntity> mpEntities = userRepository.findFavoriteMPsByUser(userEntity);
//        return mpEntities.stream().map(mpMapper::mapTo).collect(Collectors.toList());
//    }

    @Override
    public void deleteMp(Integer mpID, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        Set<MpEntity> mpList = userRepository.findFavoriteMPsByUser(userEntity);
        MpEntity mpEntity = mpRepository.findById(mpID).orElseThrow(() -> new CustomException(BusinessErrorCodes.NOT_FOUND_MP));
        if(mpList.contains(mpEntity)) {
            mpList.remove(mpEntity);
            userEntity.setFavoriteMPs(mpList);
            userRepository.save(userEntity);
        }else{
            throw new CustomException(BusinessErrorCodes.INCORRECT_MPLIST);
        }
    }

    @Override
    public void addFavParty(Long partyId, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        PartyEntity partyEntity = partyRepository.findById(partyId).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NOT_FOUND_PARTY)
        );
        userEntity.setFavoriteParty(partyEntity);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteFavParty(Long partyId, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        if( userEntity.getFavoriteParty() != null && userEntity.getFavoriteParty().getId().equals(partyId)) {
            userEntity.setFavoriteParty(null);
            userRepository.save(userEntity);
        }else{
         throw new CustomException(BusinessErrorCodes.PARTY_NOT_IN_FAVORITE);
        }
    }

    @Override
    public PartyDto getFavoriteParty(String userNick) {
        UserEntity userEntity = userRepository.findByNickName(userNick).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        PartyEntity partyEntity = userEntity.getFavoriteParty();
        if(partyEntity == null) {
            return null;
        }
//        PartyEntity partyEntity = partyRepository.findById(userEntity.getFavoriteParty().getId()).orElseThrow(
//                () -> new CustomException(BusinessErrorCodes.NOT_FOUND_PARTY)
//        );
        return partyMapper.mapTo(partyEntity);
    }

    @Override
    public Boolean isMpFavorite(Integer MpId, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        Set<MpEntity> mpEntities = userRepository.findFavoriteMPsByUser(userEntity);
        MpEntity mpEntity = mpRepository.findById(MpId).orElseThrow(() -> new CustomException(BusinessErrorCodes.NOT_FOUND_MP));
        return mpEntities.contains(mpEntity);
    }

    @Override
    public Boolean isPartyFavorite(Long PartyID, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        if( userEntity.getFavoriteParty() != null) {
            return userEntity.getFavoriteParty().getId().equals(PartyID);
        }
        return false;
    }


    @Override
    public UserDto editUser(EditUserRequest request, Principal principal) {
//        if(request.getEmail() != null){
//            userRepository.findByEmail(request.getEmail())
//                    .ifPresent(email -> {
//                        throw new CustomException(BusinessErrorCodes.EMAIL_IS_USED);
//                    });
//        }

        if(request.getNickName() != null){
            userRepository.findByNickName(request.getNickName())
                    .ifPresent(nickname -> {
                        throw new CustomException(BusinessErrorCodes.NICKNAME_IS_USED);
                    });
        }

        return mapper.mapTo(userRepository.findByEmail(principal.getName()).map(existingUser ->{
            Optional.ofNullable(request.getNickName()).ifPresent(existingUser::setNickName);
//            Optional.ofNullable(request.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(request.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(request.getLastName()).ifPresent(existingUser::setLastName);
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new UsernameNotFoundException("User not found")));
    }

    private void updateUser(UserEntity user, UserEditRequest userDto) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setNickName(userDto.getNickName());
        user.setRoles(fetchRoles(userDto.getRoles()));
    }

    private Set<RoleEntity> fetchRoles(List<String> roles) {
        return roles.stream()
                .map(roleName -> rolesRepository.findByName(roleName).orElseThrow(
                        () -> new CustomException(BusinessErrorCodes.NO_ROLE))
                ).collect(Collectors.toSet());
    }
}
