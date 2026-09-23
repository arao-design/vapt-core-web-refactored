/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vapt.core.service;

import vapt.core.enums.StatusLeilao;
import vapt.core.enums.TransportadoraHomologada;
import vapt.core.model.Leilao;
import vapt.core.model.Rastreio;

public class LeilaoService {

    // Regra de Negócio Pura: Cálculo de taxa da plataforma VAPT (5% sobre o lance final)
    public double calcularTaxaPlataforma(double valorLanceFinal) {
        if (valorLanceFinal <= 0) {
            throw new IllegalArgumentException("O valor do lance final deve ser maior que zero.");
        }
        return valorLanceFinal * 0.05; // 5% de comissão VAPT
    }

    // Regra de Negócio Pura: Cálculo do valor líquido a receber pelo vendedor
    public double calcularValorLiquidoVendedor(double valorLanceFinal) {
        double taxa = calcularTaxaPlataforma(valorLanceFinal);
        return valorLanceFinal - taxa;
    }

    // Regra do Comprador: Novo lance precisa ser estritamente maior que o atual
    public void registrarLance(Leilao leilao, Long compradorId, double valorLance, boolean aceitouTermos) {
        if (!aceitouTermos) {
            throw new IllegalArgumentException("O comprador deve aceitar os termos antes de dar um lance.");
        }
        if (leilao.getStatus() != StatusLeilao.ATIVO) {
            throw new IllegalStateException("Leilão encerrado para novos lances.");
        }
        if (valorLance <= leilao.getMaiorLance()) {
            throw new IllegalArgumentException("O valor do lance deve ser superior ao maior lance atual.");
        }

        leilao.setMaiorLance(valorLance);
        leilao.setCompradorVencedorId(compradorId);
    }

    // Regra do Pós-Leilão: Validação do Chat pelo Comprador Vencedor
    public void confirmarValidacaoChat(Leilao leilao, Long compradorId, boolean tirouDuvidas) {
        if (!compradorId.equals(leilao.getCompradorVencedorId())) {
            throw new SecurityException("Apenas o comprador vencedor pode validar o leilão no chat.");
        }
        if (tirouDuvidas) {
            leilao.setStatus(StatusLeilao.AGUARDANDO_ENVIO);
        } else {
            leilao.setStatus(StatusLeilao.EM_VALIDACAO_CHAT);
        }
    }

    // Regra do Leiloador: Inserção obrigatória de Tag/Transportadora Homologada
    public void registrarEnvio(Leilao leilao, Long vendedorId, TransportadoraHomologada transportadora, String codigoRastreio) {
        if (!vendedorId.equals(leilao.getVendedorId())) {
            throw new SecurityException("Apenas o leiloador responsável pode registrar o envio.");
        }
        if (leilao.getStatus() != StatusLeilao.AGUARDANDO_ENVIO) {
            throw new IllegalStateException("O comprador ainda não confirmou a liberação no chat.");
        }
        if (codigoRastreio == null || codigoRastreio.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio inválido.");
        }

        leilao.setRastreio(new Rastreio(transportadora, codigoRastreio));
        leilao.setStatus(StatusLeilao.ENVIADO);
    }
}
