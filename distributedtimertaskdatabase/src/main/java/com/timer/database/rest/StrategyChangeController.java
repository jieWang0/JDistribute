package com.timer.database.rest;

import com.timer.database.service.StrategyChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/strategy")
public class StrategyChangeController {
    @Autowired
    StrategyChangeService changeService;

    @RequestMapping("change")
    public void changeStrategy(@RequestParam(required = true) String name) {
        try {
            changeService.changeStrategy(name,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
