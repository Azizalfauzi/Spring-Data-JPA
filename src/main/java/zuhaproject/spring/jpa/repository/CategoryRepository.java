package zuhaproject.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zuhaproject.spring.jpa.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
