package hrms.servicetest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

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
	
	@Test
	public void testApply() {
		Candidate c1 = TestUtilities.generateRandomCandidate();
		Postion p1 = posDao.getPosition((short)1);
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(c1);
		
		List<Candidate> appliedcandidates = posAppService.createCandidatesAndapplyForPosition(p1, candidates);
		
		List<Candidate> unprocessed = posAppService.getUnprocessedApplicants(p1);
		assertTrue(unprocessed.containsAll(appliedcandidates));
	}
	@Test
	public void testShortlistPosition() {
		Candidate c1 = TestUtilities.generateRandomCandidate();
		Postion p1 = posDao.getPosition((short)1);
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(c1);
				
		List<Candidate> appliedcandidates = posAppService.createCandidatesAndapplyForPosition(p1, candidates);
		
		Set<String> candidateIds = appliedcandidates.stream().map(c -> c.getId()).collect(Collectors.toSet());

		posAppService.shortListApplicants((short)1, candidateIds);
		
		List<Candidate> shortListed = posAppService.getShortlistedApplicants(p1);
		assertTrue(shortListed.containsAll(appliedcandidates));
	}
	
	@Test
	public void testShortlistWithoutapply() {
		Candidate c1 = candidateDao.getCandidate("1");
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(c1);
				
		Set<String> candidateIds = candidates.stream().map(c -> c.getId()).collect(Collectors.toSet());

		assertThrows(InvalidDataException.class, () -> posAppService.shortListApplicants((short)1, candidateIds));
	}
	@Test
	public void testReject() {
		Candidate c1 = TestUtilities.generateRandomCandidate();
		Postion p1 = posDao.getPosition((short)1);
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(c1);
				
		List<Candidate> appliedcandidates = posAppService.createCandidatesAndapplyForPosition(p1, candidates);
		
		Set<String> candidateIds = appliedcandidates.stream().map(c -> c.getId()).collect(Collectors.toSet());

		posAppService.rejectApplicants((short)1, candidateIds);
		
		List<Candidate> rejected = posAppService.getRejectedApplicants(p1);
		assertTrue(rejected.containsAll(appliedcandidates));
	}
	@Test
	public void  testRejectWithoutApply() {
		Candidate c1 = candidateDao.getCandidate("2");

		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(c1);
				
		Set<String> candidateIds = candidates.stream().map(c -> c.getId()).collect(Collectors.toSet());
		assertThrows(InvalidDataException.class, () -> posAppService.rejectApplicants((short)1, candidateIds));
	}
	
}
