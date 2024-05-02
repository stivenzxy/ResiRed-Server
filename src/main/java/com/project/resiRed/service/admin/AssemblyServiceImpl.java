package com.project.resiRed.service.admin;


import com.project.resiRed.dto.AssemblyDto.*;

import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.User;
import com.project.resiRed.enums.AssemblyStatus;
import com.project.resiRed.enums.UserRole;
import com.project.resiRed.repository.AssemblyRepository;
import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;

import javax.swing.*;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssemblyServiceImpl implements AssemblyService{
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

        for(Long surveyId: request.getSurveys()){
            Survey survey =surveyRepository.findById(surveyId).get();
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
            for(Question question : questionRepository.findAllBySurvey(survey)){
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
        List<AssemblyResponse> respondAssemblies = new ArrayList<>();
        for (Assembly assembly : assemblies) {
            respondAssemblies.add(assembly.getDto());
        }
        return respondAssemblies;
    }

    @Override
    public ScheduledAssemblyResponse checkScheduledAssembly() {
        Optional<Assembly> assembly = assemblyRepository.findByStatus(AssemblyStatus.SCHEDULED.name());
        if (assembly.isPresent()){

            LocalDate date = assembly.get().getDate();
            LocalTime time = assembly.get().getStartTime();

            LocalDateTime dateTime = LocalDateTime.of(date.getYear(),
                    date.getMonthValue(), date.getDayOfMonth(),
                    time.getHour(), time.getMinute(), time.getSecond());

            return ScheduledAssemblyResponse.builder()
                    .isPresent(true)
                    .title(assembly.get().getTitle())
                    .date(assembly.get().getDate())
                    .startTime(assembly.get().getStartTime())
                    .isAvailable(LocalDateTime.now().isBefore(dateTime))
                    .id(assembly.get().getAssemblyId())
                    .build();
        } else{
            return ScheduledAssemblyResponse.builder()
                    .isPresent(false)
                    .build();
        }
    }




}
