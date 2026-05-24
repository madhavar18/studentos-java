package com.studentos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // tells Spring: this class handles HTTP requests AND returns data directly

public class HelloController {

    // Maps GET requests to "/" to this method
    @GetMapping("/")
    public String home() {
        return "StudentOS is running";
        // Spring converts this string to an HTTP response body
        // Status code: 200 OK (default)
        // Content-type: text/plain
    }

    // @PathVariable extracts the value from the URL path
    // GET /hello/Madhava -> name = "Madhava"
    // GET /hello/Ravi -> name = "Ravi"
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello, " + name + "! StudentOS backend is running";
    }

    // @RequestParam extracts from query string
    // GET /greet?message=Welcome -> message = "Welcome"
    // GET /greet -> message = "Default message" (uses default)
    @GetMapping("/greet")
        public String greet(
                @RequestParam(defaultValue = "No message provided") String message ) {
            return "Message received: " + message;
    }
}
