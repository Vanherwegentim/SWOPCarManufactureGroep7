package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderController {

    private List<GarageHolder> garageHolders;
    private GarageHolderRepository garageHolderRepository;

    public OrderController() {
      garageHolderRepository = new GarageHolderRepository();
      garageHolders = garageHolderRepository.getGarageHolders();
    }

    public Map<Integer, String> giveGarageHolders() {
      return this.garageHolders
        .stream()
        .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
    }

    public List<CarOrder> giveNewCarOrders(GarageHolder garageHolder){
        return List.copyOf(garageHolder.getCarOrders());
    }
}
