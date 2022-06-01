package br.com.fiap.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.fiap.model.Visitante;

public class VisitanteDao {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("progamer-persistence-unit");
	private EntityManager manager = factory.createEntityManager();

	public void create(Visitante visitante) {
		
		LocalDate today = LocalDate.now();
		System.out.print(today);
		
		visitante.setDataCadastro(today);
		System.out.print(visitante.toString());
		
		
		manager.getTransaction().begin();
		manager.persist(visitante);
		manager.getTransaction().commit();
		
		manager.clear();
	}
	
	public List<Visitante> listAll(){
		TypedQuery<Visitante> query = 
				manager.createQuery("SELECT v FROM Visitante v", Visitante.class);
		
		return query.getResultList();
	}

	public void delete(Visitante visitante) {
		manager.getTransaction().begin();
		visitante = manager.merge(visitante);
		manager.remove(visitante);
		manager.getTransaction().commit();
	}

}
