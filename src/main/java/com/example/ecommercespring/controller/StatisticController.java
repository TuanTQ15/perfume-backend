package com.example.ecommercespring.controller;

import com.example.ecommercespring.service.InvoiceService;
import com.example.ecommercespring.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    @Autowired
    StatisticService statisticService;


    @GetMapping("/revenue/date")
    public ResponseEntity<?> getRevenueByDate(@RequestParam("date-start")Long dateStart, @RequestParam("date-end")Long dateEnd){//
        return statisticService.getRevenueByDate(dateStart,dateEnd);//dateEnd
    }

    @GetMapping("/revenue/year")
    public ResponseEntity<?> getRevenueByYear(@RequestParam("year")Integer year){
        return statisticService.getRevenueByYear(year);
    }

    @GetMapping("/profit/date")
    public ResponseEntity<?> getProfitByDate(@RequestParam("date-start")Long dateStart, @RequestParam("date-end")Long dateEnd){//
        return statisticService.getProfitByDate(dateStart,dateEnd);//dateEnd
    }

    @GetMapping("/profit/year")
    public ResponseEntity<?> getProfitByYear(@RequestParam("year")Integer year){
        return statisticService.getProfitByYear(year);
    }
}
