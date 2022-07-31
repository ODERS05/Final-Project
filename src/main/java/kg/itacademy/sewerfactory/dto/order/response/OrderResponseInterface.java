package kg.itacademy.sewerfactory.dto.order.response;


public interface OrderResponseInterface {
    Long getId();

    String getClothesType();

    Long getAmount();

    Integer getUnitPrice();

    String getStatus();

    Boolean getNewOrder();

    String getFio();
}