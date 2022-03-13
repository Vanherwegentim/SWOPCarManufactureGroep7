package be.kuleuven.assemassit.Domain;

import java.util.ArrayList;
import java.util.List;

public class GarageHolder {
    private int id;
    private String name;

    public GarageHolder(int id, String name) {
      this.id = id;
      this.name = name;
    }

    public int getId() {
      return this.id;
    }

    public String getName() {
      return this.name;
    }

    private List<CarOrder> carOrders = new ArrayList<>();
    public List<CarOrder> getCarOrders(){
        return List.copyOf(carOrders);
    }
}
