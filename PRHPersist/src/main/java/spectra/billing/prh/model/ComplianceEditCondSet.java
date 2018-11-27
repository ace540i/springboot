package spectra.billing.prh.model;

import java.sql.Date;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name ="DBTCMPEDITCONDSET", schema = "MDPTRAC")
public class ComplianceEditCondSet  {

	@Id
	@Column(name="cmpedtcondsetsk")
	@SequenceGenerator(name="CECSSeq", sequenceName="SEQ_DBTCMPEDITCONDSET", allocationSize=1)
	@GeneratedValue(generator="CECSSeq")
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="cmpedtcodesk")
	@JsonProperty(access = Access.WRITE_ONLY)
	private ComplianceEditCode ceCode;
	
	@OneToMany(mappedBy = "ceCondSet", cascade = CascadeType.ALL, orphanRemoval=false)
	private List<ComplianceEditException> ceExceptions = new ArrayList<>();
	
	@NonNull
	private String name;
	
	@NonNull
	@Column(name="startdate")
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date startDate;
	
	@Column(name="enddate")
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date endDate;
	
	@NonNull
	@Column(name="userid")
	private String userId;
	
	@NonNull
	@Column(name="chngdt")
	private Timestamp changeDate;
	
	@Column(name="deletex")
	private String delete;
}
