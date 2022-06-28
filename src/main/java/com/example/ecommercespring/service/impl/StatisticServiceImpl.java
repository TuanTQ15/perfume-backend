package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.AveragePrice;
import com.example.ecommercespring.entity.DetailOrder;
import com.example.ecommercespring.entity.DetailReceipt;
import com.example.ecommercespring.repository.*;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    DetailOrderRepository detailOrderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DetailReceiptRepository detailReceiptRepository;

    @Override
    public ResponseEntity<?> getRevenueByDate(Long dateStartTime, Long dateEndTime) {//, Date dateEnd
        long subTime = dateEndTime - dateStartTime;
        long subDate = Math.abs(TimeUnit.DAYS.convert(subTime, TimeUnit.MILLISECONDS)) + 1;
        if (subDate > 30) {
            return ResponseEntity.ok(new Response(false, "Vui lòng chọn khoảng cách ít hơn 30 ngày"));
        }
        Date dateStart = new Date(dateStartTime);

        List<Integer> revenueList = new ArrayList<>();
        for (int i = 0; i < subDate; i++) {
            Date pre = new Date(dateStartTime);
            Date next = new Date();

            Calendar c = Calendar.getInstance();
            c.setTime(pre);
            c.add(Calendar.DATE, i);
            pre = c.getTime();

            c.add(Calendar.DATE, 1);
            next = c.getTime();
//findByDateCreatedBetween
            Integer revenueByDate = invoiceRepository.findByDateCreatedBetweenAndOrderUser_StatusIsLessThanEqual(pre, next, 2).stream()
                    .mapToInt(value -> value.getTotalPrice()).sum();
            revenueList.add(revenueByDate);
        }
        return ResponseEntity.ok(revenueList);
    }

    @Override
    public ResponseEntity<?> getRevenueByYear(Integer year) {
        int yearCurrent = Calendar.getInstance().get(Calendar.YEAR);
        if (year > yearCurrent) {
            return ResponseEntity.badRequest().body(new Response(false, "Không thể thống kê thời gian trong tương lai"));
        }

        List<Integer> integerList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        int month = 1;
        if (year == yearCurrent) {
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        } else {
            month = 12;
        }
        for (int i = 0; i < month; i++) {
            cal.set(year, i, 1);
            Date firstDateOfMonth = new Date(cal.getTimeInMillis());
            cal.set(year, i + 1, 1);
            Date lastDateOfMonth = new Date(cal.getTimeInMillis());

            try {
                Integer sum = invoiceRepository.findAll().stream()
                        .filter(invoice -> invoice.getDateCreated().compareTo(firstDateOfMonth) > 0
                                && invoice.getDateCreated().compareTo(lastDateOfMonth) < 0
                                && invoice.getOrderUser().getStatus() != 3)
                        .mapToInt(value -> value.getTotalPrice())
                        .sum();
                integerList.add(sum);
            } catch (Exception e) {
                integerList.add(0);
            }
        }
        return ResponseEntity.ok(integerList);
    }


    @Override
    public ResponseEntity<?> getProfitByDate(Long dateStartTime, Long dateEndTime) {//, Date dateEnd
        long subTime = dateEndTime - dateStartTime;
        long subDate = Math.abs(TimeUnit.DAYS.convert(subTime, TimeUnit.MILLISECONDS)) + 1;
        if (subDate > 30) {
            return ResponseEntity.ok(new Response(false, "Vui lòng chọn khoảng cách ít hơn 30 ngày"));
        }
        Date dateStart = new Date(dateStartTime);
        List<Long> idProductList = detailOrderRepository
                .findByOrderUser_BookingDateBetweenAndOrderUser_StatusIsLessThanEqual(new Date(dateStartTime), new Date(dateEndTime), 2)
                .stream().map(value -> value.getId().getProductId())
                .collect(Collectors.toList());

        List<AveragePrice> averagePriceList = new ArrayList<>();
        for (Long id : idProductList) {
            List<DetailReceipt> detailOSList = detailReceiptRepository.findById_ProductId(id);
            Integer sumQuantity = detailOSList.stream().mapToInt(value -> value.getQuantity()).sum();
            Integer sumPrice = detailOSList.stream().mapToInt(value -> value.getPrice() * value.getQuantity()).sum();

            AveragePrice averagePrice = new AveragePrice();
            averagePrice.setId(id);
            averagePrice.setPrice(Float.parseFloat(String.valueOf(sumPrice / sumQuantity)));
            averagePriceList.add(averagePrice);
        }

        List<Integer> profitList = new ArrayList<>();
        for (int i = 0; i < subDate; i++) {
            Date pre = new Date(dateStartTime);
            Date next = new Date();

            Calendar c = Calendar.getInstance();
            c.setTime(pre);
            c.add(Calendar.DATE, i);
            pre = c.getTime();

            c.add(Calendar.DATE, 1);
            next = c.getTime();

            List<DetailOrder> detailOrderList = detailOrderRepository
                    .findByOrderUser_BookingDateBetweenAndOrderUser_StatusIsLessThanEqual(pre, next, 2);

            int sumRevenueByDate = 0;
            //detailOrderList.stream().mapToInt(value -> value.getQuantity()* value.getPrice()).sum();
            int sumPriceImport = 0;
            for (DetailOrder detailOrder : detailOrderList) {
                sumRevenueByDate += detailOrder.getQuantity() * detailOrder.getPrice();
                float priceAverage = averagePriceList.stream().filter(value -> value.getId() == detailOrder.getId().getProductId())
                        .findFirst().get().getPrice();
                sumPriceImport += priceAverage * (float) detailOrder.getQuantity();
            }
            profitList.add(sumRevenueByDate - sumPriceImport);
        }
        return ResponseEntity.ok(profitList);
    }


    @Override
    public ResponseEntity<?> getProfitByYear(Integer year) {
        int yearCurrent = Calendar.getInstance().get(Calendar.YEAR);
        if (year > yearCurrent) {
            return ResponseEntity.badRequest().body(new Response(false, "Không thể thống kê thời gian trong tương lai"));
        }

        List<Integer> integerList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        int month = 1;
        if (year == yearCurrent) {
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        } else {
            month = 12;
        }
        Calendar pre = Calendar.getInstance();
        pre.set(year, 01, 01);
        Calendar next = Calendar.getInstance();
        next.set(year, 01, 01);
        next.add(Calendar.MONTH, month);

        List<Long> idProductList = detailOrderRepository
                .findByOrderUser_BookingDateBetweenAndOrderUser_StatusIsLessThanEqual(pre.getTime(), next.getTime(), 2)
                .stream().map(value -> value.getId().getProductId())
                .collect(Collectors.toList());

        List<AveragePrice> averagePriceList = new ArrayList<>();
        for (Long id : idProductList) {
            List<DetailReceipt> detailOSList = detailReceiptRepository.findById_ProductId(id);
            Integer sumQuantity = detailOSList.stream().mapToInt(value -> value.getQuantity()).sum();
            Integer sumPrice = detailOSList.stream().mapToInt(value -> value.getPrice() * value.getQuantity()).sum();

            AveragePrice averagePrice = new AveragePrice();
            averagePrice.setId(id);
            averagePrice.setPrice(Float.parseFloat(String.valueOf(sumPrice / sumQuantity)));
            averagePriceList.add(averagePrice);
        }

        List<Integer> profitList = new ArrayList<>();
        for (int i = 0; i < month; i++) {
            cal.set(year, i, 1);
            Date firstDateOfMonth = new Date(cal.getTimeInMillis());
            cal.set(year, i + 1, 1);
            Date lastDateOfMonth = new Date(cal.getTimeInMillis());

            List<DetailOrder> detailOrderList = detailOrderRepository
                    .findByOrderUser_BookingDateBetweenAndOrderUser_StatusIsLessThanEqual(firstDateOfMonth, lastDateOfMonth, 2);

            int sumRevenueByDate = 0;
            //detailOrderList.stream().mapToInt(value -> value.getQuantity()* value.getPrice()).sum();
            int sumPriceImport = 0;
            for (DetailOrder detailOrder : detailOrderList) {
                sumRevenueByDate += detailOrder.getQuantity() * detailOrder.getPrice();
                float priceAverage = averagePriceList.stream().filter(value -> value.getId() == detailOrder.getId().getProductId())
                        .findFirst().get().getPrice();
                sumPriceImport += priceAverage * (float) detailOrder.getQuantity();
            }
            profitList.add(sumRevenueByDate - sumPriceImport);
        }
        return ResponseEntity.ok(profitList);
    }
}


