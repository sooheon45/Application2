package deliveryapp.domain;

import deliveryapp.DeliveryApplication;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.context.ApplicationContext;

@Entity
@Table(name = "Delivery_table")
@Data
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    private String userId;

    private Integer qty;

    private String itemId;

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = applicationContext()
            .getBean(DeliveryRepository.class);
        return deliveryRepository;
    }

    public static ApplicationContext applicationContext() {
        return DeliveryApplication.applicationContext;
    }

    public void startDelivery(StartDeliveryCommand startDeliveryCommand) {
        // implement the business logics here:

        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }

    public void cancelDelivery(CancelDeliveryCommand cancelDeliveryCommand) {
        // implement the business logics here:

        DeliveryCanceled deliveryCanceled = new DeliveryCanceled(this);
        deliveryCanceled.publishAfterCommit();
    }

    public void returnDelivery(ReturnDeliveryCommand returnDeliveryCommand) {
        // implement the business logics here:

        DeliveryReturned deliveryReturned = new DeliveryReturned(this);
        deliveryReturned.publishAfterCommit();
    }
}
