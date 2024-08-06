package com.example.loginauthapi.dto;

import java.util.List;

public record OrderRequestDTO(Double weight, String delivery_date, String user, Long cake_model,
                              List<Long> complements, List<Long> fillings, Long payment_method) {

}
