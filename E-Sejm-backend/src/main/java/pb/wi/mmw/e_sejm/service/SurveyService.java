package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.SurveyDto;
import pb.wi.mmw.e_sejm.dto.request.SurveyRequest;

import java.util.List;

public interface SurveyService {
    SurveyDto getSurvey(Long id);
    List<SurveyDto> getOpenSurveys();
    SurveyDto createSurvey(SurveyRequest surveyRequest);
    SurveyDto updateSurvey(Long id, SurveyRequest surveyRequest);
    void deleteSurvey(Long id);
    Object surveyStatistics(Long id);
}
