package com.project.resiRed.controller.user;

import com.project.resiRed.dto.AttendanceDto;
import com.project.resiRed.entity.Attendance;
import com.project.resiRed.service.user.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/attendance")
public class AttendanceController {
    AttendanceService attendanceService;
    @RequestMapping("register")
    public ResponseEntity<AttendanceDto>  createAttendance(@RequestBody AttendanceDto attendanceDto){
        AttendanceDto newAttendance=attendanceService.createAttendance(attendanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAttendance);
    }
}
