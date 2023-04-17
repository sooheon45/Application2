package application.infra;

import application.config.kafka.KafkaProcessor;
import application.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PolicyHandler {

    @Autowired
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(
        @Payload String eventString,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        /*
          // Call port method with received messageKey to publish msg to the same partition. //
          DomainClass.portMethod(eventString, new String(messageKey));
          
          // ,or //
          new EventRaised(domain Obj).publishAfterCommit(new String(messageKey));
          // manual Offset Commit. //
          acknowledgment.acknowledge();  
          */
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderPlaced'"
    )
    public void wheneverOrderPlaced_OrderSaga(
        @Payload OrderPlaced orderPlaced,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        OrderPlaced event = orderPlaced;
        System.out.println(
            "\n\n##### listener OrderSaga : " + orderPlaced + "\n\n"
        );

        StartDeliveryCommand startDeliveryCommand = new StartDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of OrderPlaced event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.startDelivery(startDeliveryCommand);
            });
        DecreaseStockCommand decreaseStockCommand = new DecreaseStockCommand();
        // implement:  Map command properties from event

        productRepository
            .findById(
                // implement: Set the Product Id from one of OrderPlaced event's corresponding property

            )
            .ifPresent(product -> {
                product.decreaseStock(decreaseStockCommand);
            });
        CancelDeliveryCommand cancelDeliveryCommand = new CancelDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of OrderPlaced event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.cancelDelivery(cancelDeliveryCommand);
            });
        UpdateStatusCommand updateStatusCommand = new UpdateStatusCommand();
        // implement:  Map command properties from event

        orderRepository
            .findById(
                // implement: Set the Order Id from one of OrderPlaced event's corresponding property

            )
            .ifPresent(order -> {
                order.updateStatus(updateStatusCommand);
            });

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeliveryStarted'"
    )
    public void wheneverDeliveryStarted_OrderSaga(
        @Payload DeliveryStarted deliveryStarted,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        DeliveryStarted event = deliveryStarted;
        System.out.println(
            "\n\n##### listener OrderSaga : " + deliveryStarted + "\n\n"
        );

        StartDeliveryCommand startDeliveryCommand = new StartDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of DeliveryStarted event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.startDelivery(startDeliveryCommand);
            });
        DecreaseStockCommand decreaseStockCommand = new DecreaseStockCommand();
        // implement:  Map command properties from event

        productRepository
            .findById(
                // implement: Set the Product Id from one of DeliveryStarted event's corresponding property

            )
            .ifPresent(product -> {
                product.decreaseStock(decreaseStockCommand);
            });
        CancelDeliveryCommand cancelDeliveryCommand = new CancelDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of DeliveryStarted event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.cancelDelivery(cancelDeliveryCommand);
            });
        UpdateStatusCommand updateStatusCommand = new UpdateStatusCommand();
        // implement:  Map command properties from event

        orderRepository
            .findById(
                // implement: Set the Order Id from one of DeliveryStarted event's corresponding property

            )
            .ifPresent(order -> {
                order.updateStatus(updateStatusCommand);
            });

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='StockDecreased'"
    )
    public void wheneverStockDecreased_OrderSaga(
        @Payload StockDecreased stockDecreased,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        StockDecreased event = stockDecreased;
        System.out.println(
            "\n\n##### listener OrderSaga : " + stockDecreased + "\n\n"
        );

        StartDeliveryCommand startDeliveryCommand = new StartDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of StockDecreased event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.startDelivery(startDeliveryCommand);
            });
        DecreaseStockCommand decreaseStockCommand = new DecreaseStockCommand();
        // implement:  Map command properties from event

        productRepository
            .findById(
                // implement: Set the Product Id from one of StockDecreased event's corresponding property

            )
            .ifPresent(product -> {
                product.decreaseStock(decreaseStockCommand);
            });
        CancelDeliveryCommand cancelDeliveryCommand = new CancelDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of StockDecreased event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.cancelDelivery(cancelDeliveryCommand);
            });
        UpdateStatusCommand updateStatusCommand = new UpdateStatusCommand();
        // implement:  Map command properties from event

        orderRepository
            .findById(
                // implement: Set the Order Id from one of StockDecreased event's corresponding property

            )
            .ifPresent(order -> {
                order.updateStatus(updateStatusCommand);
            });

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCompleted'"
    )
    public void wheneverOrderCompleted_OrderSaga(
        @Payload OrderCompleted orderCompleted,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        OrderCompleted event = orderCompleted;
        System.out.println(
            "\n\n##### listener OrderSaga : " + orderCompleted + "\n\n"
        );

        StartDeliveryCommand startDeliveryCommand = new StartDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of OrderCompleted event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.startDelivery(startDeliveryCommand);
            });
        DecreaseStockCommand decreaseStockCommand = new DecreaseStockCommand();
        // implement:  Map command properties from event

        productRepository
            .findById(
                // implement: Set the Product Id from one of OrderCompleted event's corresponding property

            )
            .ifPresent(product -> {
                product.decreaseStock(decreaseStockCommand);
            });
        CancelDeliveryCommand cancelDeliveryCommand = new CancelDeliveryCommand();
        // implement:  Map command properties from event

        deliveryRepository
            .findById(
                // implement: Set the Delivery Id from one of OrderCompleted event's corresponding property

            )
            .ifPresent(delivery -> {
                delivery.cancelDelivery(cancelDeliveryCommand);
            });
        UpdateStatusCommand updateStatusCommand = new UpdateStatusCommand();
        // implement:  Map command properties from event

        orderRepository
            .findById(
                // implement: Set the Order Id from one of OrderCompleted event's corresponding property

            )
            .ifPresent(order -> {
                order.updateStatus(updateStatusCommand);
            });

        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }
}
