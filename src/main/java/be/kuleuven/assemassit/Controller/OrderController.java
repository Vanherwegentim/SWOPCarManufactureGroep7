package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;

import java.util.List;

public class OrderController {

    private List<GarageHolder> garageHolders;

    public List<CarOrder> giveNewCarOrders(GarageHolder garageHolder){
        return List.copyOf(garageHolder.getCarOrders());
        //yeet
    }
}
