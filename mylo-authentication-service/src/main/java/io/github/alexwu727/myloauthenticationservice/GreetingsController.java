package io.github.alexwu727.myloauthenticationservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingsController {
    @GetMapping("/")
    public ResponseEntity<String> greetings() {
        // return json object with message
        return ResponseEntity.ok("Hello, World!");
    }

    @GetMapping("/goodbye")
    public ResponseEntity<String> goodbye() {
        return ResponseEntity.ok("Goodbye, World!");
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedGreetings() {
        return ResponseEntity.ok("Hello, Protected World!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminGreetings() {
        return ResponseEntity.ok("Hello, Admin World!");
    }
}
