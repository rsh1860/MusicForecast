package mainproject.musicforecast.domain.recommend.admin;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminSuggest> getAllSuggestion() {
        return adminRepository.findAll();
    }
}
