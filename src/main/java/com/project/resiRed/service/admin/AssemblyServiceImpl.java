package com.project.resiRed.service.admin;

import com.project.resiRed.dto.AssemblyDto;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.AssemblyRepository;
import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssemblyServiceImpl implements AssemblyService{
    private final AssemblyRepository assemblyRepository;
    private final UserRepository userRepository;
    private  final SurveyRepository surveyRepository;
    @Override
    public AssemblyDto createAssembly(AssemblyDto assemblyDto) {

        Assembly assembly=new Assembly();
        assembly.setTitle(assemblyDto.getTitle());
        assembly.setDescription(assemblyDto.getDescription());
        assembly.setDate(assemblyDto.getDate());
        assembly.setTime(assemblyDto.getTime());


        List<Survey> surveyList=new ArrayList<>();
        for(Long surveyId: assemblyDto.getSurveys()){
            Optional<Survey> survey=surveyRepository.findById(surveyId);
            surveyList.add(survey.get());
        }
        assembly.setSurveys(surveyList);

        Assembly createdAssembly=assemblyRepository.save(assembly);
        return createdAssembly.getDto();
    }
}
