package hrms.dao;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
*/
import org.springframework.stereotype.Component;

//import com.mongodb.client.result.UpdateResult;

import hrms.domain.Applicant;
import hrms.domain.Candidate;
import hrms.domain.PositionApplicants;
import hrms.domain.Postion;

//@Component
public class PositionApplicantDaoMongoImpl 
	//implements PositionApplicantDao 
	{
	/*
	@Autowired
	MongoOperations mongoOperations;

	
	@Override
	public boolean shortListApplicants(Postion p, Set<Applicant> applicants) {
		Set<String> candidateIds = applicants.stream().map(a -> a.getCandidateId()).collect(Collectors.toSet());
		Query query = new Query(new Criteria("positionId").is(p.getId()));
		Update update = new Update();
		update = update.pullAll("candidates", candidateIds.toArray())
				.pullAll("rejected.candidateId", candidateIds.toArray());
	
		update = update.new AddToSetBuilder("shortlisted").each(applicants);
		PositionApplicants oldObject = mongoOperations.findAndModify(query, update, PositionApplicants.class);
		if(oldObject == null)
			return false;
		else
			return true;
	}

	@Override
	public boolean rejectApplicants(Postion p, Set<Applicant> applicants) {
		Set<String> candidateIds = applicants.stream().map(a -> a.getCandidateId()).collect(Collectors.toSet());
		Query query = new Query(new Criteria("positionId").is(p.getId()));
		Update update = new Update();
		update = update.pullAll("candidates", candidateIds.toArray())
				.pullAll("shortlisted.candidateId", candidateIds.toArray());
	
		update = update.new AddToSetBuilder("rejected").each(applicants);
		PositionApplicants oldObject = mongoOperations.findAndModify(query, update, PositionApplicants.class);
		if(oldObject == null)
			return false;
		else
			return true;
	}

	@Override
	public Set<Candidate> getUnprocessedApplicants(Postion p) {
		Query query = new Query(new Criteria("positionId").is(p.getId()));
		query.fields().exclude("shortlisted").exclude("rejected");
		
		PositionApplicants posApplicants = mongoOperations.findOne(
											query,
											PositionApplicants.class);
		Set<String> candidateIds =  posApplicants.getCandidates();
		
		Query queryCandidates = new Query(new Criteria("id").in(candidateIds));
		
		List<Candidate> candidates = mongoOperations.find(queryCandidates, Candidate.class);
		
		return new HashSet<Candidate>(candidates);
	}

	@Override
	public Set<Applicant> getShortListedApplicants(Postion p) {
		Query query = new Query(new Criteria("positionId").is(p.getId()));
		query.fields().exclude("candidates").exclude("rejected");
		
		PositionApplicants posApplicants = mongoOperations.findOne(
											query,
											PositionApplicants.class);
		return posApplicants.getShortlisted();
	}

	@Override
	public Set<Applicant> getRejectedApplicants(Postion p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean applyForPosition(Postion p, Set<String> candidateIds) {
		Query query = new Query(new Criteria("positionId").is(p.getId()));
		Update update = new Update();
		update = update.new AddToSetBuilder("candidates").each(candidateIds);
		UpdateResult result = mongoOperations.upsert(query, update, PositionApplicants.class);
		if(result.getModifiedCount()==1 || result.getUpsertedId().isNumber())
			return true;
		else
			return false;
	}

	/*@Override
	public boolean applyForPosition(Postion p, Set<Applicant> applicants) {
		UpdateResult result = mongoOperations.upsert(new Query(Criteria.where("positionId").is(p.getId())),
						 new Update().new AddToSetBuilder("applicants").each(applicants),
						PositionApplicants.class );
		if(result.getModifiedCount()==1 || result.getUpsertedId().isNumber())
			return true;
		else
			return false;
	}*/
}
