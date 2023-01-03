<template>
  <v-app id="inspire">
    <v-main>
      <v-form
      ref="form"
      v-model="valid"
      lazy-validation>
      <v-container>
        <v-row
        class="mt-2 pb-2">
          <v-col >
            <h2 class="blue--text text--darken-4">Nova tarefa</h2>
          </v-col>
          <v-col align="right">
            <v-tooltip
            bottom
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="black"
                  dark
                  fab
                  small
                  v-bind="attrs"
                   v-on="on"
                  >
                    <v-icon dark>
                      mdi-help
                    </v-icon>
              </v-btn>
            </template>
            <span>Crie algumas tarefas e as edite em seguida</span>
          </v-tooltip>
          </v-col>
        </v-row>

        <v-divider></v-divider>

        <v-row class="mt-1">

          <v-col cols="6">
            <v-text-field
            v-model="newItem.titulo"
            label="Título"
            placeholder="Título"
            outlined
            :rules="tituloRules"
            required
          ></v-text-field>
          </v-col>

          <v-col cols="6">
            <v-text-field
            v-model="newItem.descricao"
            label="Descrição"
            placeholder="Descrição"
            outlined
            :rules="descricaoRules"
            required
          ></v-text-field>
          </v-col>

        </v-row>

        <v-row class="m-0">
          <v-col cols="6">
            <v-select
            :items="status"
            label="Status"
            v-model="newItem.status"
            outlined
            required
          ></v-select>
          </v-col>

        </v-row>

        <v-row >
          <v-col>
            <NuxtLink to="/">
              <v-btn>
                <v-icon left>
                  mdi-subdirectory-arrow-left
                </v-icon>
                Voltar
              </v-btn>
            </NuxtLink>
          </v-col>

          <v-col class=" text-right">
            <v-btn @click="validate" color="success">
              <v-icon left >
                mdi-floppy
              </v-icon>
              Salvar
            </v-btn>
          </v-col>
        </v-row>

      </v-container>
    </v-form>
    </v-main>
  </v-app>
</template>

<script>

  export default {

    data: () => ({
      valid: true,
      status: ['Aberta', 'Em andamento', 'Concluída'],
      tituloRules: [
        v => !!v || 'O campo título é obrigatório'
      ],
      descricaoRules: [
        v => !!v || 'O campo descrição é obrigatório'
      ],
      newItem: {
        titulo: '',
        descricao: '',
        dataCriacao: '',
        dataConclusao: '',
        status:'Aberta',
      },
    }),
    methods: {

      validate () {
        if(this.$refs.form.validate()){
          this.save();
        }
      },
      save(){
        let time = new Date();

        if(this.newItem.status === 'Concluída'){
          this.newItem.dataConclusao = time;
          this.newItem.dataCriacao = time;
        }else{
          this.newItem.dataCriacao = time;
        }

        this.$store.dispatch('tarefas/saveTarefa', this.newItem).then(()=>{
          this.$router.push({path: '/'});
        })
      }
    }
  }
</script>
