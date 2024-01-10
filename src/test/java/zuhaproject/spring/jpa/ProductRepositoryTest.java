package zuhaproject.spring.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.support.TransactionOperations;
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

    @Autowired
    private TransactionOperations transactionOperations;


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
        List<Product> products = productRepository.findAllByCategory_Name("GADGET Murah", sort);
        Assertions.assertEquals(2, products.size());
        Assertions.assertEquals("APPLE IPHONE 13 Pro-Max", products.get(0).getName());
        Assertions.assertEquals("APPLE IPHONE 14 Pro-Max", products.get(1).getName());
    }

    @Test
    void pageable() {
//        Page 0
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")));
        Page<Product> products = productRepository.findAllByCategory_Name("Gadget Murah", pageable);
        Assertions.assertEquals(1, products.getContent().size());
        Assertions.assertEquals(0, products.getNumber());
        Assertions.assertEquals(2, products.getTotalElements());
        Assertions.assertEquals(2, products.getTotalPages());
        Assertions.assertEquals("APPLE IPHONE 13 Pro-Max", products.getContent().get(0).getName());
//      Page 1
        pageable = PageRequest.of(1, 1, Sort.by(Sort.Order.desc("id")));
        products = productRepository.findAllByCategory_Name("Gadget Murah", pageable);
        Assertions.assertEquals(1, products.getContent().size());
        Assertions.assertEquals(1, products.getNumber());
        Assertions.assertEquals(2, products.getTotalElements());
        Assertions.assertEquals(2, products.getTotalPages());
        Assertions.assertEquals("APPLE IPHONE 14 Pro-Max", products.getContent().get(0).getName());
    }

    @Test
    void count() {
        Long count = productRepository.count();
        Assertions.assertEquals(2L, count);

        count = productRepository.countByCategory_Name("Gadget Murah");
        Assertions.assertEquals(2L, count);

        count = productRepository.countByCategory_Name("Gadget Ga ada");
        Assertions.assertEquals(0L, count);
    }

    @Test
    void exists() {
        boolean product = productRepository.existsByName("APPLE IPHONE 14 Pro-Max");
        Assertions.assertTrue(product);

        product = productRepository.existsByName("APPLE IPHONE 14 Pro");
        Assertions.assertFalse(product);
    }

    @Test
    void deleteOld() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Category category = categoryRepository.findById(1L).orElse(null);
            Assertions.assertNotNull(category);

            Product product = new Product();
            product.setName("Samsung Galaxy S9");
            product.setPrice(10_000_000L);
            product.setCategory(category);
            productRepository.save(product);

            int delete = productRepository.deleteByName("Samsung Galaxy S9");
            Assertions.assertEquals(1, delete);

            delete = productRepository.deleteByName("Samsung Galaxy S9");
            Assertions.assertEquals(0, delete);

        });
    }

    @Test
    void deleteNew() {
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        Product product = new Product();
        product.setName("Samsung Galaxy S9");
        product.setPrice(10_000_000L);
        product.setCategory(category);
        productRepository.save(product); // Transaksi 1

        int delete = productRepository.deleteByName("Samsung Galaxy S9");// Transaksi 1
        Assertions.assertEquals(1, delete);

        delete = productRepository.deleteByName("Samsung Galaxy S9");// Transaksi 1
        Assertions.assertEquals(0, delete);
    }

    @Test
    void searchProductByName() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Product> products = productRepository.searchProductUsingName("APPLE IPHONE 14 Pro-Max", pageable);
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals("APPLE IPHONE 14 Pro-Max", products.get(0).getName());
    }

    @Test
    void searchProduct() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")));
        Page<Product> products = productRepository.searchProduct("%IPHONE%", pageable);
        Assertions.assertEquals(1, products.getContent().size());
        Assertions.assertEquals(0, products.getNumber());
        Assertions.assertEquals(2, products.getTotalPages());
        Assertions.assertEquals(2, products.getTotalElements());

        products = productRepository.searchProduct("%Gadget%", pageable);
        Assertions.assertEquals(1, products.getContent().size());
        Assertions.assertEquals(0, products.getNumber());
        Assertions.assertEquals(2, products.getTotalPages());
        Assertions.assertEquals(2, products.getTotalElements());
    }
}
