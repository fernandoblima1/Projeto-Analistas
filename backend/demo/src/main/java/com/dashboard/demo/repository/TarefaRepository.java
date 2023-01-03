package com.dashboard.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.demo.entities.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
    
}
