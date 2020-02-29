package hrms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
*/
import org.springframework.stereotype.Component;

//import com.mongodb.client.result.DeleteResult;

import hrms.domain.Candidate;
import hrms.domain.Postion;

//@Component
public class CandidateDaoMongoImpl 
		//implements CandidateDao 
{
/*
	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public Candidate createCandidate(Candidate c) {
		return mongoOperations.insert(c);
	}

	@Override
	public boolean updateCandidate(Candidate c) {
		Candidate updated = mongoOperations.findAndReplace(new Query(Criteria.where("id").is(c.getId())), c);
		if(updated != null)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean deleteCandidate(Candidate c) {
		DeleteResult result = mongoOperations.remove(c);
		if(result.getDeletedCount() == 1)
			return true;
		else
			return false;
	}
*/	
}
