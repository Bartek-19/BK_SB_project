package com.example.BK_SB_project;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class JSONList {

    private List<JSONObject> objList;

    public JSONList(int size) {
        this.objList = new ArrayList<>();
        Iterator<JSONObject> it;
        JSONObject current;
        int _id;
        int location_id;
        boolean idRepeats;
        boolean locationIdRepeats;
        Random rand = new Random();

        for(int i=0; i<size; i++) {
            objList.add(new JSONObject());
        }

        it = objList.iterator();

        while(it.hasNext()) {
            current = it.next();

            do {
                _id = rand.nextInt(100000000);
                idRepeats = false;
                for(JSONObject temp : objList) {
                    if (current != temp && _id == temp.get_id()) {
                        idRepeats = true;
                        break;
                    }
                }
            } while(idRepeats);
            current.set_id(_id);

            do {
                location_id = rand.nextInt(1000000);
                locationIdRepeats = false;
                for(JSONObject temp : objList) {
                    if (current != temp && location_id == temp.getLocation_id()) {
                        if(current.getLongitude()==temp.getLongitude() && current.getLatitude()==temp.getLatitude())
                            location_id = temp.getLocation_id();
                        else {
                            locationIdRepeats = true;
                            break;
                        }
                    }
                }
            } while(locationIdRepeats);
            current.setLocation_id(location_id);
        }
    }

    public List<JSONObject> getObjList() {
        return objList;
    }

    public void setObjList(List<JSONObject> objList) {
        this.objList = objList;
    }
}
