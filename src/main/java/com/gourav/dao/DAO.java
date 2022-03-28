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
        try {
            return entityManager.createQuery("from MovieDetails where movieName like :name", MovieDetails.class).setParameter("name","%"+name+"%").getResultList();
        } catch (Exception e){
            return null;
        }
    }

    @Transactional
    public MovieDetails findMovieScraper(String name) {
        try {
            return entityManager.createQuery("from MovieDetails where movieName= :name", MovieDetails.class).setParameter("name", name).getSingleResult();
        } catch (Exception e){
            return null;
        }
    }

    @Transactional
    public List<MovieDetails> getData(Integer page){
        return entityManager.createQuery("from MovieDetails order by id desc", MovieDetails.class).setFirstResult((page-1)*16).setMaxResults(16).getResultList();
    }
}
