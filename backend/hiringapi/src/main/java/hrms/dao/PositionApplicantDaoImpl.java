package hrms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hrms.domain.*;

@Component
public class PositionApplicantDaoImpl implements PositionApplicantDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static String shortListed = ApplicantState.SHORTLISTED.toString();
	private static String resReject = ApplicantState.RESUME_REJECTED.toString();
	private static String intrvwng = ApplicantState.INTERVIEWING.toString();
	private static String applied = ApplicantState.APPLIED.toString();
	private static String activeStates = "'"+applied+"','"+shortListed+"','"+resReject+"','"+intrvwng+"'";
	
	private static String applyForPosition = "insert into applicant (position_id, candidate_id, state) values (?,?,?)";
	private static String processForPosition = "update applicant set state=?, processed_on=? where position_id=? and candidate_id=?";
	private static String queryByState = "select c.id,c.name,c.referral,c.referrer,c.resumelink from candidate as c "
			+ "inner join applicant as a on c.id=a.candidate_id where a.position_id=? and a.state=? order by c.created_on";
	private static String queryByStates = "select c.id,c.name,c.referral,c.referrer,c.resumelink, a.state from candidate as c "
			+ "inner join applicant as a on c.id=a.candidate_id where a.position_id=? and a.state in ("+activeStates+") order by c.created_on";
	private static String updateState = "update applicant set state=? where position_id=? and candidate_id=?";
	
	@Transactional
	@Override
	public void applyForPosition(Postion p, Set<String> candidateIds) {
		for(String candidateId : candidateIds) {
			jdbcTemplate.update(applyForPosition, p.getId(), candidateId,
									ApplicantState.APPLIED.toString());
		}

	}
	
	@Transactional
	@Override
	public void shortListApplicants(Postion p, Set<String> candidateIds) {
		for(String candidateId : candidateIds) {
			int numberOfRows = jdbcTemplate.update(processForPosition,
						ApplicantState.SHORTLISTED.toString(), new Date(), p.getId(), candidateId);
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
						ApplicantState.RESUME_REJECTED.toString(), new Date(), p.getId(), candidateId);
			if(numberOfRows==0) {
				//No row was updated, either non existent position or candidate
				throw new InvalidDataException("positionId ["+p.getId()+"] with candidateId ["+candidateId+"] not found in applicant");
			}
		}
	}
	
	@Transactional
	@Override
	public int markAsInterviewing(short positionId, String candidateId) {
		return jdbcTemplate.update(updateState,
				ApplicantState.INTERVIEWING.toString(), positionId, candidateId);
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
				new CandidateRowMapper(),p.getId(), ApplicantState.RESUME_REJECTED.toString());
	}
	
	@Override
	public List<Candidate> getInterviewingApplicants(Postion p) {
		return jdbcTemplate.query(queryByState,
				new CandidateRowMapper(),p.getId(), ApplicantState.INTERVIEWING.toString());
	}
	
	@Transactional
	@Override
	public List<Applicant> getActiveApplicants(Postion p) {
		return jdbcTemplate.query(queryByStates,
				new ApplicantRowMapper(),p.getId()
				//ApplicantState.SHORTLISTED.toString()+","+ ApplicantState.APPLIED.toString()+","+
				//ApplicantState.INTERVIEWING.toString()+","+ApplicantState.RESUME_REJECTED.toString()
				);
	}

}

class ApplicantRowMapper extends CandidateMapper implements RowMapper<Applicant> {
	@Override
	public Applicant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Candidate c = mapCandidate(rs, rowNum);
		Applicant a = new Applicant();
		a.setId(c.getId());a.setName(c.getName());a.setReferrer(c.getReferrer());
		a.setReferral(c.isReferral());a.setResumeLink(c.getResumeLink());
		a.setState(ApplicantState.valueOf(rs.getString("state")));
		return a;
				
	}
}
