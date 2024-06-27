package com.projects.backend.login;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

   @GetMapping("/csrf")
   public CsrfToken csrf(CsrfToken token) {
      return token;
   }

}