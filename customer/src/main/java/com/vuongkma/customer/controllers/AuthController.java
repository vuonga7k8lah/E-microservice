package com.vuongkma.customer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongkma.customer.dto.AuthResponse;
import com.vuongkma.customer.dto.LoginDTO;
import com.vuongkma.customer.helpers.APIHelper;
import com.vuongkma.customer.helpers.ResponseFormat;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping(APIHelper.restRoot + "auth")
public class AuthController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${keycloak.client_secret}")
    private String clientSecret;

    @Value("${keycloak.client_id}")
    private String clientId;

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @PostMapping()
    @ResponseBody
    private ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // 1. Tạo dữ liệu form-urlencoded
            String requestData = "client_id=" + clientId +
                    "&username=" + loginDTO.getUsername() +
                    "&password=" + loginDTO.getPassword() +
                    "&grant_type=password" +
                    "&client_secret=" + clientSecret;

            // 2. Thiết lập Header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 3. Tạo HttpEntity từ headers và dữ liệu form-urlencoded
            HttpEntity<String> entity = new HttpEntity<>(requestData, headers);

            // 4. Gửi yêu cầu POST tới Keycloak
            ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper mapper=new ObjectMapper();
                AuthResponse value=mapper.readValue(response.getBody(),AuthResponse.class);
                return ResponseEntity.ok(
                        ResponseFormat.build(value, "Get data has been successfully.")
                );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getBody());
            }
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (IllegalStateException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
