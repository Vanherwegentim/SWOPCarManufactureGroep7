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

  // TODO: move this association to assemblyline
  private GarageHolder loggedInGarageHolder;

  public OrderController() {
    garageHolderRepository = new GarageHolderRepository();
    garageHolders = garageHolderRepository.getGarageHolders();
  }

  public String giveLoggedInGarageHolderName() {
    return loggedInGarageHolder.getName();
  }

  public void setLoggedInGarageHolder(int garageHolderId) {
    this.loggedInGarageHolder = garageHolders.get(garageHolderId);
  }

  public void logOffGarageHolder() {
    this.loggedInGarageHolder = null;
  }

  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolders
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
  }

  // TODO: this method needs to be rewritten
  public List<CarOrder> giveNewCarOrders(GarageHolder garageHolder){
      return List.copyOf(garageHolder.getCarOrders());
  }
}
