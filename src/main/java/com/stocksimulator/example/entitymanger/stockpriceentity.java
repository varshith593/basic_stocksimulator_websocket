package com.stocksimulator.example.entitymanger;

import com.stocksimulator.example.entity.stockprice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class stockpriceentity implements stockpriceinterface
{
    @PersistenceContext
    private EntityManager em;
    @Override
    public void insert_price(stockprice sp) {
        // The Engine saves the object and its Foreign Key
        em.persist(sp);
    }

    @Override
    public long get_price(long id) {
        // Find by Primary Key (id is long)
        stockprice sp = em.find(stockprice.class, id);
        return (sp != null) ? sp.getStockprice() : 0;
    }



    @Override
    public List<stockprice> get_N_prices(String symbol, int n) {
        // Querying through the relationship: sp.stock.symbol
        return em.createQuery(
                        "SELECT sp FROM stockprice sp WHERE sp.stock.symbol = :sym ORDER BY sp.id DESC",
                        stockprice.class)
                .setParameter("sym", symbol)
                .setMaxResults(n) // Example: Get last 10
                .getResultList();
    }

    @Override
    public List<stockprice> get_time_prices(String symbol) {
        // Using the 'modified' timestamp you created earlier
        return em.createQuery(
                        "SELECT sp FROM stockprice sp WHERE sp.stock.symbol = :sym ORDER BY sp.modified DESC",
                        stockprice.class)
                .setParameter("sym", symbol)
                .getResultList();
    }

    @Override
    public long get_avg_prices(String symbol) {
        // Using the Aggregate function AVG
        Double avg = em.createQuery(
                        "SELECT AVG(sp.stockprice) FROM stockprice sp WHERE sp.stock.symbol = :sym",
                        Double.class)
                .setParameter("sym", symbol)
                .getSingleResult();

        return (avg != null) ? avg.longValue() : 0;
    }
    @Override
    @Transactional
    public void delete_history_keep_last_60(String symbol) {
        // 1. Find the 'modified' timestamp of the 60th newest record
        List<LocalDateTime> timestamps = em.createQuery(
                        "SELECT sp.modified FROM stockprice sp WHERE sp.stock.symbol = :sym ORDER BY sp.modified DESC",
                        LocalDateTime.class)
                .setParameter("sym", symbol)
                .setFirstResult(59) // Skip the first 59 newest
                .setMaxResults(1)   // Get the 60th timestamp
                .getResultList();

        if (!timestamps.isEmpty()) {
            LocalDateTime cutoffTime = timestamps.get(0);

            // 2. The Window: Delete any price modified BEFORE this cutoff time
            // The Engine runs a bulk delete on the old "window" of time
            em.createQuery("DELETE FROM stockprice sp WHERE sp.stock.symbol = :sym AND sp.modified < :cutoff")
                    .setParameter("sym", symbol)
                    .setParameter("cutoff", cutoffTime)
                    .executeUpdate();
        }
    }

    @Override
    public long maxprice(String symbol) {
        return 0;
    }

    @Override
    public long minprice(String symbol) {
        return 0;
    }


}




