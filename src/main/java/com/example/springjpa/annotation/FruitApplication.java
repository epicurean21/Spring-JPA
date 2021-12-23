package com.example.springjpa.annotation;

import com.example.springjpa.annotation.object.Apple;
import com.example.springjpa.annotation.service.FruitInfoService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FruitApplication {
    public static void main(String[] args) {
        FruitInfoService.getFruitInfo(Apple.class);
    }
}
