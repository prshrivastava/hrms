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
		positionAppDao.rejectApplicants(p, candidates);
	}
	
	private boolean hasApplied(Postion p, Set<String> candidates) {
		/*Set<Candidate> unprocessed = positionAppDao.getUnprocessedApplicants(p);
		Set<String> unprocessedIds = unprocessed.stream().map(c -> c.getId()).collect(Collectors.toSet());
		for(String candidateId: candidates) {
			if(!unprocessedIds.contains(candidateId))
				return false;
		}*/
		return true;
	}
	
	private boolean createCandidates(Set<Candidate> candidates) {
		for(Candidate c: candidates) {
			candidateDao.createCandidate(c);
		}
		return true;
	}
	
	/*
	 * public boolean applyForPosition(short positionId, Set<Applicant> applicants)
	 * { Postion p = positionService.getPosition(positionId); return
	 * positionAppDao.applyForPosition(p, applicants); }
	 */
}
