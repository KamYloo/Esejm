package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.VoteDto;
import pb.wi.mmw.e_sejm.entity.VoteEntity;

@Component
@RequiredArgsConstructor
public class VoteMapper implements Mapper<VoteEntity, VoteDto>{

    private final ModelMapper modelMapper;

    @Value("${application.file.cdn}")
    private String cdnBaseUrl;


    @Override
    public VoteDto mapTo(VoteEntity voteEntity) {
        VoteDto voteDto = modelMapper.map(voteEntity, VoteDto.class);

        if (voteDto.getMp() != null && voteDto.getMp().getPhoto() != null) {
            String photo = voteDto.getMp().getPhoto();
            voteDto.getMp().setPhoto(cdnBaseUrl + photo);
        }

        return voteDto;
    }

    @Override
    public VoteEntity mapFrom(VoteDto voteDto) {
        return modelMapper.map(voteDto, VoteEntity.class);
    }
}
