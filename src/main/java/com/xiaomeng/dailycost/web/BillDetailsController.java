package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.BillDetails;
import com.xiaomeng.dailycost.dto.BillDetailsDto;
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
    public List<BillDetails> findByDate(@RequestParam String date) {
        Date d = new SimpleDateFormat("yyyy-MM").parse(date);
        return billDetailsService.findByMonth(d);
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
