package hrms.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hrms.dao.*;
import hrms.domain.*;

@Service
public class PositionApplicantService {
	
	@Autowired
	PostionService positionService;
	
	@Autowired
	PositionApplicantDao positionAppDao;
	
	@Autowired
	PositionDao positionDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	InterviewDao interviewDao;
	
	public List<Candidate> getShortlistedApplicants(Postion p){
		return positionAppDao.getShortListedApplicants(p);
	}
	public List<Candidate> getRejectedApplicants(Postion p){
		return positionAppDao.getRejectedApplicants(p);
	}
	
	public List<Candidate> getUnprocessedApplicants(Postion p){
		return positionAppDao.getUnprocessedApplicants(p);
	}
	
	@Transactional
	public void applyForPosition(Postion p, Set<Candidate> candidates) {
		createCandidates(candidates);
		Set<String> candidateIds = candidates.stream().map(c -> c.getId()).collect(Collectors.toSet());
		positionAppDao.applyForPosition(p, candidateIds);
	}
	
	@Transactional
	public void shortListApplicants(short positionId, Set<String> candidates) {
		Postion p = positionDao.getPosition(positionId);
		positionAppDao.shortListApplicants(p, candidates);
	}
	
	@Transactional
	public void rejectApplicants(short positionId, Set<String> candidates) {
		Postion p = positionDao.getPosition(positionId);
		List<Candidate> interviewing = interviewDao.getInterviewingCanddidates(positionId);
		Set<String> interviewingIds = interviewing.stream().map(c -> c.getId()).collect(Collectors.toSet());
		if(candidates.removeAll(interviewingIds)) {
			//The set got modified because some of the candidates were already interviewing
			throw new InvalidDataException("Cannot reject already interviewing candidates");
		}
		positionAppDao.rejectApplicants(p, candidates);
	}
	
	@Transactional
	public List<Applicant> getActiveApplicants(short positionId) {
		Postion p = positionDao.getPosition(positionId);
		return positionAppDao.getActiveApplicants(p);
	}
	
	private boolean createCandidates(Set<Candidate> candidates) {
		for(Candidate c: candidates) {
			candidateDao.createCandidate(c);
		}
		return true;
	}

}
