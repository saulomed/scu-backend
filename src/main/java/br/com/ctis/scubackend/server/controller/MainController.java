/*
 * Criação : 30/05/2020
 */

package br.com.ctis.scubackend.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Classe responsavel por redirecionar o acesso principal a documentacao do swagger
 */
@Controller
@RequestMapping("/")
public class MainController
{

   @GetMapping("/")
   public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
      //Redireciona o acesso da raiz para a documentação
      return new RedirectView("swagger-ui.html");
   }
}
