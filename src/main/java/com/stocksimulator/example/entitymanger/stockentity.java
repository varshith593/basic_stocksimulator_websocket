package com.stocksimulator.example.entitymanger;

import com.stocksimulator.example.entity.stock;

import com.stocksimulator.example.entitymanger.stockinterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class stockentity implements stockinterface {



    @PersistenceContext
    private EntityManager em;


    @Transactional
    @Override
    public void createstock(stock stock) {
        em.persist(stock);
    }

    @Override
    public void updatestockprice(stock stock) {
            em.merge(stock);
    }

    @Override
    public void deletestock(String symbol) {
        // The Engine runs a direct SQL/JPQL DELETE command
        em.createQuery("DELETE FROM stock s WHERE s.symbol = :symbol")
                .setParameter("symbol", symbol)
                .executeUpdate(); // Use executeUpdate for DELETE/UPDATE queries
    }

    @Override
    public List<stock> getstocks() {
        List<stock> allstocks = em.createQuery("SELECT p FROM stock p", stock.class)
                .getResultList();

        return allstocks ;
    }

    @Override
    public stock getstockbyid(long id) {
        return em.find(stock.class, id);
    }

    @Override
    public stock getstockbyname(String name) {
        return em.createQuery("SELECT s FROM stock s WHERE s.name = :name", stock.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public stock getstockbysymbol(String symbol) {
        try {
            // We use a JPQL query to find by the 'symbol' attribute
            return em.createQuery("SELECT s FROM stock s WHERE s.symbol = :sym", stock.class)
                    .setParameter("sym", symbol)
                    .getSingleResult();
        } catch (NoResultException e) {
            // If the symbol isn't in the DB, return null instead of crashing
            return null;
        }
    }
}
