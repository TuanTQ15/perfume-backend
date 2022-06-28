package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.OrderSupply;
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
public class OrderSupplyDTO {

    private Long orderSupplyId;
    private Date bookingDate;
    private Integer status;
    private Date receivedDate;

    private Long employeeId;
    private Long brandId;
    private List<DetailOSDTO> detailOSList  = new ArrayList<>();

    public OrderSupplyDTO(OrderSupply orderSupply){
        this.orderSupplyId = orderSupply.getOrderSupplyId();
        this.bookingDate = orderSupply.getBookingDate();
        this.status = orderSupply.getStatus();
        this.receivedDate = orderSupply.getReceivedDate();
        this.employeeId = orderSupply.getUser().getId();
        this.brandId = orderSupply.getBrand().getBrandId();
        this.detailOSList = orderSupply.getDetailOSList().stream().map(DetailOSDTO::new).collect(Collectors.toList());
    }
    public OrderSupply toEntity(){
        OrderSupply orderSupply = new OrderSupply();
        orderSupply.setBookingDate(this.bookingDate);
        orderSupply.setStatus(this.status);
        orderSupply.setReceivedDate(this.receivedDate);
        return orderSupply;
    }
}
