package spectra.billing.prh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spectra.billing.prh.model.ComplianceEditException;

@Repository
public interface ComplianceEditExceptionRepository extends JpaRepository<ComplianceEditException, Long>{

}
