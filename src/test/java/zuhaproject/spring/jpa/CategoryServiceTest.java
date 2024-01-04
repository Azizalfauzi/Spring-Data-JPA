package zuhaproject.spring.jpa;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zuhaproject.spring.jpa.entity.Category;
import zuhaproject.spring.jpa.repository.CategoryRepository;
import zuhaproject.spring.jpa.service.CategoryService;

import java.util.List;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void succes() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.create();
        });
    }

    @Test
    void failed() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.test();
        });
    }

    @Test
    void programmatic() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.createCategories();
        });
    }

    @Test
    void queryMethod() {
        Category category = categoryRepository.findFirstByNameEquals("GADGET Murah").orElse(null);
        Assertions.assertNotNull(category);
        Assertions.assertEquals("GADGET Murah", category.getName());

        List<Category> categories = categoryRepository.findAllByNameLike("%GADGET%");
        Assertions.assertEquals(2, categories.size());
        Assertions.assertEquals("GADGET Murah", categories.get(0).getName());

    }
}
