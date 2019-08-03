package tecso.coop.kardex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tecso.coop.kardex.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
