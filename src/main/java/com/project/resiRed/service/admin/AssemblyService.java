package com.project.resiRed.service.admin;

import com.project.resiRed.dto.AssemblyDto.createAssemblyRequest;
import com.project.resiRed.dto.MessageDto;


public interface AssemblyService {
    MessageDto createAssembly(createAssemblyRequest createAssemblyRequest);
}
