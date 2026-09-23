/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vapt.core.repository;

import vapt.core.model.Leilao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeilaoRepository {

    private final List<Leilao> leiloes = new ArrayList<>();

    public Leilao save(Leilao leilao) {
        leiloes.add(leilao);
        return leilao;
    }

    public List<Leilao> findAll() {
        return leiloes;
    }

    public Optional<Leilao> findById(Long id) {
        return leiloes.stream().filter(l -> l.getId().equals(id)).findFirst();
    }
}
