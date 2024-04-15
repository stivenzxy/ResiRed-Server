package com.project.resiRed.service.admin;

import com.project.resiRed.dto.AssemblyDto.createAssemblyRequest;
import com.project.resiRed.dto.AssemblyDto.surveysOverviewRequest;
import com.project.resiRed.dto.AssemblyDto.surveysOverviewResponse;
import com.project.resiRed.dto.MessageDto;

import java.util.List;

import java.util.List;


public interface AssemblyService {
    MessageDto createAssembly(createAssemblyRequest request);

    List<surveysOverviewResponse> getSurveysOverview(surveysOverviewRequest request);

    MessageDto deleteAssembly(Long assemblyId);

   // List<AssemblyDto> getAllAssemblies();

}
