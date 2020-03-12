package hrms.daotest;

//import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hrmstest.HrmsTestConfig;
import hrms.dao.CandidateDao;
import hrms.domain.Candidate;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HrmsTestConfig.class })
public class CandidateDaoTest {

	@Autowired
	CandidateDao candidateDao;

	@Test
	public void testCreateCandidate() {

		  Candidate c = new Candidate(); c.setName("Jane Doe"); c.setReferral(false);
		  c.setResumeLink("http://dnvdnxxnd"); 
		  Candidate out = candidateDao.createCandidate(c); 
		  c.setId(out.getId());
		  assertEquals(c,out);
		  System.out.println(c.getId());
	}
	
	@Test
	public void testUpdateCandidate() {
		Candidate c = candidateDao.getCandidate("1");
		c.setName("Jason Doe"); c.setResumeLink("http://newlink");
		c.setReferral(true);
		candidateDao.updateCandidate(c);
		
		Candidate updated = candidateDao.getCandidate("1");
		
		assertEquals(c, updated);
	}
}
