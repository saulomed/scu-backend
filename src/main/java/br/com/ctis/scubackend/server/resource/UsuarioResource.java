/*
 * Criação : 30/05/2020
 */

package br.com.ctis.scubackend.server.resource;

import java.util.Optional;
import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.ctis.scubackend.server.dto.UsuarioDTO;
import br.com.ctis.scubackend.server.entity.Usuario;
import br.com.ctis.scubackend.server.repository.UsuarioRepository;
import br.com.ctis.scubackend.server.util.ErrorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe de controller para a entidade usuario
 */
@RestController
@RequestMapping("/v1/usuarios")
@CrossOrigin(origins = "*")
@Api(tags = "Usuario", value = "API de usuários")
public class UsuarioResource
{

   public static final int QUANTIDADE_MIN_CARACTERES = 5;
   private final Logger LOGGER = LoggerFactory.getLogger(UsuarioResource.class);

   @Autowired
   private UsuarioRepository usuarioRepository;

   @ApiOperation(value = "Listar todos os usuários")
   @GetMapping()
   public Iterable<Usuario> listar()
   {
      return usuarioRepository.findAll();
   }

   @ApiOperation(value = "Buscar um usuario por Id")
   @GetMapping(value = "/{id}")
   public ResponseEntity<Usuario> listar(@PathVariable Long id)
   {
      LOGGER.info("Iniciar listar usuario por id: " + id);
      Optional<Usuario> usuario = usuarioRepository.findById(id);
      if (!usuario.isPresent())
      {
         //return ApiError.notFount("Usuario não encontrado");
         return ErrorUtil.notFound("Não existem usuários cadastrados");
      }
      LOGGER.info("Fim listar usuario por id ");
      return new ResponseEntity<>(usuario.get(), HttpStatus.OK);

   }

   @ApiOperation(value = "Criar novo usuário")
   @PostMapping
   public ResponseEntity<Usuario> criar(@RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder ucBuilder)
   {
      LOGGER.info("Iniciar criar usuario: " + usuarioDTO.getNome() + " login: " + usuarioDTO.getLogin());
      try
      {
         Usuario usuario = new Usuario();

         if (!validarCampoStringObrigatorio(usuarioDTO.getNome()))
         {
            return ErrorUtil.badRequest("Informe o nome do usuário.");
         }

         if (!validarCampoStringObrigatorio(usuarioDTO.getLogin()))
         {
            return ErrorUtil.badRequest("Informe o login do usuário.");
         }

         if (!validarCampoStringObrigatorio(usuarioDTO.getSenha(), QUANTIDADE_MIN_CARACTERES))
         {
            return ErrorUtil.badRequest("A senha não foi informada ou não atende a quantidade minima de caracteres.");
         }
         usuario.setLogin(usuarioDTO.getLogin());
         usuario.setNome(usuarioDTO.getNome());
         usuario.setSenha(usuarioDTO.getSenha());

         usuario = usuarioRepository.save(usuario);
         if (usuario == null)
         {
            return ErrorUtil.internalServerError("Ocorreu algum erro na criação do usuário");
         }

         LOGGER.info("Fim metodo - usuario criado com sucesso");
         return new ResponseEntity<>(usuario, HttpStatus.CREATED);

      }
      catch (Exception e)
      {
         LOGGER.error("Erro ao criar um usuario.", e);
         return ErrorUtil.internalServerError("Ocorreu algum erro na criação do usuário");
      }

   }

   @ApiOperation(value = "Remove um usuario")
   @DeleteMapping(value = "/{id}")
   public ResponseEntity<Usuario> deletar(@PathVariable Long id)
   {
      LOGGER.info("Iniciar deletar usuario id: " + id);
      Optional<Usuario> usuario = usuarioRepository.findById(id);
      if (!usuario.isPresent())
      {
         LOGGER.error("Erro ao deletar um usuario. Usuario não encontrado!");
         return ErrorUtil.notFound("Usuário não encontrado");
      }
      else
      {
         usuarioRepository.deleteById(id);
      }
      LOGGER.info("fim deletar usuario id: " + id);
      return new ResponseEntity<>(HttpStatus.OK);
   }

   /**
    * Valida obrigatoriedade de campo string
    *
    * @param campo
    * @return
    */
   private boolean validarCampoStringObrigatorio(String campo)
   {
      boolean retorno = true;
      if (StringUtils.isNullOrEmpty(campo))
      {
         retorno = false;
      }

      return retorno;
   }

   /**
    * Valida a obrigatoriedade de campo string e quantidade de caracteres
    *
    * @param campo
    * @param qtd
    * @return
    */
   private boolean validarCampoStringObrigatorio(String campo, Integer qtd)
   {
      boolean retorno = true;
      retorno = validarCampoStringObrigatorio(campo);
      if (retorno && campo.length() < qtd)
      {
         retorno = false;
      }
      return retorno;
   }

}
