package uz.urspi.newurspi.hemis.publicstat.service;

import uz.urspi.newurspi.hemis.publicstat.response.StudentStatResponse;

public interface HemisStudentStatService {
    StudentStatResponse fetchStudentStats(String lang);
}
