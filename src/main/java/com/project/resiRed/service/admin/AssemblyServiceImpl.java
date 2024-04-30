package com.project.resiRed.service.admin;

import com.project.resiRed.dto.AssemblyDto;
import com.project.resiRed.dto.UserDto;
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

    @Override
    public AssemblyDto deleteAssemblies(Long assemblyId) {
        Optional<Assembly> assembly=assemblyRepository.findById(assemblyId);
        assemblyRepository.deleteById(assemblyId);

        return assembly.get().getDto();
    }

    @Override
    public List<AssemblyDto> getAllAssemblies() {
        List<Assembly> assemblies=assemblyRepository.findAll();
        List<AssemblyDto> respondAssemblies=new ArrayList<>();
        for(Assembly assembly:assemblies){
            respondAssemblies.add(assembly.getDto());
        }
        return respondAssemblies;
    }

    @Override
    public AssemblyDto getAssemblyById(Long id) {
        Optional<Assembly> assembly=assemblyRepository.findById(id);
        return assembly.get().getDto();
    }

    @Override
    public AssemblyDto updateAttendies(AssemblyDto assemblyDto) {
        Optional<Assembly> optionalAssembly = assemblyRepository.findById(assemblyDto.getAssemblyId());
        if (optionalAssembly.isPresent()) {
            Assembly assembly = optionalAssembly.get();

            List<User> attendees = new ArrayList<>();
            for (User users : assemblyDto.getAttendees()) {
                User user = new User();
                user.setUserId((long) users.getUserId());
                user.setName(users.getName());
                user.setLastname(users.getLastname());
                user.setEmail(users.getEmail());
                user.setAddress(users.getAddress());
                attendees.add(user);
            }
            assembly.setAttendees(attendees);

            Assembly updatedAssembly = assemblyRepository.save(assembly);

            return updatedAssembly.getDto();
        }
        return null;
    }



}
