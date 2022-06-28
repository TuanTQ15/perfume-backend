package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.OrderUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserDTO {
    private Long orderId ;
    private String fullName ;
    private String phoneNumber ;
    private String address ;
    private Date bookingDate ;
    private Date deliveryDate ;
    private Integer status ;
    private String note ;
    private String transactionId ;
    private Long employeeId;
    private Long customerId;
    private Integer total;

    private List<DetailOrderDTO> detailOrderList = new ArrayList<>();

    public OrderUserDTO(OrderUser order){
        this.orderId = order.getOrderId();
        this.fullName = order.getFullName();
        this.phoneNumber = order.getPhoneNumber();
        this.address = order.getAddress();
        this.bookingDate = order.getBookingDate();
        this.deliveryDate = order.getDeliveryDate();
        this.status = order.getStatus();
        this.note = order.getNote();
        this.transactionId = order.getTransactionId();

        if (order.getEmployee() == null) this.employeeId = null;
        else this.employeeId = order.getEmployee().getId();

        this.customerId = order.getCustomer().getId();
        this.detailOrderList = order.getDetailOrders().stream().map(DetailOrderDTO:: new).collect(Collectors.toList());

        this.total = order.getDetailOrders().stream().mapToInt(value -> value.getPrice()*value.getQuantity()).sum();
    }

    public OrderUser toEntity(){
        OrderUser orderUser = new OrderUser();
        orderUser.setFullName(this.fullName);
        orderUser.setPhoneNumber(this.phoneNumber);
        orderUser.setAddress(this.address);
        orderUser.setBookingDate(this.bookingDate);
        orderUser.setDeliveryDate(this.deliveryDate);
        orderUser.setStatus(this.status);
        orderUser.setNote(this.note);
        orderUser.setTransactionId(this.transactionId);
//        List<DetailOrder> detailOrderList = new ArrayList<>();
//        for (DetailOrderDTO detailOrderDTO:this.getDetailOrderList()
//             ) {
//            detailOrderList.add(detailOrderDTO.toEntity());
//        }
//        orderUser.setDetailOrders(detailOrderList);

//        orderUser.setDetailOrders(this.getDetailOrderList().stream().map(detailOrderDTO -> detailOrderDTO.toEntity()).collect(Collectors.toList()));
        return  orderUser;
    }
}
