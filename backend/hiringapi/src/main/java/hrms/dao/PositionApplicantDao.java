package hrms.dao;

import java.util.*;

import hrms.domain.Applicant;
import hrms.domain.Candidate;
import hrms.domain.Postion;

public interface PositionApplicantDao {
	
	public void applyForPosition(Postion p, Set<String> candidateIds);
	public void shortListApplicants(Postion p, Set<String> candidateIds);
	public void rejectApplicants(Postion p, Set<String> candidateIds);
	public List<Candidate> getUnprocessedApplicants(Postion p);
	public List<Candidate> getShortListedApplicants(Postion p);
	public List<Candidate> getRejectedApplicants(Postion p);

}

enum ApplicantState {
	APPLIED, SHORTLISTED, REJECTED
}