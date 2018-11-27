package spectra.billing.prh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spectra.billing.prh.model.ComplianceEditCondSet;

@Repository
public interface ComplianceEditCondSetRepository extends JpaRepository<ComplianceEditCondSet, Long>{
	
}
