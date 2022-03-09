package Domain;

import java.util.ArrayList;
import java.util.List;

public class GarageHolder {
    private List<CarOrder> carOrders = new ArrayList<>();
    //TODO misschien een set gebruiken hier? is orde en duplicates belangrijk?
    public List<CarOrder> getCarOrders(){
        return List.copyOf(carOrders);
    }
}
