package com.gourav.service;

import com.gourav.manager.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ScraperService {

    @Autowired
    Manager managerBean;

    @GetMapping("/get")
    List<?> getDataService(){
        return managerBean.getManager();
    }
}
