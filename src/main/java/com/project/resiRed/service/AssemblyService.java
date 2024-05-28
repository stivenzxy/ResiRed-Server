package com.project.resiRed.service;

import com.project.resiRed.dto.AssemblyDto.*;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.surveyResult;
import com.project.resiRed.dto.SurveyDto.surveysOverviewRequest;
import com.project.resiRed.dto.SurveyDto.surveysOverviewResponse;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.User;

import java.util.List;


public interface AssemblyService {
    MessageDto createAssembly(createAssemblyRequest request);

    MessageDto deleteAssembly(Long assemblyId);

    List<AssemblyResponse> getAllAssembliesHistory();

    boolean IsAssemblyAvailable(Assembly assembly);

    ScheduledAssemblyResponse checkScheduledAssembly();
    ScheduledAssemblyResponse checkStartedAssembly();
    ScheduledAssemblyResponse checkFinishedAssembly();
    MessageDto cancelAssembly(Long assemblyId);

    Integer generateCode(Long assemblyId);

    String getAssemblyStatus(Long assemblyId);

    boolean validateCode(Long assemblyId, int passcode);

    void addAttendee(Long assemblyId, User user);

    MessageDto joinAssembly(Long assemblyId, int passcode, String email);

    boolean validateAttendees(Assembly assembly);

    MessageDto startAssembly(Long assemblyId);

    MessageDto finishAssembly(Long assemblyId);
    List<surveyResult> getResults(Long assemblyId);

}
