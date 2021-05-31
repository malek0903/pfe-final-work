package org.tn.esprit.error;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private List<java.lang.Error> errors = new ArrayList<>();
}
