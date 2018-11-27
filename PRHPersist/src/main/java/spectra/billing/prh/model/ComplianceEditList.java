package spectra.billing.prh.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name ="DBTCMPEDITLIST", schema = "MDPTRAC")
public class ComplianceEditList {

	@Id
	@Column(name="cmpeditlistsk")
	@SequenceGenerator(name="CELSeq", sequenceName="SEQ_DBTCMPEDITLIST", allocationSize=1)
	@GeneratedValue(generator="CELSeq")		
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="cmpedtexpsk")
	@JsonProperty(access = Access.WRITE_ONLY)
	private ComplianceEditException ceException;
	
	@NonNull
	@Column(name="listitem")
	private String listItem;
	
	@Column(name="deletex")
	private String delete;
	
	@Column(name="userid")
	private String userId;
	
	@Column(name="chngdt")
	private Timestamp changeDate;	
	
	@NonNull
	@Column(name="startdate")
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date startDate;
	
	@Column(name="enddate")
	@JsonFormat
	  (shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date endDate;
	
	
}
