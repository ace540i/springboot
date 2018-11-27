package spectra.billing.prh.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name ="DBTCMPEDITCODE", schema = "MDPTRAC")

public class ComplianceEditCode {

	@Id       
	@Column(name = "cmpedtcodesk")
	@SequenceGenerator(name="CECSeq", sequenceName="SEQ_DBTCMPEDITCODE", allocationSize=1)
	@GeneratedValue(generator="CECSeq")
    private Long id;
	 
	/**
	 * if you specify fetch = FetchType.LAZY then you will see an error as JSON conversion tries to serialize the object but Lazy fetch will not fetch the object yet
	 *  
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cmpedtsk", nullable = false)	
	@JsonProperty(access = Access.WRITE_ONLY)
    private ComplianceEdit complianceEdit;		
	
	//@OneToMany(mappedBy = "ceCode", cascade = CascadeType.ALL, orphanRemoval=true)
	@OneToMany(mappedBy = "ceCode", cascade = CascadeType.ALL, orphanRemoval=false)
	private List<ComplianceEditCondSet> ceCondSets = new ArrayList<>();
	
	@NonNull
	private String code;	
	
	@Column(name="userid")
	private String userId;	
	
	@Column(name="chngdt")
	private Timestamp changeDate;
}
