package by.prakapienka.at13java.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = OrderItem.ALL, query = "SELECT i FROM OrderItem i WHERE i.order.id=:orderId ORDER BY i.name"),
        @NamedQuery(name = OrderItem.DELETE, query = "DELETE FROM OrderItem i WHERE i.id=:id AND i.order.id=:orderId")
})
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    public static final String DELETE = "OrderItem.delete";
    public static final String ALL = "OrderItem.getAll";

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem() {}

    public OrderItem(String name) {
        this.name = name;
    }

    public OrderItem(String name, Order order) {
        this.name = name;
        this.order = order;
    }

    public OrderItem(Integer id, String name, Order order) {
        super(id);
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "name='" + name + '\'' +
                '}';
    }

}
