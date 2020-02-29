package hrms.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hrms.domain.Applicant;
import hrms.domain.Candidate;
import hrms.domain.Postion;

@Component
public class PositionApplicantDaoImpl implements PositionApplicantDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static String applyForPosition = "insert into applicant (position_id, candidate_id, state) values (?,?,?)";
	private static String processForPosition = "update applicant set state=?, processed_on=? where position_id=? and candidate_id=?";
	private static String queryByState = "select c.id,c.name,c.referral,c.referrer,c.resumelink from candidate as c "
			+ "inner join applicant as a on c.id=a.candidate_id where a.position_id=? and a.state=? order by c.created_on";
	
	@Transactional
	@Override
	public void applyForPosition(Postion p, Set<String> candidateIds) {
		for(String candidateId : candidateIds) {
			int numberOfRows = jdbcTemplate.update(applyForPosition, p.getId(), candidateId,
									ApplicantState.APPLIED.toString());
		}

	}
	
	@Transactional
	@Override
	public void shortListApplicants(Postion p, Set<String> candidateIds) {
		for(String candidateId : candidateIds) {
			int numberOfRows = jdbcTemplate.update(processForPosition,
						ApplicantState.SHORTLISTED.toString(), new Date(), p.getId(), candidateId);
			System.out.println(numberOfRows);
			if(numberOfRows==0) {
				//No row was updated, either non existent position or candidate
				throw new InvalidDataException("positionId ["+p.getId()+"] with candidateId ["+candidateId+"] not found in applicant");
			}
		}
	}
	
	@Transactional
	@Override
	public void rejectApplicants(Postion p, Set<String> candidateIds) {
		for(String candidateId : candidateIds) {
			int numberOfRows = jdbcTemplate.update(processForPosition,
						ApplicantState.REJECTED.toString(), new Date(), p.getId(), candidateId);
			if(numberOfRows==0) {
				//No row was updated, either non existent position or candidate
				throw new InvalidDataException("positionId ["+p.getId()+"] with candidateId ["+candidateId+"] not found in applicant");
			}
		}
	}

	@Override
	public List<Candidate> getUnprocessedApplicants(Postion p) {
		return jdbcTemplate.query(queryByState,
											new CandidateRowMapper(),p.getId(), ApplicantState.APPLIED.toString());
	}

	@Override
	public List<Candidate> getShortListedApplicants(Postion p) {
		return jdbcTemplate.query(queryByState,
				new CandidateRowMapper(),p.getId(), ApplicantState.SHORTLISTED.toString());
	}

	@Override
	public List<Candidate> getRejectedApplicants(Postion p) {
		return jdbcTemplate.query(queryByState,
				new CandidateRowMapper(),p.getId(), ApplicantState.REJECTED.toString());
	}

}
