package br.com.gabrielle.service;

import br.com.gabrielle.dao.PessoaDAO;
import br.com.gabrielle.entities.Pessoa;

import java.util.List;

public class PessoaService {

    public void insert(Pessoa pessoa) {
        new PessoaDAO().insert(pessoa);

    }

    public List<Pessoa> findAll() {
        return new PessoaDAO().findAll();

    }

    public boolean update(Pessoa pessoa) {
        return new PessoaDAO().update(pessoa);

    }

    public boolean delete(Pessoa pessoa) {
        return new PessoaDAO().delete(pessoa);
    }


}

