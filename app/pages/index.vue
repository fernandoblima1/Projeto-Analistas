<template>
  <v-app id="inspire">
    <v-main>
      <v-container>
        <v-row
        class="mt-2 pb-2">
          <v-col >
            <h2 class="blue--text text--darken-4">Tarefas</h2>
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

        <v-dialog
        v-model="dialog"
        max-width="500px"
      >
<!-- MODAL DE EDIÇÃO -->
        <v-card>
          <v-card-title>
            <span class="text-h5">Editar tarefa</span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col
                  cols="12"
                  sm="6"
                  md="4"
                >
                  <v-text-field
                    v-model="editedItem.titulo"
                    label="Título"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  sm="6"
                  md="4"
                >
                  <v-text-field
                    v-model="editedItem.descricao"
                    label="Descrição"
                  ></v-text-field>
                </v-col>
                <v-col cols="6">
                  <v-select
                  v-model="editedItem.status"
                  :items="status"
                  label="Status"
                  outlined
                ></v-select>
                </v-col>

              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="blue darken-1"
              text
              @click="close"
            >
              Cancel
            </v-btn>
            <v-btn
              color="blue darken-1"
              text
              @click="save"
            >
              Save
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

<!-- BOTÕES  -->
        <v-text-field
            class="mt-2"
            v-model="search"
            label="Título"
            placeholder="Título"
            outlined
            ref="search"
          ></v-text-field>
          <div
              class=" mb-3 text-right"
              >

              <v-btn
              color="warning"
              @click="deleteAll"
              >
                <v-icon left>
                  mdi-eraser
                </v-icon>
                Limpar
              </v-btn>
              <v-btn

              class="mx-1"
              color="primary"
              @click="searchFocus"
              >
                <v-icon left>
                  mdi-magnify
                </v-icon>
                Consultar
              </v-btn>

              <NuxtLink to="/newTask">
                <v-btn
                color="success"
                >
                  <v-icon left>
                    mdi-plus
                  </v-icon>
                  Nova tarefa
                </v-btn>
              </NuxtLink>

            </div>
              <v-data-table
                :headers="headers"
                :items="desserts"
                :search="search"
                sort-by="dataCriacao"
                class="elevation-1"
                >
                <template v-slot:top>
                    <v-dialog v-model="dialogDelete" max-width="480px">
                      <v-card class="py-4">
                        <v-card-title class="text-h5 justify-center px-3">Deseja realmente apagar este item?</v-card-title>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn color="grey" text @click="closeDelete">Cancelar</v-btn>
                          <v-btn color="error" text @click="deleteItemConfirm">Sim, apagar!</v-btn>
                          <v-spacer></v-spacer>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>

                    <v-dialog v-model="dialogDeleteAll" max-width="480px">
                      <v-card class="py-4">
                        <v-card-title class="text-h5 justify-center px-3">Deseja realmente apagar todos os itens?</v-card-title>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn color="grey" text @click="closeDeleteAll">Cancelar</v-btn>
                          <v-btn color="error" text @click="deleteAllItemsConfirm">Sim, apagar tudo!</v-btn>
                          <v-spacer></v-spacer>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                </template>

                <template v-slot:item.actions="{ item }">
                  <v-icon
                    small
                    class="mr-2"
                    @click="editItem(item)"
                  >
                    mdi-pencil
                  </v-icon>
                  <v-icon
                    small
                    @click="deleteItem(item)"
                  >
                    mdi-delete
                  </v-icon>
                </template>

              </v-data-table>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
  export default {

    async fetch(){
      this.tarefas = await this.$store.dispatch('tarefas/getTarefas')
      for(let tarefa of this.tarefas){
        this.desserts.push({
          id: tarefa.id,
          titulo: tarefa.titulo,
          descricao: tarefa.descricao,
          dataCriacao: this.getFormattedTime(tarefa.dataCriacao),
          dataConclusao: this.getFormattedTime(tarefa.dataConclusao),
          status: tarefa.status
        });
      }
    },

    data: () => ({
      search: '',
      show: false,
      tarefas: [],
      dialog: false,
      dialogDelete: false,
      dialogDeleteAll: false,
      headers: [
        {
          text: 'Título',
          align: 'start',
          sortable: false,
          value: 'titulo',
        },
        { text: 'Descrição', value: 'descricao' },
        { text: 'Data de criação', value: 'dataCriacao' },
        { text: 'Actions', value: 'actions', sortable: false, align: 'right' },
      ],
      desserts: [],
      editedIndex: -1,
      editedItemId: -1,
      editedItem: {
        id: '',
        titulo: '',
        descricao: '',
        dataCriacao: '',
        dataConclusao: '',
        status:'',
      },
      defaultItem: {
        id: '',
        titulo: '',
        descricao: '',
        dataCriacao: '',
        dataConclusao: '',
        status:'',
      },
      status: ['Aberta', 'Em andamento', 'Concluída'],
    }),

    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
      dialogDeleteAll (val) {
        val || this.closeDeleteAll()
      },
    },

    methods: {

      getFormattedTime(data){
        if(data !== null){
          let formattedTime = data.slice(8, 10) + '/' + data.slice(5, 7)  + '/' + data.slice(0, 4);
          return formattedTime;
        }else{
          return ''
        }
      },

      getFormattedTimeBack(data){
        let formattedTime = data.slice(6,10) + '/' + data.slice(3,5) + '/' +  data.slice(0,2)
        return formattedTime;
      },

      searchFocus(){
        this.$refs.search.focus();
      },

      editItem (item) {
        this.editedItemId = item.id
        this.editedIndex = this.desserts.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteAll(){
        this.dialogDeleteAll = true
      },

      deleteItem (item) {
        this.editedIndex = this.desserts.indexOf(item)
        this.editedItemId = item.id
        this.editedItem = Object.assign({}, item)
        this.dialogDelete = true
      },

      deleteItemConfirm () {
        this.$store.dispatch('tarefas/deleteTarefa', this.editedItemId)
        this.desserts.splice(this.editedIndex, 1)
        this.closeDelete()
      },

      deleteAllItemsConfirm(){
        this.$store.dispatch('tarefas/deleteAll');
        this.desserts = [];
        this.closeDeleteAll()
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
          this.editedItemId = -1
        })
      },

      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      closeDeleteAll(){
        this.dialogDeleteAll = false
      },

      save () {
        let editedItemTemporary = Object.assign({},this.editedItem);

        if(editedItemTemporary.status == 'Concluída'){
          let currentDate = new Date();

          editedItemTemporary.dataConclusao = currentDate;
        }else{
          editedItemTemporary.dataConclusao = '';
        }

        Object.assign(this.desserts[this.editedIndex], editedItemTemporary)

        editedItemTemporary.dataCriacao = new Date(this.getFormattedTimeBack(editedItemTemporary.dataCriacao));

        this.$store.dispatch('tarefas/updateTarefa', editedItemTemporary);

        this.close()
      },
    },
  }
</script>
