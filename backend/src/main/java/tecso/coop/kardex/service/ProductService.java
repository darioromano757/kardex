package tecso.coop.kardex.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tecso.coop.kardex.domain.Product;
import tecso.coop.kardex.exception.LimitStockException;
import tecso.coop.kardex.exception.ProductNotFoundException;
import tecso.coop.kardex.repository.ProductRepository;
import tecso.coop.kardex.service.dto.ProductDTO;

@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);

        log.debug("Created Information for Product: {}", product);

        return product;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setCode(product.getCode());
            productDTO.setDescription(product.getDescription());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setPrice(product.getPrice());
            return productDTO;
        }).collect(Collectors.toList());
    }
    
    public Product removeStock(Long productId, Integer quantity) {
    	Product product = productRepository.findById(productId).orElseThrow(
    			() -> new ProductNotFoundException());
    	if (product.getQuantity() <= 0) {
    		throw new LimitStockException();
    	}
		product.setQuantity(product.getQuantity() - quantity);
    	return product;
    }
    
	public Product addStock(Long productId, Integer quantity) {
		
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ProductNotFoundException());
		product.setQuantity(product.getQuantity() + quantity);
		
		productRepository.save(product);
		return product;
	}
	
	public void update(ProductDTO productDTO) {		
		Product product = productRepository.findById(productDTO.getId()).orElseThrow(
				() -> new ProductNotFoundException());
		product.setQuantity(productDTO.getQuantity());
		productRepository.save(product);
	}
    
}
