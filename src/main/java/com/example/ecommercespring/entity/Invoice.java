package com.example.ecommercespring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId ;
    private Date dateCreated ;
    private Integer totalPrice ;
    private Integer taxCode ;

    @ManyToOne
    @JoinColumn(name = "shipperId")
    private Shipper shipper;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private User user;

    @OneToOne
//    @MapsId
    @JoinColumn(name = "orderId")
    private OrderUser orderUser;
}
