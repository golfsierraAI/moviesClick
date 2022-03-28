package com.gourav.service;

import com.gourav.manager.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ScraperService {

    @Autowired
    Manager managerBean;

    @GetMapping(value = "/get/{page}")
    List<?> getDataService(@PathVariable("page") Integer page){
        return managerBean.getManager(page);
    }

    @PostMapping(value = "/search")
    List<?> searchMovieService(@RequestBody Map<String,String> request){
        return managerBean.searchMovieManager(request.get("name"));
    }

}
