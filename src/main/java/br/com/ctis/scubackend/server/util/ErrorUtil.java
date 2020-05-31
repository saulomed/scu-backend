/*
 * Criação : 31/05/2020
 */

package br.com.ctis.scubackend.server.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Classe responsável pela padronização de resposta de erro
 */
public class ErrorUtil
{

   public static ResponseEntity badRequest(String mensagem) {
      return error(mensagem, HttpStatus.BAD_REQUEST);
   }

   public static ResponseEntity notFound(String mensagem) {
      return error(mensagem, HttpStatus.NOT_FOUND);
   }

   public static ResponseEntity internalServerError(String mensagem) {
      return error(mensagem, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   public static ResponseEntity error(String mensagem, HttpStatus httpStatus) {
      Map<Object, Object> model = new HashMap<>();
      model.put("error", httpStatus.getReasonPhrase());
      model.put("status", httpStatus.value());
      model.put("message", mensagem);

      return new ResponseEntity(model, httpStatus);
   }
}
