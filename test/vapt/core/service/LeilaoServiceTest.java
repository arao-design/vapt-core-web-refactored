package vapt.core.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeilaoServiceTest {
    private LeilaoService leilaoService;

    @BeforeEach
    public void setUp() {
        leilaoService = new LeilaoService();
    }

    @Test
    @DisplayName("Deve calcular a taxa da plataforma VAPT de 5% corretamente")
    public void deveCalcularTaxaPlataformaCorretamente() {
        double valorLanceFinal = 1000.00;
        double taxaEsperada = 50.00; // 5% de 1000

        double taxaCalculada = leilaoService.calcularTaxaPlataforma(valorLanceFinal);

        assertEquals(taxaEsperada, taxaCalculada, 0.001, "A taxa da plataforma deve ser exatamente 5% do valor final.");
    }

    @Test
    @DisplayName("Deve calcular o valor liquido repassado ao vendedor corretamente")
    public void deveCalcularValorLiquidoVendedorCorretamente() {
        double valorLanceFinal = 500.00;
        double valorLiquidoEsperado = 475.00; // 500 - 25 (5%)

        double valorLiquidoCalculado = leilaoService.calcularValorLiquidoVendedor(valorLanceFinal);

        assertEquals(valorLiquidoEsperado, valorLiquidoCalculado, 0.001, "O valor liquido deve descontar a comissao de 5%.");
    }

    @Test
    @DisplayName("Deve lancar excecao ao tentar calcular taxa para valor de lance invalido")
    public void deveLancarExcecaoParaValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            leilaoService.calcularTaxaPlataforma(0.0);
        }, "Valores menores ou iguais a zero devem disparar IllegalArgumentException.");
    }
}

