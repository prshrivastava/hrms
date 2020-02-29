package hrms.dao;

import java.util.List;

import hrms.domain.Candidate;
import hrms.domain.Postion;

public interface CandidateDao {
	public Candidate createCandidate(Candidate c);
	public boolean updateCandidate(Candidate c);
	public boolean deleteCandidate(Candidate c);
}
