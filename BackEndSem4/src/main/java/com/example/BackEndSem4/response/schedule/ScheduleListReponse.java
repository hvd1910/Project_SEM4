package com.example.BackEndSem4.response.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ScheduleListReponse {
    private List<ScheduleResponse> scheduleResponseList;
    private int totalPages;
}
