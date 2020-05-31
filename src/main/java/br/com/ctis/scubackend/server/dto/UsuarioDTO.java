/*
 * Criação : 30/05/2020
 */

package br.com.ctis.scubackend.server.dto;

import java.io.Serializable;
import javax.persistence.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de transporte para a entidade usuário
 */
@Getter
@Setter
public class UsuarioDTO implements Serializable
{

   private static final long serialVersionUID = -8047094490590052438L;

   @ApiModelProperty(notes = "Nome do usuário")
   private String nome;

   @ApiModelProperty(notes = "Login do usuário")
   @Column(unique = true)
   private String login;

   @ApiModelProperty(notes = "Senha do usuário")
   private String senha;
}
