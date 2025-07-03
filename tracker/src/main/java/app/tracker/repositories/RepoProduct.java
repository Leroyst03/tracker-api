package app.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.tracker.entities.Product;

@Repository
public interface RepoProduct extends JpaRepository<Product, Long>{
    public Product findProductByNombre(String nombre);
}
