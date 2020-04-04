package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hrms.domain.*;


@Component
public class InterviewDaoImpl implements InterviewDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static String createSchedule = "insert into interview (position_id, candidate_id, state, slot, panel) "
											+ "values (?,?,?,?,?)";
	private static String getAllInterviews = "select id, slot, panel, state from interview where position_id=? and "
											+ "candidate_id=?";
	private static String getInterviewingCandidates = "select distinct c.id, name, referral, referrer, resumelink from candidate c "
											+ "join interview i on i.candidate_id = c.id where i.position_id=?;";
	@Override
	@Transactional
	public int scheduleInterview(short positionId, String candidateId, Date slot, String panel) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(createSchedule, Statement.RETURN_GENERATED_KEYS);
	                ps.setInt(1, positionId);
	                ps.setString(2, candidateId);
	                ps.setString(3, InterviewState.SCHEDULED.toString());
	                ps.setTimestamp(4, new Timestamp(slot.getTime()));
	                ps.setString(5, panel);
	                return ps;
	              }, keyHolder);
	       
	    return keyHolder.getKey().intValue();
	}

	@Override
	@Transactional
	public List<Interview> getInterviewSlots(short positionId, String candidateId) {
		return jdbcTemplate.query(getAllInterviews, new InterviewRowMapper(), positionId, candidateId);
	}
	
	@Override
	@Transactional
	public List<Candidate> getInterviewingCanddidates(short positionId){ 
		return jdbcTemplate.query(getInterviewingCandidates, new CandidateRowMapper(), positionId);
	}

}

class InterviewRowMapper implements RowMapper<Interview> {
	@Override
	public Interview mapRow(ResultSet rs, int rowNum) throws SQLException {
		Interview i = new Interview();
		i.setId(rs.getString("id"));
		i.setPanel(rs.getString("panel"));
		i.setSlot(new Date(rs.getTimestamp("slot").getTime()));
		i.setState(InterviewState.valueOf(rs.getString("state")));
		return i;
	}

}