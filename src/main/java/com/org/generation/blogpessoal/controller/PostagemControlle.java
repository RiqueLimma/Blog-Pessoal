package com.org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.org.generation.blogpessoal.model.Postagem;
import com.org.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemControlle {

	@Autowired
	private PostagemRepository repository;
	
	
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll (){
		return ResponseEntity.ok(repository.findAll()); // OK = 200
	}
	
	//Busca o id
	@GetMapping("ifelse/{id}")
	public ResponseEntity<Postagem> GetByIfElse(@PathVariable long id){
		
		Optional<Postagem> postagem = repository.findById(id);
		if(postagem.isPresent()) {
			return ResponseEntity.ok(postagem.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/titulo{titulo}/data/")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		repository.deleteById(id);
	
		// Continuação do codigo 28/06
		
		Optional<Postagem> postagem = repository.findById(id);
		if(postagem.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Postagem não encontrada", null);
		}
	}
	
	
}

