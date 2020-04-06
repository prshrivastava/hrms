package hrms.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hrms.dao.*;
import hrms.domain.*;

@Service
public class InterviewService {
	@Autowired
	private InterviewDao interviewDao;
	
	@Autowired
	private PositionApplicantDao posApplicantDao;
	
	@Autowired
	private PositionDao positionDao;
	
	@Autowired
	private FeedbackDao feedbackDao;
	
	@Transactional
	public int scheduleInterview(short positionId, String candidateId, Date slot, String panel) {
		Postion p = positionDao.getPosition(positionId);
		List<Candidate> shortListed = posApplicantDao.getShortListedApplicants(p);
		List<Candidate> interviewing = posApplicantDao.getInterviewingApplicants(p);
		Set<String> shortListedIds = shortListed.stream().map(c -> c.getId()).collect(Collectors.toSet());
		Set<String> interviewingIds = interviewing.stream().map(c -> c.getId()).collect(Collectors.toSet());
		if(!shortListedIds.contains(candidateId) && !interviewingIds.contains(candidateId))
			throw new InvalidDataException("The candidate ["+candidateId+"] is not shortlisted for this position");
		
		posApplicantDao.markAsInterviewing(positionId, candidateId);
		return interviewDao.scheduleInterview(positionId, candidateId, slot, panel);
	}
	
	@Transactional
	public List<Interview> getAllInterviews(short positionId, String candidateId){
		return interviewDao.getInterviewSlots(positionId, candidateId);
	}
	
	@Transactional
	public List<Candidate> getAllInterviewingCandidates(short positionId) {
		return interviewDao.getInterviewingCanddidates(positionId);
	}
	
	@Transactional
	public int submitFeedback(int interviewId, String comments) {
		 //TODO: The interview should be marked as CONDUCTED 
		return feedbackDao.submitFeedback(interviewId, comments);
	}
	
	@Transactional
	public List<InterviewFeedback> getInterviewFeedback(int candidateId){
		return feedbackDao.getFeedback(candidateId);
	}
}
