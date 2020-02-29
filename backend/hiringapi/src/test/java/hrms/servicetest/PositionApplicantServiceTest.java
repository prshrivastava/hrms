package hrms.servicetest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hrms.dao.*;
import hrms.domain.*;
import hrms.service.PositionApplicantService;
import hrmstest.HrmsTestConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HrmsTestConfig.class })
public class PositionApplicantServiceTest {
	
	@Autowired
	PositionApplicantService posAppService;
	
	@Autowired
	CandidateDao candidateDao;
	@Autowired
	PositionDao posDao;
	
	private  Candidate c1, c2;
	private  Postion p1;
	
	@Test
	public void testShortlistPosition() {
		
		setupCandidates();
		setUpPositions();
		
		Set<Candidate> candidates = new HashSet<Candidate>();
		candidates.add(c1);
		candidates.add(c2);
		
		
		posAppService.applyForPosition(p1, candidates);

		/*
		Set<String> candidateIds = candidates.stream().map(c -> c.getId()).collect(Collectors.toSet());
		result = posAppService.shortListApplicants(p1.getId(), candidateIds);
		assertTrue(result);
		*/
		
	}
	
	//@Test
	public void testReject() {
		Set<String> candidateIds = new HashSet<String>();
		candidateIds.add("2");
		posAppService.shortListApplicants((short)1, candidateIds);

	}
	//@BeforeAll
	public  void setupCandidates() {
		c1 = new Candidate();
		c1.setName("Jane Doe 2");c1.setResumeLink("https://drive.com/janedoe");
		c2 = new Candidate();
		c2.setName("John Doe 2");c2.setResumeLink("http://drive.com/johndoe");
		
		System.out.println("Done setting up candidates");
		
	}
	
	
	public void cleanupCandidates() {
		
	}
	
	
	//@BeforeAll
	public  void setUpPositions() {
		p1 = new Postion();
		p1.setName("Test Developer");p1.setExperienceRange("3-8");
		p1.setSkills("JUnit testing");
		p1 = posDao.createPosition(p1);
		System.out.println("Done setting up position "+p1);
	}

}
