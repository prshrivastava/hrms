package hrms.daotest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hrms.dao.InvalidDataException;
import hrms.dao.PositionDao;
import hrms.domain.Postion;
import hrmstest.HrmsTestConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HrmsTestConfig.class })
public class PositionDaoTest {
	
	@Autowired
	PositionDao positionDao;
	
	@Test
	public void testGetAllPositions() {
		List<Postion> allPositions = positionDao.getPositions();
		assertTrue(allPositions.size()>=2);
	}
	@Test
	public void testGetOnePosition() {
		Postion p = positionDao.getPosition((short) 1);
		assertEquals((short)1, p.getId());
	}
	
	@Test
	public void testUpdatePosition() {
		Postion p = positionDao.getPosition((short) 1);
		String hrmgr = Long.toString(System.currentTimeMillis());
		p.setHiringManager(hrmgr);p.setExperienceRange("0-10");p.setPriority((short)2);p.setSkills("Python and Django");
		p = positionDao.updatePosition(p);
		
		Postion updated = positionDao.getPosition((short)1);
		assertEquals(p, updated);
	}
	
	@Test
	public void testCreatePositionWithId() {
		assertThrows(InvalidDataException.class, () -> createPositionWithId()); 

	}
	@Test
	public void testCreatePosition() {
		Postion p = new Postion();
		p.setName("Python developer");p.setSkills("Python, pandas");p.setExperienceRange("2-8");
		p = positionDao.createPosition(p);
		assertTrue(p.getId()>0);
	}
	private void createPositionWithId() {
		Postion p = new Postion();
		p.setId((short)2);
		p.setName("Genius");p.setSkills("Magic");p.setExperienceRange("11-110");
		positionDao.createPosition(p);
	}

}
