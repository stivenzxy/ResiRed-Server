package com.project.resiRed.dto.AssemblyDto;


import com.project.resiRed.dto.AssemblyDto.questionOverviewResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class surveysOverviewResponse {
    private String topic;
    private List<questionOverviewResponse> questions;

}
