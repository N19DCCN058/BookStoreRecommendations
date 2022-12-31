package ptithcm.controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.*;
import ptithcm.bean.Cluster;
import ptithcm.bean.Record;

@Controller
@Transactional
@RequestMapping("/product")
public class Product_Cus {
	@Autowired
	SessionFactory factory;
	
	List<Record> data = new ArrayList<Record>();
	List<Cluster> clusters = new ArrayList<Cluster>();
	Map<Cluster, List<Record>> clusterRecords = new HashMap<Cluster, List<Record>>();
	
	@RequestMapping(value = "{batchName}", method = RequestMethod.GET)
	public String get_Batch(ModelMap model, @PathVariable("batchName") String batchName, HttpServletRequest request) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Batch batch = getBatch(batchName);
		Batch batchRc = null;
		if(batch!=null) {
			HttpSession ss = request.getSession();
			model.addAttribute("role",(String) ss.getAttribute("role"));
			model.addAttribute("batch", batch);
			
			this.initiateClusterAndCentroid(2);
			for (Map.Entry<Cluster, List<Record>> entry : clusterRecords.entrySet())  {
				for(Record r:entry.getValue()) {
					if(r.getId() == batch.getBatchId())
					{
						batchRc = Recommendation(r,entry.getValue());
						model.addAttribute("batch_recommendation", batchRc);
					}
				}
					
			}
			batch = (Batch) session.get(Batch.class, batchRc.getBatchId());
			batch.setScore(batch.getScore() + 5);
			try {
				session.update(batch);
				transaction.commit();
				model.addAttribute("status_update_batch", 1);
			}catch (Exception e) {
				transaction.rollback();
				// TODO: handle exception
				model.addAttribute("status_update_batch", 0);
			}finally {
				session.close();
			}
			return "product";
		}
		return "redirect:/trangchu.htm";
	}
	
	@SuppressWarnings("unchecked")
	private Batch getBatch(String batchName) {
		List<Batch> listBatch = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch");
		listBatch = query.list();
		return findByBatchName(listBatch, batchName);
	}
	
	private Batch findByBatchName(List<Batch> list, String batchName) {
		for(Batch item: list) {
			if(item.batchNameToURL().equals(batchName)) return item;
		}
		return null;
	}
	
	private Batch findByBatchId(int Id) {
		
		List<Batch> listBatch = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch where BatchId =:Id");
		query.setParameter("Id", Id);
		listBatch = query.list();
		if(listBatch.size()>0) {
			return listBatch.get(0);
		}
		else {
			return null;
		}
			
	}
	public double cosineSimilarity(Record r, Record rc) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    dotProduct = r.getCategory()*rc.getCategory() + r.getDiscount()*rc.getDiscount() + r.getPrice()*rc.getPrice() + r.getScore()*rc.getScore();
	    normA = Math.pow(r.getCategory(), 2) + Math.pow(r.getDiscount(), 2) + Math.pow(r.getPrice(), 2) + Math.pow(r.getScore(), 2);
	    normB = Math.pow(rc.getCategory(), 2) + Math.pow(rc.getDiscount(), 2) + Math.pow(rc.getPrice(), 2) + Math.pow(rc.getScore(), 2);  
	    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	private Batch Recommendation(Record r,List<Record> list) {
		int Id = 0;
		double min = 100000000;
		double d = 0;
		for(Record rc:list) {
			if(r.getId() == rc.getId()) {
			}
			else {
				d = cosineSimilarity(r,rc);
				if(d < min) {
					min = d;
					Id = rc.getId();
				}
			}
				
		}
		return findByBatchId(Id);
	}
	private void genereateRecord() {
		List<Batch> listBatch = null;
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("FROM Batch");
		listBatch = query.list();
		for(Batch r:listBatch)
		{
			data.add(r.toRecord());
		}
	}
	public void initializeCluster(int clusterNumber, Record record) {
		
		Cluster cluster = new Cluster(record.getCategory(),record.getPrice(),record.getScore(),record.getDiscount(),clusterNumber);
		clusters.add(cluster);
		List<Record> clusterRecord = new ArrayList<Record>();
		clusterRecord.add(record);
		clusterRecords.put(cluster, clusterRecord);

	}
	public void initiateClusterAndCentroid(int clusterNumber) {
		genereateRecord();
		int counter = 1;
		Iterator<Record> iterator = data.iterator();
		Record record = null;
		
		while(iterator.hasNext()) {
			record = iterator.next();
			if(counter <= clusterNumber) {
				record.setClusterNumber(counter);
				initializeCluster(counter, record);
				counter++;
			}else {
                double minDistance = Integer.MAX_VALUE;
                Cluster whichCluster = null;
                
                for(Cluster cluster : clusters) {
                	double distance = cluster.calculateDistance(record);
                	if(minDistance > distance) {
                		minDistance = distance;
                		whichCluster = cluster;
                	}
                }
                
                record.setClusterNumber(whichCluster.getClusterNumber());
				whichCluster.updateCentroid(record);
				clusterRecords.get(whichCluster).add(record);

			}
		}
	}
	
}
