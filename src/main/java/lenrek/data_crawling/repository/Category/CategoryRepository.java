package lenrek.data_crawling.repository.Category;

import lenrek.data_crawling.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(Category name);
}
