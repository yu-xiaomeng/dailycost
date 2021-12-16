package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.Bill;
import com.xiaomeng.dailycost.dto.BillDto;
import com.xiaomeng.dailycost.dto.BillStatDto;
import com.xiaomeng.dailycost.dto.MonthlyBillDto;
import com.xiaomeng.dailycost.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/bill")
@SecurityRequirement(name = "dailycostapi")
public class BillController {
    @Autowired
    private BillService billService;

    @Operation(summary = "create a new bill details")
    @PostMapping()
    public Map<String, Object> create(@Valid @RequestBody BillDto billDto) throws Exception{
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", billService.create(billDto));
            return map;
        } catch (Exception e) {
            throw e;
        }

    }

    @Operation(summary = "get bill details list in one month")
    @SneakyThrows
    @GetMapping("/list")
    public List<BillStatDto> findBillDaily(@RequestParam String date) {
        List<BillStatDto> result = new ArrayList<>();
        Date d = new SimpleDateFormat("yyyy-MM").parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(d);
        Integer month = calendar.get(Calendar.MONTH);
        Integer m = month;

        while ( m == month) {
            String dateStr = date + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            BillStatDto bill = billService.findBillByDay(newDate);
            if (bill.getBill().size() != 0) {
                result.add(bill);
            }
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1); //add 1
            m = calendar.get(Calendar.MONTH);
        }
        return result;
    }

    @Operation(summary = "get bill details by its id")
    @GetMapping("/{id}")
    public Optional<Bill> findById(@PathVariable String id) {
        return billService.findById(id);
    }

    @Operation(summary = "update one bill details")
    @PutMapping()
    public Map<String, Object> update(@Valid @RequestBody BillDto billDto) throws ParseException {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", billService.update(billDto));
            return map;
        } catch (Exception e) {
            throw e;
        }
    }

    @Operation(summary = "delete one bill details by its id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        billService.delete(id);
    }

    @Operation(summary = "get bill in one month")
    @GetMapping("/stat/monthly")
    public MonthlyBillDto monthlyBill(@RequestParam String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM").parse(date);
        return billService.findMonthlyBill(d);

    }

    @Operation(summary = "get bill in one year")
    @GetMapping("/stat/yearly")
    public List<MonthlyBillDto> yearlyBill(@RequestParam String year) throws ParseException {
        return billService.findYearlyBill(year);
    }

}
