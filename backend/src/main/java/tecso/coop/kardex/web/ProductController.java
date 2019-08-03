package tecso.coop.kardex.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tecso.coop.kardex.domain.Product;
import tecso.coop.kardex.exception.LimitStockException;
import tecso.coop.kardex.service.ProductService;
import tecso.coop.kardex.service.dto.ProductDTO;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);

        Product newProduct = productService.createProduct(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + newProduct.getCode()))
                .body(newProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
   
    
    @PutMapping("/products/stock/add/{productId}") 
    public ResponseEntity<Product> addStock(@PathVariable Long productId) throws Exception {
    	Product product = productService.addStock(productId, 1);
    	 return new ResponseEntity<>(product, HttpStatus.OK);
    } 
    
    @PutMapping("/products/stock/remove/{productId}")
    public ResponseEntity<?> removeStock(@PathVariable Long productId) {
    	try {
    		Product product = productService.removeStock(productId, 1);
    		return new ResponseEntity<>(product, HttpStatus.OK);    		
    	} catch(LimitStockException e) {
    		return new ResponseEntity("No posee stock suficiente", HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PutMapping("/products")
    public ResponseEntity<Void> edit(@Valid @RequestBody ProductDTO productDTO) {
    	productService.update(productDTO);
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
