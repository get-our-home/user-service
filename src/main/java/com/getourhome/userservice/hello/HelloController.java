package com.getourhome.userservice.hello;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Example", description = "Example API")
@RestController
public class HelloController {

    @GetMapping("/hello")
    @Operation(summary = "예시 API", description = "예시 API에 대한 설명, hello world에서 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Not Found")
    })
    public String hello(){
        return "Hello, World!";
    }
}