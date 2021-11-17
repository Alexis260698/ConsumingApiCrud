package com.example.ConsumingApiCrud;

import com.example.ConsumingApiCrud.dto.ClienteDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class ConsumingApiCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingApiCrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception{
		return args->{


			generarClientes(restTemplate);
			buscarCliente(3,restTemplate);
			actualizarCliente(restTemplate);
			eliminarCliente(0,restTemplate);
			getAll(restTemplate);
		};
	}

	public void generarClientes(RestTemplate restTemplate){
		System.out.println("------------------------------"+"\n"
				+"Clientes Creados");

		for(int i=0; i<5; i++){
			ClienteDTO cliente= new ClienteDTO();
			cliente.setDni(i+"");
			cliente.setNombre("Cliente " +i);
			ResponseEntity<ClienteDTO> clienteResponse= restTemplate.postForEntity("http://localhost:8080/SpringRest/crearCliente",cliente,ClienteDTO.class);
			System.out.println(clienteResponse.getBody().toString());
		}
	}

	public void buscarCliente(int id,RestTemplate restTemplate){
		ClienteDTO cliente=restTemplate.getForObject("http://localhost:8080/SpringRest/buscarCliente/"+ id, ClienteDTO.class);
		System.out.println("------------------------------"+"\n"
				+"Cliente Buscado");
		System.out.println(cliente.toString());
	}


	public void actualizarCliente( RestTemplate restTemplate){
		ClienteDTO cliente= new ClienteDTO();
		cliente.setDni("4");
		cliente.setNombre("Cliente Actualizado");
		restTemplate.put("http://localhost:8080/SpringRest/actualizarCliente",cliente,ClienteDTO.class);
		System.out.println("------------------------------"+"\n"
				+"Cliente Actualizado");
		buscarCliente(4,restTemplate);

	}

	public void eliminarCliente(int id, RestTemplate restTemplate){

		restTemplate.delete("http://localhost:8080/SpringRest/eliminarCliente/"+ id);
	}

	public void getAll(RestTemplate restTemplate){

		ResponseEntity<List<ClienteDTO>> clienteResponse =
				restTemplate.exchange("http://localhost:8080/SpringRest/traerTodos",
						HttpMethod.GET, null, new ParameterizedTypeReference<List<ClienteDTO>>() {
						});


		System.out.println(clienteResponse.getBody());


	}


}
