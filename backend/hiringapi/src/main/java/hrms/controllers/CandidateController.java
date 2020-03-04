package hrms.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hrms.domain.*;
import hrms.service.*;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/candidate")
public class CandidateController {
	
	@Autowired
	PositionApplicantService posAppService;
	
	@Autowired
	PostionService positionService;

	@PostMapping("/apply/{id}")
	public void aplyForPosition(@PathVariable short id, @RequestBody Set<Candidate> candidates) {
		Postion p = positionService.getPosition(id);
		posAppService.applyForPosition(p, candidates);
	}
	
	@PostMapping("/shortlist/{id}")
	public void markShortlist(@PathVariable short id, @RequestBody Set<String> candidateIds) {
		System.out.println(candidateIds);
		System.out.println(id);
		posAppService.shortListApplicants(id, candidateIds);
	}
	
	@PostMapping("/reject/{id}")
	public void markReject(@PathVariable short id, @RequestBody Set<String> candidateIds) {
		System.out.println(candidateIds);
		System.out.println(id);
		posAppService.rejectApplicants(id, candidateIds);
	}
	
	@GetMapping("/applied/{id}")
	public List<Candidate> getUnprocessedCandidates(@PathVariable short id) {
		Postion p = positionService.getPosition(id);
		return posAppService.getUnprocessedApplicants(p);
	}
	
	@GetMapping("/shortlisted/{id}")
	public List<Candidate> getShortlistedCandidates(@PathVariable short id) {
		Postion p = positionService.getPosition(id);
		return posAppService.getShortlistedApplicants(p);
	}
	@GetMapping("/rejected/{id}")
	public List<Candidate> getRejectedCandidates(@PathVariable short id) {
		Postion p = positionService.getPosition(id);
		return posAppService.getRejectedApplicants(p);
	}
	
	@GetMapping("/active/{id}")
	public List<Applicant> getActiveApplicants(@PathVariable short id) {
		return posAppService.getActiveApplicants(id);
	}
	
	
}
