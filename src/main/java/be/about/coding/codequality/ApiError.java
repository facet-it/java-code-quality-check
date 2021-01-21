package be.about.coding.codequality;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError implements Serializable {

    private String error;
    private String message;
    private String detail;

}
