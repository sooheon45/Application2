package application.domain;

import application.domain.*;
import application.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class StockDecreased extends AbstractEvent {

    private Long id;

    public StockDecreased(Product aggregate) {
        super(aggregate);
    }

    public StockDecreased() {
        super();
    }
}