package zuhaproject.spring.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionOperations;
import zuhaproject.spring.jpa.entity.Category;
import zuhaproject.spring.jpa.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    public void error() {
        throw new RuntimeException("Ups");
    }

    public void createCategories() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            for (int i = 1; i < 5; i++) {
                Category category = new Category();
                category.setName("Category" + i);
                categoryRepository.save(category);
            }
            error();
        });
    }

    @Transactional
    public void create() {
        for (int i = 1; i < 5; i++) {
            Category category = new Category();
            category.setName("Category" + i);
            categoryRepository.save(category);
        }
        throw new RuntimeException("Ups rollback please");
    }

    public void test() {
        create();
    }
}
