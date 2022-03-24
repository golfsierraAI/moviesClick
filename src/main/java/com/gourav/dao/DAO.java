package com.gourav.dao;

import com.gourav.entities.MovieDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class DAO {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void saveData(MovieDetails movie){
       entityManager.persist(movie);
       entityManager.flush();
    }
    @Transactional
    public List<MovieDetails> findMovie(String name) {
        System.out.println(name);
        try {
            return entityManager.createQuery("from MovieDetails where movieName like :name", MovieDetails.class).setParameter("name",name+"%").getResultList();
        } catch (Exception e){
            return null;
        }
    }

    @Transactional
    public List<MovieDetails> getData(Integer page){
        return entityManager.createQuery("from MovieDetails", MovieDetails.class).setFirstResult((page-1)*15).setMaxResults(15).getResultList();
    }
}
