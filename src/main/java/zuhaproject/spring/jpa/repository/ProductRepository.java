package zuhaproject.spring.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zuhaproject.spring.jpa.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Name(String name);

    List<Product> findAllByCategory_Name(String name, Sort sort);

    Page<Product> findAllByCategory_Name(String name, Pageable pageable);

    Long countByCategory_Name(String name);

    boolean existsByName(String name);

    @Transactional
    int deleteByName(String name);

    List<Product> searchProductUsingName(@Param("name") String name, Pageable pageable);

    @Query(value = "select p from Product p where p.name like :name or p.category.name like :name"
            , countQuery = "select count(p) from Product p where p.name like :name or p.category.name like :name ")
    Page<Product> searchProduct(@Param("name") String name, Pageable pageable);

    @Modifying
    @Query("delete from Product p where p.name = :name")
    int deleteProductUsingName(@Param("name") String name);

    @Modifying
    @Query("update Product p set p.price = 0 where p.id = :id")
    int updateProductUsingName(@Param("id") Long id);
}
