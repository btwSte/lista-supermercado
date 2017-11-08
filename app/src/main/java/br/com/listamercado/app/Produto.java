package br.com.listamercado.app;

import com.orm.SugarRecord;

public class Produto extends SugarRecord {
    private String nome;
    private boolean ativo;

    String teste;

    //Construtor obrigatorio por causa do ORM
    public Produto(){}

    public Produto(String nome, boolean ativo){
        this.nome = nome;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
