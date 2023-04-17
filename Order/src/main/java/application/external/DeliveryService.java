package application.external;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Date;
@FeignClient(name = "Delivery", url = "${api.url.Delivery}")
public interface DeliveryService {
    @RequestMapping(method= RequestMethod.PUT, path="/deliveries/{id}/canceldelivery")
    public void cancelDelivery(@PathVariable("id")  );
    @RequestMapping(method= RequestMethod.PUT, path="/deliveries/{id}/startdelivery")
    public void startDelivery(@PathVariable("id")  );
}