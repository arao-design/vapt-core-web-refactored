package vapt.core.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.Test;
import static org.junit.Assert.*;
import vapt.core.model.Leilao;

public class LeilaoServiceTest {

    @Test
    public void testRegistrarLanceValido() {
        LeilaoService service = new LeilaoService();
        Leilao leilao = new Leilao(1L, "Notebook", 1000.0, 101L);

        service.registrarLance(leilao, 102L, 1200.0, true);

        assertEquals(1200.0, leilao.getMaiorLance(), 0.001);
        assertEquals(Long.valueOf(102L), leilao.getCompradorVencedorId());
    }

    @Test
    public void testRegistrarLanceInvalidoSemTermos() {
        LeilaoService service = new LeilaoService();
        Leilao leilao = new Leilao(1L, "Notebook", 1000.0, 101L);

        try {
            service.registrarLance(leilao, 102L, 1200.0, false);
            fail("Deveria ter lançado exceção pois não aceitou os termos");
        } catch (IllegalArgumentException e) {
            assertEquals("O comprador deve aceitar os termos antes de dar um lance.", e.getMessage());
        }
    }
}
