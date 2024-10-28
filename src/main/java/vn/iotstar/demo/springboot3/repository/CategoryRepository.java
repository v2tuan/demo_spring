package vn.iotstar.demo.springboot3.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.demo.springboot3.entity.Category;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
        Optional<Category> findCategoriesByCategoryname(String categoryname);
        Page<Category> findByCategorynameContaining(String keyword, Pageable page);
}
