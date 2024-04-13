package com.project.resiRed.service.admin;


import com.project.resiRed.dto.AssemblyDto.createAssemblyRequest;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.repository.AssemblyRepository;
import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssemblyServiceImpl implements AssemblyService{
    private final AssemblyRepository assemblyRepository;
    private final UserRepository userRepository;
    private  final SurveyRepository surveyRepository;
    @Override
    public MessageDto createAssembly(createAssemblyRequest createAssemblyRequest) {

        Assembly assembly=new Assembly();

        assembly.setCreatedAt(LocalDateTime.now());
        assembly.setTitle(createAssemblyRequest.getTitle());
        assembly.setDescription(createAssemblyRequest.getDescription());
        assembly.setDate(LocalDate.now());
        assembly.setTime(LocalTime.now());


        List<Survey> surveyList=new ArrayList<>();
        for(Long surveyId: createAssemblyRequest.getSurveys()){
            Survey survey =surveyRepository.findById(surveyId).get();
            survey.setAssembly(assembly);
            surveyList.add(survey);
        }

        assemblyRepository.save(assembly);
        return MessageDto.builder().detail("Assembly Created").build();
    }
}
