package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.NewsDto;
import pb.wi.mmw.e_sejm.dto.request.NewsRequestDto;

import java.util.List;

public interface NewsService {
    NewsDto save(NewsRequestDto newsEntity, String email);
    List<NewsDto> findAll();
    NewsDto findOne(Long id);
    NewsDto updateNews(Long id, NewsRequestDto newsDto);
    void delete(Long id);
    List<NewsDto> findLatestByParty(Long id);
}
