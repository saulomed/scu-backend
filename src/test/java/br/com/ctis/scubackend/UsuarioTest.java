/*
 * Criação : 31/05/2020
 */

package br.com.ctis.scubackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import br.com.ctis.scubackend.server.dto.UsuarioDTO;
import br.com.ctis.scubackend.server.entity.Usuario;
import br.com.ctis.scubackend.server.repository.UsuarioRepository;
import br.com.ctis.scubackend.server.resource.UsuarioResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
         classes = {
                  UsuarioResource.class
         }
)
public class UsuarioTest
{

   private final String BASE_URL = "/v1/usuarios";

   private ObjectMapper objectMapper;

   @Autowired
   private UsuarioResource usuarioApi;

   private MockMvc mockMvc;

   @MockBean
   private UsuarioRepository mockRepo;

   @Before
   public void setUp()
   {
      objectMapper = new ObjectMapper();
      mockMvc = MockMvcBuilders
               .standaloneSetup(usuarioApi)
               .build();
   }

   @Test
   public void adicionarUsuario() throws Exception
   {
      UsuarioDTO usuarioDTO = new UsuarioDTO();
      usuarioDTO.setLogin("login");
      usuarioDTO.setNome("Nome Sobrenome");
      usuarioDTO.setSenha("123456");

      Usuario usuario = new Usuario();
      usuario.setId(1l);
      usuario.setLogin("login");
      usuario.setNome("Nome Sobrenome");
      usuario.setSenha("123456");

      when(mockRepo.save(any(Usuario.class))).thenReturn(usuario);

      mockMvc.perform(post(BASE_URL)
               .content(objectMapper.writeValueAsString(usuarioDTO))
               .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.nome", is("Nome Sobrenome")))
               .andExpect(jsonPath("$.login", is("login")))
               .andExpect(jsonPath("$.senha", is("123456")));

      verify(mockRepo, times(1)).save(any(Usuario.class));
   }

   @Test
   public void buscarTodos() throws Exception
   {
      Usuario usuario = new Usuario();
      usuario.setId(1l);
      usuario.setLogin("login");
      usuario.setNome("Nome Sobrenome");
      usuario.setSenha("123456");

      Usuario usuario2 = new Usuario();
      usuario2.setId(2l);
      usuario2.setLogin("login2");
      usuario2.setNome("Nome Sobrenome");
      usuario2.setSenha("123456");

      List<Usuario> listaUsuarios = new ArrayList<>();
      listaUsuarios.add(usuario);
      listaUsuarios.add(usuario2);

      when(mockRepo.findAll()).thenReturn(listaUsuarios);

      mockMvc.perform(get(BASE_URL)
               .content(objectMapper.writeValueAsString(listaUsuarios))
               .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id", is(1)))
               .andExpect(jsonPath("$[0].nome", is("Nome Sobrenome")))
               .andExpect(jsonPath("$[0].login", is("login")))
               .andExpect(jsonPath("$[0].senha", is("123456")))
               .andExpect(jsonPath("$[1].id", is(2)))
               .andExpect(jsonPath("$[1].nome", is("Nome Sobrenome")))
               .andExpect(jsonPath("$[1].login", is("login2")))
               .andExpect(jsonPath("$[1].senha", is("123456")));

      verify(mockRepo, times(1)).findAll();
   }
}
