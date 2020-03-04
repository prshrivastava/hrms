package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hrms.domain.Candidate;

@Component
public class CandidateDaoImpl implements CandidateDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static String insertCandidate = "insert into candidate (name, resumelink, referral, referrer) values (?,?,?,?)";

	@Transactional
	@Override
	public Candidate createCandidate(Candidate c) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(insertCandidate, Statement.RETURN_GENERATED_KEYS);
	                ps.setString(1, c.getName());
	                ps.setString(2, c.getResumeLink());
	                ps.setBoolean(3, c.isReferral());
	                ps.setString(4, c.getReferrer());
	                return ps;
	              }, keyHolder);
	       
	    c.setId(keyHolder.getKey().toString());
	    return c;
	}

	@Override
	public boolean updateCandidate(Candidate c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCandidate(Candidate c) {
		// TODO Auto-generated method stub
		return false;
	}

}

abstract class CandidateMapper {
	public Candidate mapCandidate(ResultSet rs, int rowNum) throws SQLException {
		Candidate c = new Candidate();
		c.setId(Integer.toString(rs.getInt("id")));
		c.setName(rs.getString("name"));
		c.setReferral(rs.getBoolean("referral"));
		c.setReferrer(rs.getString("referrer"));
		c.setResumeLink(rs.getString("resumelink"));
		return c;
	}
}

class CandidateRowMapper extends CandidateMapper implements RowMapper<Candidate> {

	@Override
	public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
		return mapCandidate(rs, rowNum);
	}
	
}