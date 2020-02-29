package hrms.daotest;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hrms.dao.*;
import hrms.domain.*;
import hrmstest.HrmsTestConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HrmsTestConfig.class })
public class PositionApplicantDaoTest {
	@Autowired
	PositionApplicantDao posAppDao;
	
	@Autowired
	PositionDao posDao;
	
	@Test
	public void testApplyForPostion() {
		Postion p = posDao.getPosition((short)1);
		Set<String> candidateIds = new HashSet<String>();
		candidateIds.add("2");
		candidateIds.add("3");
		candidateIds.add("1");
		p.setId((short)2);		
		posAppDao.applyForPosition(p, candidateIds);
	}
	
	//@Test
	public void testForShortlisting() {
		Postion p = posDao.getPosition((short)1);
		Set<String> candidateIds = new HashSet<String>();
		candidateIds.add("4");
		
		posAppDao.shortListApplicants(p, candidateIds);
	}
	
	//@Test
	public void testForGettingApplicants() {
		Postion p = posDao.getPosition((short)1);
		List<Candidate> candidates = posAppDao.getUnprocessedApplicants(p);
		System.out.println(candidates);
		candidates = posAppDao.getShortListedApplicants(p);
		System.out.println(candidates);
	}
}
