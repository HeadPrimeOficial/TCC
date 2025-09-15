package com.example.tcc.cadastro_model;

import jakarta.persistence.*;



    @Entity
    @Table(name = "cadastro")
    public class CadastroModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nome;
        private String email;
        private String telefone;

        // construtor vazio (obrigatório pro JPA)
        public CadastroModel() {}

        // construtor completo
        public CadastroModel(String nome, String email, String telefone) {
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
        }

        // getters e setters
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefone() {
            return telefone;
        }
        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }
    }


