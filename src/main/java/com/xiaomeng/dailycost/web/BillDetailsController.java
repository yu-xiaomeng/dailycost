package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.BillDetails;
import com.xiaomeng.dailycost.dto.BillDetailsDto;
import com.xiaomeng.dailycost.dto.BillDto;
import com.xiaomeng.dailycost.service.BillDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/bill")
public class BillDetailsController {
    @Autowired
    private BillDetailsService billDetailsService;

    @PostMapping("/details")
    public Map<String, Object> create(@Valid @RequestBody BillDetailsDto billDetailsDto) throws Exception{
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", billDetailsService.create(billDetailsDto));
            return map;
        } catch (Exception e) {
            throw e;
        }

    }

    @SneakyThrows
    @GetMapping("/details/list")
    public List<BillDto> findBillDaily(@RequestParam String date) {
        List<BillDto> result = new ArrayList<>();
        Date d = new SimpleDateFormat("yyyy-MM").parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(d);
        Integer month = calendar.get(Calendar.MONTH);
        Integer m = month;

        while ( m == month) {
            String dateStr = date + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            BillDto bill = billDetailsService.findBillByDay(newDate);
            if (bill.getBillDetails().size() != 0) {
                result.add(bill);
            }
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1); //add 1
            m = calendar.get(Calendar.MONTH);
        }
        return result;
    }

    @GetMapping("/details/{id}")
    public Optional<BillDetails> findById(@PathVariable String id) {
        return billDetailsService.findById(id);
    }

    @PutMapping("/details")
    public String update(@Valid @RequestBody BillDetailsDto billDetailsDto) throws ParseException {
        return billDetailsService.update(billDetailsDto);
    }

}
