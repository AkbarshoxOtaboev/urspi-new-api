package uz.urspi.newurspi.leader.service;

import uz.urspi.newurspi.leader.dto.LeaderDTO;
import uz.urspi.newurspi.leader.response.LeaderLocalizedResponse;
import uz.urspi.newurspi.leader.response.LeaderResponse;
import uz.urspi.newurspi.utils.Language;

import java.util.List;

public interface LeaderService {
    LeaderResponse create(LeaderDTO dto);
    LeaderResponse findById(Long id);
    List<LeaderResponse> fetchAllLeaders();
    List<LeaderLocalizedResponse> fetchAllLeadersByLang(Language lang);
    LeaderResponse update(Long id, LeaderDTO dto);
    void delete(Long id);
    void activeOrDisabledLeader(Long id);
}
