package com.project.resiRed.service;

import com.project.resiRed.dto.AssemblyDto.*;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.surveysOverviewRequest;
import com.project.resiRed.dto.SurveyDto.surveysOverviewResponse;
import com.project.resiRed.entity.Assembly;

import java.util.List;


public interface AssemblyService {
    MessageDto createAssembly(createAssemblyRequest request);

    List<surveysOverviewResponse> getSurveysOverview(surveysOverviewRequest request);

    MessageDto deleteAssembly(Long assemblyId);

    List<AssemblyResponse> getAllAssembliesHistory();

    boolean IsAssemblyAvailable(Assembly assembly);

    ScheduledAssemblyResponse checkScheduledAssembly();

    MessageDto cancelScheduledAssembly();

    MessageDto finishAssembly();

    void addAttendee(String email);


}
