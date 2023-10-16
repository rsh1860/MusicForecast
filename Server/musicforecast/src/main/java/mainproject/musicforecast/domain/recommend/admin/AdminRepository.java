package mainproject.musicforecast.domain.recommend.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminSuggest, Long> {

}
