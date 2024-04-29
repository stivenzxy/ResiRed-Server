package com.project.resiRed.service.admin;

import com.project.resiRed.dto.AssemblyDto;

import java.util.List;


public interface AssemblyService {
    AssemblyDto createAssembly(AssemblyDto assemblyDto);
    AssemblyDto deleteAssemblies(Long assemblyId);
    List<AssemblyDto> getAllAssemblies();
    AssemblyDto getAssemblyById(Long id);
    AssemblyDto updateAttendies(AssemblyDto assemblyDto);
}
