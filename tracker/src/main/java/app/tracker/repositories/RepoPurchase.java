package app.tracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.tracker.entities.Purchase;

@Repository
public interface RepoPurchase extends JpaRepository<Purchase, Long>{
    public List<Purchase> findPurchaseByUserId(Long userId);
    public Purchase findPurchaseById(Long id);
}
