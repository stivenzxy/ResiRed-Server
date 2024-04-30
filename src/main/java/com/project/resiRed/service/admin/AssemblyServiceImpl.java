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
import java.util.ArrayList;

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
    public List<AssemblyResponse> getAllAssemblies() {
        List<Assembly> assemblies = assemblyRepository.findAll();
        List<AssemblyResponse> respondAssemblies = new ArrayList<>();
        for (Assembly assembly : assemblies) {
            respondAssemblies.add(assembly.getDto());
        }
        return respondAssemblies;
    }

    @Override
    public AssemblyAvailabilityResponse checkAvailability(Long assemblyId, Long userId) {
        Assembly assembly = assemblyRepository.findById(assemblyId).get();
        User user = userRepository.findById(userId).get();

        if (user.getRole() == UserRole.ADMIN) {
            LocalDate date = assembly.getDate();
            LocalTime time = assembly.getStartTime();

            LocalDateTime dateTime = LocalDateTime.of(date.getYear(),
                    date.getMonthValue(), date.getDayOfMonth(),
                    time.getHour(), time.getMinute(), time.getSecond());

            System.out.println("ESTA MIERDA " + dateTime + " ESTA OTRA MIERDA " + LocalDateTime.now());

            if (LocalDateTime.now().isBefore(dateTime)) {
                assembly.setStatus(AssemblyStatus.OPENED);
                return AssemblyAvailabilityResponse.builder()
                        .flag(false)
                        .date(assembly.getDate())
                        .startTime(assembly.getStartTime())
                        .build();
            } else {
                return AssemblyAvailabilityResponse.builder()
                        .flag(true)
                        .build();
            }
        } else {
            if (assembly.getStatus() != AssemblyStatus.OPENED) {
                return AssemblyAvailabilityResponse.builder()
                        .flag(false)
                        .date(assembly.getDate())
                        .startTime(assembly.getStartTime())
                        .build();
            } else{
            return AssemblyAvailabilityResponse.builder()
                    .flag(true)
                    .build();
            }
        }

        }

}
