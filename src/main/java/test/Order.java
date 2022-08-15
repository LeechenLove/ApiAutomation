package test;


/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/10 20:36
 **/
public class Order {
    public int id;
    public int petId;
    public int quantity;
    public String shipDate;
    public String status;
    public boolean complete;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetid(int petId){
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShipDate(){
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Order(){}

    public Order(int id, int petId, int quantity, String shipDate, String status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status =status;
        this.complete = complete;
    }
}
