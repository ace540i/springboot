package spectra.billing.prh.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name ="DBTCMPEDIT", schema = "MDPTRAC")
public class ComplianceEdit {

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)  
	@Column(name = "cmpedtsk")
	private Long id;
	
	@NonNull
	@Column(name="cmpcode")
	private String cmpCode;	
	
	@Column(name="userid")
	private String userId;	
	
	@Column(name="chngdt")
	private Timestamp changeDate;
		
	@OneToMany(mappedBy = "complianceEdit", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<ComplianceEditCode> codes = new ArrayList<>();
	
	 
	
	
}
