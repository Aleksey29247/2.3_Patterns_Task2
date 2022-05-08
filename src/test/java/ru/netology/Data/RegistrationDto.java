package ru.netology.Data;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
    public class RegistrationDto {
        private String login;
    private String password;
    private String status;

    }

