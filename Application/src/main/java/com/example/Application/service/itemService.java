package com.example.Application.service;

import com.example.Application.model.item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class itemService {

    private final List<item> Items = new ArrayList<>();

    public item addItem(item item){
        Items.add(item);
        return item;
    }

    public List<item> getItem(){
        return Items;
    }


    public item getItemById(Long id){
        return Items.stream().filter(i->i.getId().equals(id)).findFirst().orElse(null);
    }

}
