package com.cts.recommendations.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.recommendations.entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
	@Query("SELECT g FROM Products g where g.brand = :brand")
	public List<Products> findBybrand(@Param("brand") String brand);

	@Query("SELECT p FROM Products p join ConsumerQuery c on p.brand = c.brands and p.color=c.colors and p.segment=c.segments and p.price <= c.maxBudget where c.consumerId = ?1")
	public List<Products> findByUserPreference(String consumerId);
}