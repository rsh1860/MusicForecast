package mainproject.musicforecast.domain.provider;

import mainproject.musicforecast.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Provider findByProviderName(String providerName);
}
