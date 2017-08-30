package excel;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/data")
public class DataService {
	
	EntityManager em;
	
	public DataService() {
		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-data");
		em = emf.createEntityManager();
	}
	
	@GET
	@Path("/total")
	@Produces("text/plain")
	public Number getTotalItems(@QueryParam("author") String author, @QueryParam("title") String title, @QueryParam("primary_community") String primary_community) {
		String q = "SELECT COUNT(c) FROM DataEntity c";
		if((author != null) || (title != null) || (primary_community != null)) {
			q = q + " WHERE";
			if(author != null) {
				q = q + " LOWER(c.author) like " + "LOWER('%" + author + "%')";
			}
			if(title != null) {
				q = q + " LOWER(c.title) like " + "LOWER('%" + title + "%')";
			}
			if(primary_community != null) {
				q = q + " LOWER(c.primary_community) like " + "LOWER('%" + primary_community + "%')";
			}
		}
		Query query = em.createQuery(q);
		return (Number)query.getSingleResult();
	}
	
	@GET
	@Path("/page")
	@Produces("application/json")
	public List<DataEntity> getPageData(@DefaultValue("1") @QueryParam("pageNumber") int pageNumber, @DefaultValue("25") @QueryParam("pageSize") int pageSize, @QueryParam("sortParams") List<SortObj> sortObjList, @QueryParam("author") String author, @QueryParam("title") String title, @QueryParam("primary_community") String primary_community) {
		Collections.sort(sortObjList);
		String q = "SELECT c FROM DataEntity c";
		if((author != null) || (title != null) || (primary_community != null)) {
			q = q + " WHERE";
			if(author != null) {
				q = q + " LOWER(c.author) like " + "LOWER('%" + author + "%')";
			}
			if(title != null) {
				q = q + " LOWER(c.title) like " + "LOWER('%" + title + "%')";
			}
			if(primary_community != null) {
				q = q + " LOWER(c.primary_community) like " + "LOWER('%" + primary_community + "%')";
			}
		}
		if(sortObjList.size() != 0) {
			q = q + " ORDER BY";
			for(SortObj sortObj: sortObjList) {
				q = q + " c." + sortObj.column + " " + sortObj.direction + ",";
			}
			q = q.substring(0, q.length()-1);
		}
		Query query = em.createQuery(q);
		query.setFirstResult((pageNumber-1)*pageSize);
		query.setMaxResults(pageSize);
		List<DataEntity> data = query.getResultList();
		return data;
	}
}
