package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.entity.MpEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.MpRepository;
import pb.wi.mmw.e_sejm.service.MpService;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpServiceImpl implements MpService {


    private final Mapper<MpEntity, MpDto> mpMapper;
    private final MpRepository mpRepository;

    @Value("${application.file.cdn}")
    private String cdnBaseUrl;

    @Override
    public List<MpDto> getAllMps() {
        List<MpEntity> mps = (List<MpEntity>) mpRepository.findAll();
        return mps.stream()
                .map(mpMapper::mapTo)
                .peek(dto -> dto.setPhoto(cdnBaseUrl + dto.getPhoto()))
                .toList();
    }

    @Override
    public MpDto findMpById(Integer id) {
        MpEntity mpEntity = mpRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NOT_FOUND_MP)
        );
        MpDto dto = mpMapper.mapTo(mpEntity);
        dto.setPhoto(cdnBaseUrl + dto.getPhoto());
        return dto;
    }
}
