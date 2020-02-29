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
		  assertEquals("Jane Doe", out.getName());
		  System.out.println(c.getId());
	}
	
	//@Test
	public void testUpdateCandidate() {
		Candidate c = getTestCandidate("Jane Doe");
		Candidate testCandidate = candidateDao.createCandidate(c); 
		
		testCandidate.setName("Jason Doe"); testCandidate.setResumeLink("http://newlink");
		testCandidate.setReferral(true);
		boolean result = candidateDao.updateCandidate(testCandidate);
		
		assertTrue(result);
		
		candidateDao.deleteCandidate(testCandidate);
	
	}
	
	private Candidate getTestCandidate(String name) {
		Candidate c = new Candidate(); c.setName(name); c.setReferral(false);
		c.setResumeLink("http://dnvdnnd"); 
		return c;
	}

}
