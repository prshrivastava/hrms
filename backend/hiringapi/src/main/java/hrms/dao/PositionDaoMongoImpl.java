package hrms.dao;

import java.util.List;

//import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
*/
import hrms.domain.Postion;

//@Component
public class PositionDaoMongoImpl 
	//implements PositionDao 
	{
/*
	@Autowired
	MongoOperations mongoOperations;
	
	public Postion getPosition(short positionId) {
		//return "Hello from MongoDao2";
		return mongoOperations.findOne(new Query(where("id").is(positionId)), hrms.domain.Postion.class);
		//return mongoOperations.findById(positionId, hrms.domain.Postion.class);
	}

	@Override
	public List<Postion> getPositions() {
		/*
		 * FindIterable<Document> allPositions =
		 * mongoClient.getDatabase("hrms").getCollection("positions").find();
		 * for(Document position: allPositions) { System.out.println(position); }
		 */
		//List<Postion> allPositions = mongoOperations.findAll(hrms.domain.Postion.class);
		//return allPositions;
		/*
		 * for(Postion p: allPositions) { System.out.println(p); } return null;
		 */
	/*}
	public Postion createPosition(Postion position) {
		position.setId(nextPositionId());
		return mongoOperations.insert(position);
	}
	public Postion updatePosition(Postion position) {
		return mongoOperations.findAndReplace(new Query(Criteria.where("id").is(position.getId())), position);
	}
	private short nextPositionId() {
		Query q = new Query().limit(1).with(Sort.by(Sort.Direction.DESC, "id"));
		Postion p = mongoOperations.findOne(q, Postion.class);
		if(p == null)
			return (short)1;
		return (short) (p.getId()+1);
		
	}
	*/

}
