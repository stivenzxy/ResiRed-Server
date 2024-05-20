package com.project.resiRed.service;


import com.project.resiRed.dto.AssemblyDto.*;

import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.questionOverviewResponse;
import com.project.resiRed.dto.SurveyDto.surveysOverviewRequest;
import com.project.resiRed.dto.SurveyDto.surveysOverviewResponse;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.enums.AssemblyStatus;
import com.project.resiRed.repository.AssemblyRepository;
import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssemblyServiceImpl implements AssemblyService {
    private final AssemblyRepository assemblyRepository;
    private final UserRepository userRepository;
    private  final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    @Override
    public MessageDto createAssembly(createAssemblyRequest request) {

        Assembly assembly=new Assembly();

        assembly.setCreatedAt(LocalDateTime.now());
        assembly.setTitle(request.getTitle());
        assembly.setDescription(request.getDescription());
        assembly.setDate(request.getDate());
        assembly.setStartTime(request.getStartTime());
        assembly.setStatus(AssemblyStatus.SCHEDULED);

        for(Survey survey: surveyRepository.findAllById(request.getSurveys())) {
            survey.setAssembly(assembly);
        }

        assemblyRepository.save(assembly);
        return MessageDto.builder().detail("Assembly Created").build();
    }

    @Override
    public List<surveysOverviewResponse> getSurveysOverview(surveysOverviewRequest request){

        List<surveysOverviewResponse> response = new ArrayList<surveysOverviewResponse>();

        for(Survey survey : surveyRepository.findAllById(request.getSurveys())){
            List<questionOverviewResponse> questions = new ArrayList<questionOverviewResponse>();
            for(Question question : survey.getQuestions()){
                questions.add(questionOverviewResponse.builder()
                        .description(question.getDescription())
                        .build());
            }
            response.add(surveysOverviewResponse.builder()
                    .topic(survey.getTopic())
                    .questions(questions)
                    .build());
        }

        return response;
    }

    @Override
    public MessageDto deleteAssembly(Long assemblyId) {
        Assembly assembly = assemblyRepository.findById(assemblyId).get();
        assemblyRepository.deleteById(assemblyId);
        return MessageDto.builder().detail("Assembly deleted").build();
    }


    @Override
    public List<AssemblyResponse> getAllAssembliesHistory() {
        List<Assembly> assemblies = assemblyRepository.findAllHistory();
        List<AssemblyResponse> responseAssemblies = new ArrayList<>();
        for (Assembly assembly : assemblies) {
            responseAssemblies.add(assembly.getDto());
        }
        return responseAssemblies;
    }

    @Override
    public ScheduledAssemblyResponse checkScheduledAssembly() {
        Optional<Assembly> assembly = assemblyRepository.findByStatus(AssemblyStatus.SCHEDULED);
        if (assembly.isPresent()){

            LocalTime time = assembly.get().getStartTime();
            LocalDate date = assembly.get().getDate();

            LocalDateTime dateTime = LocalDateTime.of(date.getYear(),
                    date.getMonthValue(), date.getDayOfMonth(),
                    time.getHour(), time.getMinute(), time.getSecond());

            return ScheduledAssemblyResponse.builder()
                    .isScheduled(true)
                    .title(assembly.get().getTitle())
                    .date(assembly.get().getDate())
                    .startTime(assembly.get().getStartTime())
                    .isAvailable(LocalDateTime.now().isAfter(dateTime))
                    .build();
        } else{
            return ScheduledAssemblyResponse.builder()
                    .isScheduled(false)
                    .build();
        }
    }

    @Override
    public MessageDto cancelScheduledAssembly(){
        Assembly assembly = assemblyRepository.findByStatus(AssemblyStatus.SCHEDULED).get();
        assembly.setStatus(AssemblyStatus.CANCELED);
        assemblyRepository.save(assembly);

        List<Survey> surveys = surveyRepository.findAllByAssembly(assembly);

        for(Survey survey : surveys){
            survey.setAssembly(null);
            surveyRepository.save(survey);
        }

        return MessageDto.builder()
                .detail("Assembly canceled")
                .build();
    }

    @Override
    public MessageDto finishAssembly(){
        Assembly assembly = assemblyRepository.findByStatus(AssemblyStatus.STARTED).get();
        assembly.setStatus(AssemblyStatus.FINISHED);
        assemblyRepository.save(assembly);

        return MessageDto.builder()
                .detail("Assembly finished")
                .build();
    }


}