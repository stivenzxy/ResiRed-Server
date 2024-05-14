package com.project.resiRed.service;

import com.project.resiRed.dto.AssemblyDto.*;
import com.project.resiRed.dto.MessageDto;

import java.util.List;

import java.util.List;


public interface AssemblyService {
    MessageDto createAssembly(createAssemblyRequest request);

    List<surveysOverviewResponse> getSurveysOverview(surveysOverviewRequest request);

    MessageDto deleteAssembly(Long assemblyId);

    List<AssemblyResponse> getAllAssembliesHistory();

    ScheduledAssemblyResponse checkScheduledAssembly();

    MessageDto cancelScheduledAssembly();



}
