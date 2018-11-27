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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name ="DBTCMPEDITEXCEPT", schema = "MDPTRAC")
public class ComplianceEditException {

	@Id
	@Column(name="cmpedtexpsk")
	@SequenceGenerator(name="CEESeq", sequenceName="SEQ_DBTCMPEDITEXCEPT", allocationSize=1)
	@GeneratedValue(generator="CEESeq")
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="cmpedtcondsetsk")
	@JsonProperty(access = Access.WRITE_ONLY)
	private ComplianceEditCondSet ceCondSet;
	
	@OneToMany(mappedBy = "ceException", cascade = CascadeType.ALL, orphanRemoval=false)
	private List<ComplianceEditList> ceLists = new ArrayList<>();
	
	@NonNull
	private String condition;
	
//	@NonNull
	private String conditionType;
	
	@Column(name="userid")
	private String userId;
	
	@Column(name="chngdt")
	private Timestamp changeDate;	
	
	@Column(name="deletex")
	private String delete;
	
}
