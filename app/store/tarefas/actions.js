export default {
  getTarefas(){
    return this.$axios.$get('tarefas');
  },
  deleteTarefa(context, id){
    return this.$axios.$delete(`tarefas/${id}`)
  },
  deleteAll(){
    return this.$axios.$delete('tarefas')
  },
  updateTarefa(context, tarefa){
    return this.$axios.$put('tarefas', tarefa);
  },
  getTarefaPorId(context, id){
    return this.$axios.$get(`tarefas/${id}`);
  },
  saveTarefa(context, tarefa){
    return this.$axios.$post('tarefas', tarefa)
  }
}
