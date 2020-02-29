package hrms.service;

import java.time.LocalDateTime;
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
	
	@Transactional
	public int scheduleInterview(short positionId, String candidateId, Date slot, String panel) {
		List<Candidate> shortListed = posApplicantDao.getShortListedApplicants(positionDao.getPosition(positionId));
		Set<String> shortListedIds = shortListed.stream().map(c -> c.getId()).collect(Collectors.toSet());
		if(!shortListedIds.contains(candidateId))
			throw new InvalidDataException("The candidate ["+candidateId+"] is not shortlisted for this position");
		return interviewDao.scheduleInterview(positionId, candidateId, slot, panel);
	}
	
	public List<Interview> getAllInterviews(short positionId, String candidateId){
		return interviewDao.getInterviewSlots(positionId, candidateId);
	}
}
