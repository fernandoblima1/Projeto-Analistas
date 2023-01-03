package com.dashboard.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.demo.entities.Tarefa;
import com.dashboard.demo.repository.TarefaRepository;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tRepository;

    public void delete(Tarefa tarefa){
        tRepository.delete(tarefa);
    }

    public void deleteAll(){
        tRepository.deleteAll();
    }

    public Tarefa save(Tarefa tarefa){
        return tRepository.save(tarefa);
    }

    public List<Tarefa> findAll(){
        return tRepository.findAll();
    }

    public Tarefa findById(Long id){
        Optional<Tarefa> resultado = tRepository.findById(id);
        
        return resultado.get();
    }
}
