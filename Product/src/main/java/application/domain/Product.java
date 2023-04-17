package application.domain;

import application.ProductApplication;
import application.domain.StockDecreased;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.context.ApplicationContext;

@Entity
@Table(name = "Product_table")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PostPersist
    public void onPostPersist() {
        StockDecreased stockDecreased = new StockDecreased(this);
        stockDecreased.publishAfterCommit();
    }

    public static ProductRepository repository() {
        ProductRepository productRepository = applicationContext()
            .getBean(ProductRepository.class);
        return productRepository;
    }

    public static ApplicationContext applicationContext() {
        return ProductApplication.applicationContext;
    }

    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // implement the business logics here:

    }
}
