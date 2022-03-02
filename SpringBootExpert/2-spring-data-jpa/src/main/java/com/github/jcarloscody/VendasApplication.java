package com.github.jcarloscody;


import com.github.jcarloscody.domain.entity.Cliente;
import com.github.jcarloscody.domain.repositorio.Clientes_jdbcTemplate;
import com.github.jcarloscody.domain.repositorio.ClientesEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes_jdbcTemplate clientesJdbcTemplate, @Autowired ClientesEntityManager clientesEntityManager){
        return args -> {
           /* clientesEntityManager.salvar(new Cliente("josue"));
            clientesEntityManager.salvar(new Cliente("marcos"));
            clientesEntityManager.salvar(new Cliente("silveira"));

            clientesEntityManager.buscarTodos().forEach(cliente -> {
                clientesEntityManager.deletar(cliente);
            });

            */ //TRABALHANDO COM JDBCTEMPLATE  precisa do data.sql no resources & não se usa o JPA nas classes de entidades
                //o Spring tem a capacidade de trabalhar com ele tbm

            clientesJdbcTemplate.salvar(new Cliente("josue"));
            clientesJdbcTemplate.salvar(new Cliente("Carlos"));

            System.out.println("MOSTRANDO TODOS");
            clientesJdbcTemplate.obterTodos().forEach(c -> {
                System.out.println(c.getNome());
            });
            //OU
             clientesJdbcTemplate.obterTodos().forEach(System.out::println);

            System.out.println("DELETANDO TODOS");
            clientesJdbcTemplate.obterTodos().forEach(c -> {
                clientesJdbcTemplate.deletar(c);
            });

            clientesJdbcTemplate.buscarNome("os").forEach(System.out::println);


            System.out.println("DELETANDO TODOS");


            System.out.println("RESULTADO APOS DELECAO");
            clientesJdbcTemplate.obterTodos().forEach(System.out::println);


        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }


}
