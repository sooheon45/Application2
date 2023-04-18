package application.saga;


import application.config.kafka.KafkaProcessor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import application.domain.*;
import application.external.*;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// @Component
// @Saga
// @ProcessingGroup("OrderSagaSaga")

// @Service

// @Saga
public class OrderSagaSaga {
    
    @Autowired
    DeliveryService deliveryService;
    ProductService productService;



    // 1. start
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
        
        try {
            // 2
            deliveryService.startDelivery(event.getId());
        } catch (Exception e){
            // 2'
            OrderCancelCommand orderCancelCommand = new OrderCancelCommand();
            Order order = new Order();
            order.orderCancel(orderCancelCommand);

        }
        
        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    // 3
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeliveryStarted'"
    )
    public void wheneverDeliveryStarted_DecreaseStock(
        @Payload DeliveryStarted deliveryStarted,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        DeliveryStarted event = deliveryStarted;
        System.out.println(
            "\n\n##### listener DecreaseStock : " + deliveryStarted + "\n\n"
        );

        try{
            // 4
            productService.decreaseStock(event.getId());
        }catch (Exception e){
            // 4'
            deliveryService.cancelDelivery(event.getId());
        }
       
        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

     // 5
     @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='StockDecreased'"
    )
    public void wheneverStockDecreased_UpdateStatus(
        @Payload StockDecreased stockDecreased,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        StockDecreased event = stockDecreased;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + stockDecreased + "\n\n"
        );

        // 6
        Order order =new Order();
        // order.updateStatus(stockDecreased);
        
       
        // Manual Offset Commit //
        acknowledgment.acknowledge();
    }

    // 7. end
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCompleted'"
    )
    public void wheneverOrderCompleted(
        @Payload OrderCompleted orderCompleted,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) byte[] messageKey
    ) {
        OrderCompleted event = orderCompleted;
        System.out.println(
            "\n\n##### listener OrderCompleted : " + orderCompleted + "\n\n"
        );

        //...

        acknowledgment.acknowledge();
    }
}




// @FeignClient(name = "Order", url = "${api.url.Order}")
// public class OrderSagaSaga {
//     @RequestMapping(method = RequestMethod.POST, path = "/orders/{id}")
//     public void increaseStock(
//         @PathVariable("id") Long id,
//         @RequestBody OrderPlaced orderPlaced
//     );
// }