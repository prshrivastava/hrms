package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hrms.domain.Postion;

@Component
public class PositionDaoImpl implements PositionDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static String getPositions = "select id,name,skills,experienceRange, hiringManager, priority from position";
	private static String getPosition = "select * from position where id=?";
	private static String createPosition = "insert into position (name, skills, experienceRange, hiringManager, priority)"
			+ " values (?,?,?,?,?)";
	private static String updatePosition = "update position set name=?,skills=?,experienceRange=?,hiringManager=?, "
			+ "priority=? where id=?";
	
	@Transactional
	@Override
	public Postion getPosition(short positionId) {
		return jdbcTemplate.queryForObject(getPosition, new Object[] {positionId}, new PositionRowMapper());
	}

	@Transactional
	@Override
	public List<Postion> getPositions() {
		return jdbcTemplate.query(getPositions, new PositionRowMapper());
	}
	
	@Transactional
	@Override
	public Postion createPosition(Postion p) {
		if(p.getId()!=0)
			throw new InvalidDataException("creating a new position with non zero id is not allowed");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(createPosition, Statement.RETURN_GENERATED_KEYS);
	                ps.setString(1, p.getName());
	                ps.setString(2, p.getSkills());
	                ps.setString(3, p.getExperienceRange());
	                ps.setString(4, p.getHiringManager());
	                ps.setShort(5, p.getPriority());
	                return ps;
	              }, keyHolder);
	       
	    p.setId(keyHolder.getKey().shortValue());
	    return p;
	}

	@Transactional
	@Override
	public Postion updatePosition(Postion p) {
		int rowsUpdated = jdbcTemplate.update(updatePosition, p.getName(),p.getSkills(),p.getExperienceRange(),
								p.getHiringManager(),p.getPriority(), p.getId());
		if(rowsUpdated !=1)
			throw new InvalidDataException("Updating position for id=["+p.getId()+"] updated "+rowsUpdated+" rows");
		return p;
	}

}

class PositionRowMapper implements RowMapper<Postion> {

	@Override
	public Postion mapRow(ResultSet rs, int rowNum) throws SQLException {
		Postion p = new Postion();
		p.setId(rs.getShort("id"));
		p.setName(rs.getString("name"));
		p.setSkills(rs.getString("skills"));
		p.setExperienceRange(rs.getString("experienceRange"));
		p.setHiringManager(rs.getString("hiringManager"));
		p.setPriority(rs.getShort("priority"));
		return p;
		
	}
	
}
