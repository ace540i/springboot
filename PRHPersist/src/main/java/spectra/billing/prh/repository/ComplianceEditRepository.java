package spectra.billing.prh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spectra.billing.prh.model.ComplianceEdit;

@Repository
public interface ComplianceEditRepository extends JpaRepository<ComplianceEdit, Long> { 

}
