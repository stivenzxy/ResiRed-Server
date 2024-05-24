package com.project.resiRed.service;


import com.project.resiRed.dto.AssemblyDto.*;

import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.questionOverviewResponse;
import com.project.resiRed.dto.SurveyDto.surveysOverviewRequest;
import com.project.resiRed.dto.SurveyDto.surveysOverviewResponse;
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
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public boolean IsAssemblyAvailable(Assembly assembly){
        LocalTime time = assembly.getStartTime();
        LocalDate date = assembly.getDate();

        LocalDateTime dateTime = LocalDateTime.of(date.getYear(),
                date.getMonthValue(), date.getDayOfMonth(),
                time.getHour(), time.getMinute(), time.getSecond());

        return LocalDateTime.now().isAfter(dateTime);
    }

    @Override
    public ScheduledAssemblyResponse checkScheduledAssembly() {
        Optional<Assembly> assembly = assemblyRepository.findByStatus(AssemblyStatus.SCHEDULED);
        if (assembly.isPresent()){
            return ScheduledAssemblyResponse.builder()
                    .isScheduled(true)
                    .assemblyId(assembly.get().getAssemblyId())
                    .title(assembly.get().getTitle())
                    .date(assembly.get().getDate())
                    .startTime(assembly.get().getStartTime())
                    .isAvailable(IsAssemblyAvailable(assembly.get()))
                    .build();
        } else{
            return ScheduledAssemblyResponse.builder()
                    .isScheduled(false)
                    .build();
        }
    }

    @Override
    public MessageDto cancelAssembly(Long assemblyId){
        Assembly assembly = assemblyRepository.findById(assemblyId).get();
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
    public Integer generateCode(Long assemblyId){
        Optional<Assembly> assembly = assemblyRepository.findById(assemblyId);
        if(assembly.isPresent()) {
            if(IsAssemblyAvailable(assembly.get())) {
                Random random = new Random();
                Integer code = 100000 + random.nextInt(900000);
                while(assemblyRepository.findByPasscode(code).isPresent()){
                    code = 100000 + random.nextInt(900000);
                }
                assembly.get().setPasscode(code);
                assemblyRepository.save(assembly.get());
                return code;
            }
        }
        return null;
    }

    @Override
    public String getAssemblyStatus(Long assemblyId){
        Assembly assembly = assemblyRepository.findById(assemblyId).get();
        return assembly.getStatus().name();
    }

    @Override
    public boolean validateCode(Long assemblyId, int passcode){
        Optional<Assembly> assembly = assemblyRepository.findById(assemblyId);
        if(assembly.isPresent()) {
            if (IsAssemblyAvailable(assembly.get())) {
                return assembly.get().getPasscode() == passcode;
            }
        }
        return false;
    }

    @Override
    public void addAttendee(Long assemblyId, User user){
        Optional<Assembly> assembly = assemblyRepository.findById(assemblyId);
        if (assembly.isPresent()) {
            if(IsAssemblyAvailable(assembly.get())){
                assembly.get().getUsers().add(user);
                assemblyRepository.save(assembly.get());
            }
        }
    }

    @Override
    public MessageDto joinAssembly(Long assemblyId, int passcode, String email){
        User user = userRepository.findUserByEmail(email).get();
        if(validateCode(assemblyId, passcode)){
            addAttendee(assemblyId, user);
            return MessageDto.builder()
                    .detail("User joined successfully")
                    .build();
        }
        return MessageDto.builder()
                .detail("Wrong passcode")
                .build();
    }

    @Override
    public boolean validateAttendees(Assembly assembly){
            int totalUsers = userRepository.findByRole(UserRole.OWNER).size();
            int totalAttendees = assembly.getUsers().size();
            return totalAttendees > (totalUsers * 0.5);
    }

    @Override
    public MessageDto startAssembly(Long assemblyId){
        Optional<Assembly> assembly = assemblyRepository.findById(assemblyId);
        if(assembly.isPresent()){
            if(validateAttendees(assembly.get())){
                assembly.get().setStatus(AssemblyStatus.STARTED);
                assemblyRepository.save(assembly.get());
                return MessageDto.builder()
                        .detail("Assembly successfully started")
                        .build();
            }
            return MessageDto.builder().
                    detail("Insufficient number of attendees")
                    .build();
        }
        return null;
    }

    @Override
    public MessageDto finishAssembly(Long assemblyId){

        Optional<Question> lastQuestion = questionRepository.findByCanBeVoted(true);
        lastQuestion.ifPresent(question -> question.setCanBeVoted(false));

        Assembly assembly = assemblyRepository.findById(assemblyId).get();
        assembly.setStatus(AssemblyStatus.FINISHED);
        assembly.setFinishedTime(LocalTime.now());
        assemblyRepository.save(assembly);


        return MessageDto.builder()
                .detail("Assembly finished")
                .build();
    }


}