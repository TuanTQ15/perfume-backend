package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.Receipt;
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
public class ReceiptDTO {
    private Long receiptId;
    private Date createdDate;

    private Long employeeId;
    private Long orderSupplyId;
    private List<DetailReceiptDTO> detailReceiptList = new ArrayList<>();

    public ReceiptDTO (Receipt receipt){
        this.receiptId = receipt.getReceiptId();
        this.createdDate = receipt.getCreatedDate();
        this.employeeId = receipt.getUser().getId();
        this.orderSupplyId = receipt.getOrderSupply().getOrderSupplyId();
        this.detailReceiptList = receipt.getDetailReceiptList().stream().map(DetailReceiptDTO::new).collect(Collectors.toList());
    }

    public Receipt toEntity(){
        Receipt receipt = new Receipt();
        receipt.setCreatedDate(this.createdDate);
        return receipt;
    }
}
