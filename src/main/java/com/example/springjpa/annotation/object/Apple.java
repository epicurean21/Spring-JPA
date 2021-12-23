package com.example.springjpa.annotation.object;

import com.example.springjpa.annotation.annotations.FruitColor;
import com.example.springjpa.annotation.annotations.FruitName;
import com.example.springjpa.annotation.annotations.FruitProvider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apple {
    @FruitName("Apple") // Annotation !
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String appleColor;

    @FruitProvider(id = 3, name = "CheongSong Apple", address = "CheongSong")
    private String appleProvider;
}
