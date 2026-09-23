/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vapt.core.controller;

import vapt.core.model.Leilao;
import vapt.core.service.LeilaoService;
import vapt.core.repository.LeilaoRepository;
import java.util.List;

public class LeilaoController {

    private final LeilaoRepository repository;
    private final LeilaoService service;

    public LeilaoController() {
        this.repository = new LeilaoRepository();
        this.service = new LeilaoService();
    }

    // Listar todos os leilões
    public List<Leilao> listarTodos() {
        return repository.findAll();
    }

    // Buscar leilão por ID
    public Leilao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Registrar um novo lance chamando as regras de negócio
    public boolean registrarLance(Leilao leilao, Long compradorId, double valor, boolean aceitouTermos) {
        try {
            service.registrarLance(leilao, compradorId, valor, aceitouTermos);
            repository.save(leilao);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao registrar lance: " + e.getMessage());
            return false;
        }
    }
}
