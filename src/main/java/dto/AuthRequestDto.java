package dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class AuthRequestDto {
    private String username;
    private String password;

}
