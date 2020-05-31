/*
 * Criação : 30/05/2020
 */

package br.com.ctis.scubackend.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ctis.scubackend.server.entity.Usuario;

/**
 * Classe responsável pelo repositorio da entidade clientes
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{

}
