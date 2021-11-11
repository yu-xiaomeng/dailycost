package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.Bill;
import com.xiaomeng.dailycost.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping
    public Bill findByDate(@RequestParam String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        return billService.findByDate(d);
    }

    @GetMapping("/list")
    public List<Bill> findByMonth(@RequestParam String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM").parse(date);
        return billService.findByMonth(d);
    }
}
