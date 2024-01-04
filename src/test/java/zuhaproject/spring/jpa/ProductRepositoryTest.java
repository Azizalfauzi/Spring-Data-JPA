package zuhaproject.spring.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import zuhaproject.spring.jpa.entity.Category;
import zuhaproject.spring.jpa.entity.Product;
import zuhaproject.spring.jpa.repository.CategoryRepository;
import zuhaproject.spring.jpa.repository.ProductRepository;

import java.util.List;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;


    @Test
    void createProduct() {
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        {
            Product product = new Product();
            product.setName("APPLE IPHONE 14 Pro-Max");
            product.setPrice(25_000_000L);
            product.setCategory(category);
            productRepository.save(product);

        }
        {
            Product product = new Product();
            product.setName("APPLE IPHONE 13 Pro-Max");
            product.setPrice(20_000_000L);
            product.setCategory(category);
            productRepository.save(product);
        }

    }

    @Test
    void findByCategoryName() {
        List<Product> products = productRepository.findAllByCategory_Name("GADGET Murah");
        Assertions.assertEquals(2, products.size());
        Assertions.assertEquals("APPLE IPHONE 14 Pro-Max", products.get(0).getName());
        Assertions.assertEquals("APPLE IPHONE 13 Pro-Max", products.get(1).getName());
    }

    @Test
    void sort() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findAllByCategory_Name("GADGET Murah",sort);
        Assertions.assertEquals(2, products.size());
        Assertions.assertEquals("APPLE IPHONE 13 Pro-Max", products.get(0).getName());
        Assertions.assertEquals("APPLE IPHONE 14 Pro-Max", products.get(1).getName());
    }
}
