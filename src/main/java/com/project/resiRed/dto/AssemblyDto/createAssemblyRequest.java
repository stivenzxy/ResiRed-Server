package com.project.resiRed.dto.AssemblyDto;

import com.project.resiRed.enums.AssemblyStatus;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class createAssemblyRequest {
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private List<Long> surveys;
}
