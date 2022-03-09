package Controller;

import Domain.CarOrder;
import Domain.GarageHolder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class OrderController {

    private List<GarageHolder> garageHolders;

    public List<CarOrder> giveNewCarOrders(GarageHolder garageHolder){
        return List.copyOf(garageHolder.getCarOrders());
    }
}
