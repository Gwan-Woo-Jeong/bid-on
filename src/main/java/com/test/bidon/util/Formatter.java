package com.test.bidon.util;

import org.springframework.stereotype.Component;

@Component("formatter")
public class Formatter {
    public String price(Integer price) {
        return String.format("%,d", price);
    }

    public String wonPrice(Integer price) {
        return "₩" + String.format("%,d", price);
    }

    public String priceWon(Integer price) {
        return String.format("%,d", price) + "₩";
    }
}