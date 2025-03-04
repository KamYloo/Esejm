package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.wi.mmw.e_sejm.dto.NewsDto;
import pb.wi.mmw.e_sejm.dto.request.NewsRequestDto;
import pb.wi.mmw.e_sejm.entity.CategoryEntity;
import pb.wi.mmw.e_sejm.entity.NewsEntity;
import pb.wi.mmw.e_sejm.entity.PartyEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.CategoryRepository;
import pb.wi.mmw.e_sejm.repository.NewsRepository;
import pb.wi.mmw.e_sejm.repository.PartyRepository;
import pb.wi.mmw.e_sejm.repository.UserRepository;
import pb.wi.mmw.e_sejm.service.FileStorageService;
import pb.wi.mmw.e_sejm.service.NewsService;
import pb.wi.mmw.e_sejm.service.PartyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final Mapper<NewsEntity, NewsDto> mapper;
    private final FileStorageService fileStorageService;
    private final PartyRepository partyRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Value("${application.file.cdn}")
    private String cdnBaseUrl;

    @Override
    public NewsDto save(NewsRequestDto newsDto, String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        NewsEntity news = new NewsEntity().builder()
                .author(userEntity)
                .categories(fetchCategories(newsDto.getCategories()))
                .title(newsDto.getTitle())
                .content(newsDto.getContent())
                .imagePath(newsDto.getImagePath())
                .build();
        NewsEntity savedNews = newsRepository.save(news);
        return mapper.mapTo(savedNews);
    }

    @Override
    public List<NewsDto> findAll() {
        return StreamSupport.stream(newsRepository
                                .findAll()
                                .spliterator(),
                        false)
                .map(entity -> {
                    NewsDto dto = mapper.mapTo(entity);
                    dto.setImagePath(cdnBaseUrl + dto.getImagePath());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto findOne(Long id) {
        NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.BAD_NEWS_ID));
        NewsDto dto = mapper.mapTo(newsEntity);
        dto.setImagePath(cdnBaseUrl + dto.getImagePath());
        return dto;
    }

    @Override
    public NewsDto updateNews(Long id, NewsRequestDto newsDto) {
        if (!newsRepository.existsById(id)) {
            throw new CustomException(BusinessErrorCodes.BAD_NEWS);
        }
        NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.BAD_NEWS_ID)
        );
        newsEntity.setTitle(newsDto.getTitle());
        newsEntity.setContent(newsDto.getContent());
        newsEntity.setImagePath(newsDto.getImagePath());
        newsEntity.setCategories(fetchCategories(newsDto.getCategories()));
        NewsEntity savedNewsEntity = newsRepository.save(newsEntity);
        return mapper.mapTo(savedNewsEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.BAD_NEWS_ID));
        newsRepository.deleteById(id);
        fileStorageService.deleteFile(newsEntity.getImagePath());
    }

    @Override
    public List<NewsDto> findLatestByParty(Long id) {
        PartyEntity partyEntity = partyRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NOT_FOUND_PARTY)
        );
        List<NewsEntity> newsList = newsRepository.findLatestByCategoryName(partyEntity.getPartyId(), PageRequest.of(0, 3));
        return newsList.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    private List<CategoryEntity> fetchCategories(List<String> categories){
        return categories.stream()
                .map(c -> categoryRepository.findByName(c).orElseThrow(
                        () -> new CustomException(BusinessErrorCodes.NO_CATEGORY))
                ).collect(Collectors.toList());
    }
}