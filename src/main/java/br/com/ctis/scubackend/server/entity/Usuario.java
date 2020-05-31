/*
 * Criação : 30/05/2020
 */

package br.com.ctis.scubackend.server.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe de entidade para Usuario
 */
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable
{

   private static final long serialVersionUID = -5873042165026769063L;

   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private Long id;

   @ApiModelProperty(notes = "Nome do usuário")
   private String nome;

   @ApiModelProperty(notes = "Login do usuário")
   @Column(unique = true)
   private String login;

   @ApiModelProperty(notes = "Senha do usuário")
   private String senha;

}
