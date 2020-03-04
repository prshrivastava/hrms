package hrms.dao;

import java.util.Date;
import java.util.List;

import hrms.domain.Candidate;
import hrms.domain.Interview;

public interface InterviewDao {
	public int scheduleInterview(short positionId, String candidateId, Date slot, String panel);
	public List<Interview> getInterviewSlots(short positionId, String candidateId);
	public List<Candidate> getInterviewingCanddidates(short positionId);
}


