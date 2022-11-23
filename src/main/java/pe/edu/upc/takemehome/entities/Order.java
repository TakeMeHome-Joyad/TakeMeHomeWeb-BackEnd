package pe.edu.upc.takemehome.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
//import java.util.List;

@Data
@Entity
@Table(name="orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date limitDate;
    private String destinationCountry;
    private String destinationCity;
    private String destinationAddress;

    private String originCountry;
    private String originCity;
    private String nameProduct;
    private String categoryProduct;
    private Number priceProduct;
    @Column(length = 2000)
    private String urlProduct;
    private String dimensionsProduct;

    
     @OneToOne(mappedBy = "order")
     private Shipment shipment;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(Date limitDate, String destinationCountry, String destinationCity, String destinationAddress, String originCountry,String originCity, String urlProduct, String nameProduct, String categoryProduct, Number priceProduct, String dimensionsProduct,User user) {
        this.limitDate = limitDate;
        this.destinationCountry = destinationCountry;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.originCountry = originCountry;
        this.originCity=originCity;
        this.nameProduct = nameProduct;
        this.categoryProduct = categoryProduct;
        this.priceProduct = priceProduct;
        this.urlProduct = urlProduct;
        this.dimensionsProduct = dimensionsProduct;
        this.user=user;
    }


}
