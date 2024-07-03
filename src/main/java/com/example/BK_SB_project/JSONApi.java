package com.example.BK_SB_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class JSONApi {
    @Autowired
    private JSONList list;

    @GetMapping("/generate/json/{size}")
    public List<JSONObject> lista(@PathVariable("size") int size) {
        list = new JSONList(size);
        return list.getObjList();
    }
}
