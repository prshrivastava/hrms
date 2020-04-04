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
import hrms.service.InterviewService;
import hrms.service.PositionApplicantService;
import hrmstest.HrmsTestConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HrmsTestConfig.class })
public class InterviewServiceTest {
	@Autowired
	private InterviewService interviewService;
	
	@Autowired
	PositionApplicantService posAppService;
	
	@Autowired
	PositionDao posDao;
	
	@Test
	public void testScheduleInterview() {
		Postion p1 = posDao.getPosition((short)1);
		Candidate c1 = getOneShortlistedCandidate(p1);
		Date slot = new Date();
		String panel = "Interviewer 1";
		int interviewId = interviewService.scheduleInterview((short)1, c1.getId(), slot, panel);
		
		assertTrue(interviewId > 0);
		List<Interview> interviews = interviewService.getAllInterviews(p1.getId(), c1.getId());
		Interview interview = interviews.get(0);
		assertEquals(slot,interview.getSlot());
		assertEquals(panel, interview.getPanel());
		assertTrue(Integer.parseInt(interview.getId()) == interviewId);
	}
	
	@Test
	public void testScheduleInterviewWithoutShortlisting() {
		Postion p1 = posDao.getPosition((short)1);
		List<Candidate> appliedcandidates = createAndApplyOneCandidate(p1);
		Candidate c1 = appliedcandidates.get(0);
		Date slot = new Date();
		String panel = "Interviewer 1";
		assertThrows(InvalidDataException.class,
							() -> interviewService.scheduleInterview(p1.getId(), c1.getId(), slot, panel));
		
		
	}
	@Test
	public void testScheduleInterviewForResumeRejected() {
		Postion p1 = posDao.getPosition((short)1);
		List<Candidate> appliedcandidates = createAndApplyOneCandidate(p1);
		Candidate c1 = appliedcandidates.get(0);
		Set<String> candidateIds = appliedcandidates.stream().map(c -> c.getId()).collect(Collectors.toSet());
		posAppService.rejectApplicants(p1.getId(), candidateIds);
		
		Date slot = new Date();
		String panel = "Interviewer 1";
		assertThrows(InvalidDataException.class,
							() -> interviewService.scheduleInterview(p1.getId(), c1.getId(), slot, panel));
		
	}
	@Test
	public void testGetAllInterviews() {
		Postion p1 = posDao.getPosition((short)1);
		Candidate c1 = getOneShortlistedCandidate(p1);
		Date slot1 = new Date();
		String panel1 = "Interviewer 1";
		int interviewId1 = interviewService.scheduleInterview(p1.getId(), c1.getId(), slot1, panel1);
		Date slot2 = new Date(); String panel2 = "Interviewer 2";
		int interviewId2 = interviewService.scheduleInterview(p1.getId(), c1.getId(), slot2, panel2);
		
		List<Interview> interviews = interviewService.getAllInterviews(p1.getId(), c1.getId());
		assertTrue(interviews.size()==2);
		
	}
	
	@Test
	public void testGetAllInterviewingCandidates() {
		
		Postion p1 = posDao.getPosition((short)1);
		Candidate c1 = getOneShortlistedCandidate(p1);
		
		Date slot1 = new Date();
		String panel1 = "Interviewer 1";
		int interviewId1 = interviewService.scheduleInterview(p1.getId(), c1.getId(), slot1, panel1);
		
		Candidate c2 = getOneShortlistedCandidate(p1);
		
		Date slot2 = new Date();
		String panel2 = "Interviewer 2";
		int interviewId2 = interviewService.scheduleInterview(p1.getId(), c2.getId(), slot1, panel2);
		
		List<Candidate> interviewCandidates = interviewService.getAllInterviewingCandidates(p1.getId());
		assertTrue(interviewCandidates.contains(c1));
		assertTrue(interviewCandidates.contains(c2));
	}
	
	private List<Candidate> createAndApplyOneCandidate(Postion p1) {
		Candidate c1 = TestUtilities.generateRandomCandidate();
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(c1);
		
		List<Candidate> appliedcandidates = posAppService.createCandidatesAndapplyForPosition(p1, candidates);
		return appliedcandidates;
	}
	
	private Candidate getOneShortlistedCandidate(Postion p1) {
		
		List<Candidate> appliedcandidates = createAndApplyOneCandidate(p1);
		Candidate c1 = appliedcandidates.get(0);
		Set<String> candidateIds = appliedcandidates.stream().map(c -> c.getId()).collect(Collectors.toSet());
		posAppService.shortListApplicants(p1.getId(), candidateIds);
		
		return c1;
	}

}
