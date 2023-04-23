package ru.grigoriev.cloudstorage.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseWebResponse {
    private String errorMessage;
}
