package com.example.BackEndSem4.response;

import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private String status;
    private String message;

    private Object data;


}
