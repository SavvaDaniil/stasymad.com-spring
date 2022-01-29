package com.nastyabagdasarova.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Component.ParseTimeStampComponent;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Model.Accessofuser;
import com.nastyabagdasarova.Model.Accessofuseractive;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.Videoplay;
import com.nastyabagdasarova.Repo.AccessofuserRepository;

@Service
public class AccessofuserService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");
	
	@Autowired 
	private AccessofuserRepository accessofuserRepository;
	

    @Autowired
    private IAuthenticationFacade authenticationFacade;
	@Autowired
	private UserService userService;
	@Autowired
	private VideoplayService videoplayService;
	
	
	public List<Accessofuser> listAll(){
		return (List<Accessofuser>) accessofuserRepository.findAll();
	}
	
	public Accessofuser save(Accessofuser accessofuser) {
		return accessofuserRepository.save(accessofuser);
	}
	
	public Accessofuser findActiveByIdOfContent(int id_of_user, int id_of_course, int is_back) {

		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		String timestampYMD = LocalDateTime.now(ZoneId.of("UTC+06:00"))
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		int tsYMD = (int)(ptsc.parseDatestamp(timestampYMD).getTime() / 1000L);
		
		
		entityManager = entityManagerFactory.createEntityManager();
		Query<Accessofuser> query = (Query<Accessofuser>) entityManager.createQuery("SELECT p FROM Accessofuser p "
			+ " WHERE p.id_of_course = :id_of_course AND "
			+ "(p.date_must_be_used = 0 or p.date_must_be_used > :date_must_be_used or p.date_must_be_used = : date_must_be_used "
			+ "or p.date_must_be_used IS NULL)"
	   		+ " AND p.id_of_user = :id_of_user AND p.is_back = :is_back", Accessofuser.class);
		
		query.setParameter("id_of_course", id_of_course);
		query.setParameter("id_of_user", id_of_user);
		query.setParameter("date_must_be_used", tsYMD);
		query.setParameter("is_back", is_back);
		List<Accessofuser> resultList = (List<Accessofuser>) query.getResultList();

		entityManager = null;
		query = null;
		ptsc = null;
		timestampYMD = null;
		System.gc();
		
		if(resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Accessofuseractive> getActiveAccessOfUser(int id_of_user, int id_of_type){

		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		String timestampYMD = LocalDateTime.now(ZoneId.of("UTC+06:00"))
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		int tsYMD = (int)(ptsc.parseDatestamp(timestampYMD).getTime() / 1000L);
		
		
		entityManager = entityManagerFactory.createEntityManager();
		Query<Object> query = (Query<Object>) entityManager.createQuery("SELECT p as Accessofuser, a.name, a.description FROM Accessofuser p "+
				" INNER JOIN Course a ON a.id = p.id_of_course"
				+ " INNER JOIN ConnectionCourseToType c ON c.id_of_course = a.id AND c.id_of_type = :id_of_type "
	   		+ " WHERE p.id_of_user = :id_of_user AND "
	   		+ "(p.date_must_be_used = 0 or p.date_must_be_used > :date_must_be_used or p.date_must_be_used = :date_must_be_used ) "
	   		+ "AND p.is_back = 0 GROUP BY p.id_of_course");
		query.setParameter("id_of_type", id_of_type);
	   query.setParameter("id_of_user", id_of_user);
	   query.setParameter("date_must_be_used", tsYMD);
	   
		
	   List<Object> resultList = (List<Object>) query.getResultList();

	   	
	   ArrayList<Accessofuseractive> activeAccessList = new ArrayList<Accessofuseractive>();
	   Accessofuseractive iterator = new Accessofuseractive();
		for (int i = 0; i < resultList.size(); i++) {
		   Object[] o = (Object[]) resultList.get(i);
		   Accessofuser a = (Accessofuser)o[0];
		   iterator = new Accessofuseractive(a.getId(), a.getId_of_payment(), a.getId_of_user(), a.getStatus(), a.getDate_of_add(), a.getDate_of_activation(),
		   		a.getDate_must_be_used(), a.getId_of_course(), a.getIs_back(), a.getDays(), a.getKind(),
		   		a.getBack(), a.getOperation());
		   
		   if(o[1].toString() != null) iterator.setName_of_course(o[1].toString());
		   if(o[2].toString() != null)iterator.setDescription_of_course(o[2].toString());
		  
		   activeAccessList.add(iterator);
		   
		 }
		
		/////////////////////////////////////////////////////////////

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			return null;
		}
		
		User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
		
		Accessofuseractive backActiveAccess = null;
		
		Videoplay videoplay;
		File posterSrc = null;
		int i = 0;
		for(Accessofuseractive access : activeAccessList) {
			
			//tsDateOfAdd = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(access.getDate_of_activation());
			//access.setDate_must_be_used_to_print(tsDateOfAdd);
			if(access.getDate_of_add() != null) {
				access.setDate_of_add_to_print(new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(access.getDate_of_add()));
			}
			
			
			if(access.getDate_must_be_used() != 0
					&& (access.getDate_must_be_used() - 10 * 86400) < (int)(System.currentTimeMillis() / 1000L)) {
				access.setProlong(true);
				//System.out.println("Можно продлить\n");
			} else {
				access.setProlong(false);
			}

			if(access.getDate_must_be_used() != 0) {
				access.setDate_must_be_used_to_print(new SimpleDateFormat("dd.MM.YYYY")
						.format(new Date((long)(access.getDate_must_be_used() * 1000L))));
			}
			if(access.getDate_of_activation() != 0) {
				access.setDate_of_activation_to_print(new SimpleDateFormat("dd.MM.YYYY")
						.format(new Date((long)(access.getDate_of_activation() * 1000L))));
			}


			if(access.getId_of_course() != 0) {
				backActiveAccess = findBackActiveByIdOfProduct(access.getId_of_course(), "acrobatics");
				access.setBackActiveAccess(backActiveAccess);
			}

			videoplay = videoplayService.findByIdOfContent(access.getId_of_course(), user.getId());
			if(videoplay == null) {
				access.setLast_played_number(1);
			} else {
				access.setLast_played_number(videoplay.getNumber());
			}

			posterSrc = new File("public/course/" + access.getId_of_course() + "/poster.jpg");
			if(posterSrc.exists()) {
				access.setPosterSrc("public/course/" + access.getId_of_course() + "/poster.jpg");
			} else {
				access.setPosterSrc("/images/noPoster.jpg");
			}
			
			activeAccessList.set(i, access);
			i++;
		}
		
		

		
		
		
		
		
		
		
		
		
		/////////////////////////////////////////////////////////////
		query = null;
	   	ptsc = null;
		timestampYMD = null;
		resultList = null;
		iterator = null;
		System.gc();
		
		
		return activeAccessList;
	}

	
	public Accessofuseractive findBackActiveByIdOfProduct(int id_of_course, String product) {


		entityManager = entityManagerFactory.createEntityManager();
		Query<Accessofuser> query = (Query<Accessofuser>) entityManager.createQuery("SELECT p as Accessofuser FROM Accessofuser p "
			+ " WHERE p.id_of_course = :id_of_course AND "
	   		+ "(p.date_must_be_used = 0 OR p.date_must_be_used IS NULL) AND p.is_back = 1 GROUP BY p.id_of_course", Accessofuser.class);
	   query.setParameter("id_of_course", id_of_course);
	   List<Accessofuser> resultList = (List<Accessofuser>) query.getResultList();

	   
	   ArrayList<Accessofuseractive> activeAccessList = new ArrayList<Accessofuseractive>();
	   Accessofuseractive iterator = new Accessofuseractive();
		for (int i = 0; i < resultList.size(); i++) {
		   Accessofuser a = resultList.get(i);
		   iterator = new Accessofuseractive(a.getId(), a.getId_of_payment(), a.getId_of_user(), a.getStatus(), a.getDate_of_add(), a.getDate_of_activation(),
		   		a.getDate_must_be_used(), a.getId_of_course(), a.getIs_back(), a.getDays(), a.getKind(),
		   		a.getBack(), a.getOperation());

		   activeAccessList.add(iterator);
		 }
		
		
		query = null;
		resultList = null;
		iterator = null;
		System.gc();
		
		if(activeAccessList.isEmpty()) {
			return null;
		} else {
			return activeAccessList.get(0);
		}
	   
	}
}
