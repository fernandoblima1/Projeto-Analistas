package com.dashboard.demo.controllers;

import java.util.List;
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

import com.dashboard.demo.entities.Tarefa;
import com.dashboard.demo.services.TarefaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/tarefas")
@Api(value="Endpoints para controle de tarefas")
@CrossOrigin(origins="*")
public class TarefasController{
    @Autowired
	private TarefaService tarefaService;

    @ApiOperation(value="Busca todas as tarefas do banco de dados")
    @GetMapping
    public List<Tarefa> findAll(){
        List<Tarefa> resultado = tarefaService.findAll();
        return resultado;
    }

    @ApiOperation(value="Deleta uma tarefa específica do banco, selecionando-a por meio do ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Tarefa tarefa = tarefaService.findById(id);
        
        tarefaService.delete(tarefa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @ApiOperation(value="Deleta todas as tarefas do banco de dados")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        tarefaService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value="Cadastra nova tarefa no banco")
    @PostMapping
    public void save(@RequestBody Tarefa tarefa){
        tarefaService.save(tarefa);
    }

    @ApiOperation(value="Atualiza tarefa quando editada, selecionando-a por meio do ID já existente")
    @PutMapping
    public ResponseEntity<Tarefa> update(@RequestBody Tarefa tarefa){
        return new ResponseEntity<>(tarefaService.save(tarefa), HttpStatus.OK);
    }
}
