package com.example.ecommercespring.service;


import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface StatisticService {

    public ResponseEntity<?> getRevenueByDate(Long dateStart,Long dateEnd);//, Date dateEnd
    public ResponseEntity<?> getRevenueByYear(Integer year);
    public ResponseEntity<?> getProfitByDate(Long dateStartTime, Long dateEndTime);
    public ResponseEntity<?> getProfitByYear(Integer year);
}
