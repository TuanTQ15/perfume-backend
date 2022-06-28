package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.Invoice;
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
public class InvoiceDTO {
    private Long invoiceId ;
    private Date dateCreated ;
    private Integer totalPrice ;
    private Integer taxCode ;

    private Long orderId;
    private Long employeeId;
    private Long shipperId;
    private String fullName;
    private String phoneNumber;
    private String address;

    private List<DetailOrderDTO> detailOrderList = new ArrayList<>();

    public InvoiceDTO (Invoice invoice){
        this.invoiceId = invoice.getInvoiceId();
        this.dateCreated = invoice.getDateCreated();
        this.totalPrice = invoice.getTotalPrice();
        this.taxCode = invoice.getTaxCode();

        this.orderId = invoice.getOrderUser().getOrderId();
        this.employeeId = invoice.getUser().getId();
        this.shipperId = invoice.getShipper().getShipperId();
        this.fullName = invoice.getOrderUser().getFullName();
        this.phoneNumber = invoice.getOrderUser().getPhoneNumber();
        this.address = invoice.getOrderUser().getAddress();
        this.detailOrderList = invoice.getOrderUser().getDetailOrders().stream().map(DetailOrderDTO::new)
                .collect(Collectors.toList());
    }

    public Invoice toEntity(){
        Invoice invoice = new Invoice();
        invoice.setDateCreated(this.dateCreated);
        invoice.setTotalPrice(this.totalPrice);
        invoice.setTaxCode(this.taxCode);
        return invoice;
    }
}
