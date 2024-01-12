package zuhaproject.spring.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zuhaproject.spring.jpa.entity.Category;
import zuhaproject.spring.jpa.repository.CategoryRepository;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void insertCategory() {
        Category category = new Category();
        category.setName("GADGET");

        categoryRepository.save(category);
        Assertions.assertNotNull(category.getId());
    }

    @Test
    void updateCategory() {
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        category.setName("GADGET Murah");
        categoryRepository.save(category);

//        categoryRepository.delete(category)

        category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);
        Assertions.assertEquals("GADGET Murah", category.getName());
    }

    @Test
    void audit() {
        Category category = new Category();
        category.setName("Sample audit");
        categoryRepository.save(category);

        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getCreatedDate());
        Assertions.assertNotNull(category.getLastModifiedDate());
    }
}
