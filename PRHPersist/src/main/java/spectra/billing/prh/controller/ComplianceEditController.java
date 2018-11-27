package spectra.billing.prh.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spectra.billing.prh.model.ComplianceEdit;
import spectra.billing.prh.model.ComplianceEditCode;
import spectra.billing.prh.model.ComplianceEditCondSet;
import spectra.billing.prh.model.ComplianceEditException;
import spectra.billing.prh.model.ComplianceEditList;
import spectra.billing.prh.repository.ComplianceEditCodeRepository;
import spectra.billing.prh.repository.ComplianceEditCondSetRepository;
import spectra.billing.prh.repository.ComplianceEditExceptionRepository;
import spectra.billing.prh.repository.ComplianceEditListRepository;
import spectra.billing.prh.repository.ComplianceEditRepository;

@RestController
@RequestMapping("/prh")
public class ComplianceEditController {
	
	@Autowired
	ComplianceEditRepository complianceEditRepository;
	
	@Autowired
	ComplianceEditCodeRepository complianceEditCodeRepository;
	
	@Autowired
	ComplianceEditCondSetRepository complianceEditCondSetRepository;
	
	@Autowired
	ComplianceEditExceptionRepository complianceEditExceptionRepository;

	@Autowired
	ComplianceEditListRepository complianceEditListRepository;
	
	@GetMapping("/ce")
	public List<ComplianceEdit> getAllComplianceEdits() {
	    return complianceEditRepository.findAll();
	}
		
	@GetMapping("/cec")
	public List<ComplianceEditCode> getAllComplianceEditCodes() {
	    return complianceEditCodeRepository.findAll();
	}
	
	@GetMapping("/cec/{id}")
	public Optional<ComplianceEditCode> getComplianceEditCodeById(@PathVariable(value = "id") Long cecId) {
	    return complianceEditCodeRepository.findById(cecId);
	}
	
	@GetMapping("/cecs")
	public List<ComplianceEditCondSet> getAllComplianceEditCondSets() {
	    return complianceEditCondSetRepository.findAll();
	}	
	
	@GetMapping("/cecs/{id}")
	public Optional<ComplianceEditCondSet> getComplianceEditCondSetById(@PathVariable(value = "id") Long cecsId) {
	    return complianceEditCondSetRepository.findById(cecsId);
	}			
	
	@GetMapping("/cee")
	public List<ComplianceEditException> getAllComplianceEditExceptions() {
	    return complianceEditExceptionRepository.findAll();
	}
	
	@GetMapping("/cel")
	public List<ComplianceEditList> getAllComplianceEditLists() {
	    return complianceEditListRepository.findAll();
	}		
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/cec", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ComplianceEditCode CreateCode(@Valid @RequestBody ComplianceEditCode complianceEditCode) {
		System.out.println("creating new code");
		System.out.println("complianceEditCode.getCode(): " + complianceEditCode.getCode());
		System.out.println("complianceEditCode.getUserId() : " + complianceEditCode.getUserId());
		System.out.println("complianceEditCode.getId() : " + complianceEditCode.getId());
		
		complianceEditCode.setComplianceEdit(complianceEditRepository.findById(201L)
				.orElseThrow(() -> new EntityNotFoundException("ComplianceEdit cannot be found by id:201" )));		
		complianceEditCode.getCeCondSets().forEach(c -> { 
			c.setCeCode(complianceEditCode);
			c.setChangeDate(new Timestamp(System.currentTimeMillis()));
			c.setDelete("N");
	
			for (ComplianceEditException ce:new ArrayList <> (c.getCeExceptions())) {
				if (ce.getCeLists().isEmpty()) {
					c.getCeExceptions().remove(ce);
				}
			}
			c.getCeExceptions().forEach(e -> {
				e.setCeCondSet(c);
				e.setChangeDate(new Timestamp(System.currentTimeMillis()));
				e.setDelete("N");
				if(e.getConditionType() == null) {
					e.setConditionType("Hold for");
				}
				
				for (ComplianceEditList cel:new ArrayList <> (e.getCeLists())) {
					if (cel == null) {
						e.getCeLists().remove(cel);
					}
				}
				e.getCeLists().forEach(l -> {
					l.setCeException(e);
					l.setChangeDate(new Timestamp(System.currentTimeMillis()));
					l.setDelete("N");
					if(e.getCondition().equals("CHARGETYPE") || e.getCondition().equals("CLIENTTYPE")) {
						l.setStartDate(c.getStartDate());
				  	}
				});			 
			});
		});
		
//		}
		
		
		return complianceEditCodeRepository.save(complianceEditCode);	
		
	}		
	@CrossOrigin(origins = "*")
	@RequestMapping(value="/cec/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ComplianceEditCode UpdateCode(@PathVariable(value = "id") Long cecId, @Valid @RequestBody ComplianceEditCode complianceEditCode) {
		System.out.println("updating new code");
		System.out.println("complianceEditCode.getCode(): " + complianceEditCode.getCode());
		complianceEditCode.setComplianceEdit(complianceEditRepository.findById(201L)
				.orElseThrow(() -> new EntityNotFoundException("ComplianceEdit cannot be found by id:201" )));
		System.out.println("complianceEditCode.getComplianceEdit().getId: " + complianceEditCode.getComplianceEdit().getId());
		
		//First update all the records for the given compliance edit code
		
		ComplianceEditCode ceCodeFromRep = complianceEditCodeRepository.findById(cecId)
				.orElseThrow(() -> new EntityNotFoundException("ComplianceEditCode cannot be found by id:" + cecId));
		complianceEditCodeRepository.refresh(ceCodeFromRep);
		ceCodeFromRep.getCeCondSets().forEach(c -> { 
			c.setDelete("Y");
			System.out.println("cond set name from rep: " + c.getName());
			c.getCeExceptions().forEach(e -> {
				e.setDelete("Y");
				
				e.getCeLists().forEach(l -> {
					l.setDelete("Y");
				});
			});
		});		
		ComplianceEditCode updatedCEC = complianceEditCodeRepository.saveAndFlush(ceCodeFromRep);
		System.out.println("done updating existing records: " + updatedCEC.getCeCondSets().get(0).getName());
		
		complianceEditCode.getCeCondSets().forEach(c -> { 
			c.setId(null);
			c.setCeCode(complianceEditCode);
			c.setChangeDate(new Timestamp(System.currentTimeMillis()));
			c.setDelete("N");
			
			for (ComplianceEditException ce:new ArrayList <> (c.getCeExceptions())) {
				if (ce.getCeLists().isEmpty()) {
					c.getCeExceptions().remove(ce);
				}
			}
			c.getCeExceptions().forEach(e -> {
				e.setId(null);
				e.setCeCondSet(c);
				e.setChangeDate(new Timestamp(System.currentTimeMillis()));
				e.setDelete("N");
				if(e.getConditionType() == null) {
					e.setConditionType("Hold for");
				}
				for (ComplianceEditList cel:new ArrayList <> (e.getCeLists())) {
					if (cel == null) {
						e.getCeLists().remove(cel);
					}
				}
				e.getCeLists().forEach(l -> {
					l.setId(null);
					l.setCeException(e);
					l.setChangeDate(new Timestamp(System.currentTimeMillis()));
					l.setDelete("N");
					if(e.getCondition().equals("CHARGETYPE") || e.getCondition().equals("CLIENTTYPE")) {
						l.setStartDate(c.getStartDate());
				  	}
				});
			});
		});
		
		
		System.out.println("inserting new records");
		
		return complianceEditCodeRepository.saveAndFlush(complianceEditCode);	
		
	}			
}
