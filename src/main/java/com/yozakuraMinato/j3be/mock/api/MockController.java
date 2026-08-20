package com.yozakuraMinato.j3be.mock.api;

import com.yozakuraMinato.j3be.mock.api.dto.MockRequest;
import com.yozakuraMinato.j3be.mock.api.dto.MockResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mock")
public class MockController {

    @GetMapping
    public MockResponse get() {
        return new MockResponse("Hello world!");
    }

    @PostMapping
    public MockResponse post(@RequestBody MockRequest request) {
        String message = "Hello " + request.name() + "!";
        return new MockResponse(message);
    }

}

