package excel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/data")
public class DataService {
	
	@GET
	@Produces("application/json")
	public DataEntity[] getJsonData() {
		System.setProperty("db2.jcc.charsetDecoderEncoder", "3");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-data");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c from DataEntity c");
		List<DataEntity> data = query.getResultList();
		em.close();
		return data.toArray(new DataEntity[data.size()]);
	}
	
}
