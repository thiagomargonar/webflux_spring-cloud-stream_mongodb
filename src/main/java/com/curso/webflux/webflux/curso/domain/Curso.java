package com.curso.webflux.webflux.curso.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "modulos")
public class Curso {
    @Id
    private String id;

    private String item;

    private String price;

    private String ordererd;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrdererd() {
        return ordererd;
    }

    public void setOrdererd(String ordererd) {
        this.ordererd = ordererd;
    }
}
