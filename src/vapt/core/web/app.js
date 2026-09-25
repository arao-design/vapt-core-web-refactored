/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', () => {
    // Elementos do formulário
    const formLance = document.getElementById('form-lance');
    const compradorInput = document.getElementById('compradorId');
    const valorInput = document.getElementById('valorLance');
    const termosCheckbox = document.getElementById('aceitaTermos');
    const maiorLanceSpan = document.getElementById('maior-lance');
    const mensagemBox = document.getElementById('mensagem-status');

    // Elementos do Modal de Termos
    const modal = document.getElementById('modal-termos');
    const linkTermos = document.getElementById('link-termos');
    const fecharModal = document.getElementById('fechar-modal');
    const btnEntendi = document.getElementById('btn-entendi');

    let maiorLanceAtual = 1000.00;

    // Funções para abrir/fechar o Modal
    const abrirModal = (e) => {
        if (e) e.preventDefault();
        modal.classList.remove('hidden');
    };

    const ocultarModal = () => {
        modal.classList.add('hidden');
    };

    // Eventos do Modal
    linkTermos.addEventListener('click', abrirModal);
    fecharModal.addEventListener('click', ocultarModal);
    
    // Ao clicar em Concordar, aceita os termos e fecha o modal
    btnEntendi.addEventListener('click', () => {
        termosCheckbox.checked = true;
        ocultarModal();
    });

    // Submissão do formulário
    formLance.addEventListener('submit', (e) => {
        e.preventDefault();

        const compradorId = compradorInput.value;
        const valorLance = parseFloat(valorInput.value);
        const aceitouTermos = termosCheckbox.checked;

        // Validação dos Termos de Uso
        if (!aceitouTermos) {
            const mensagem = 'O comprador deve aceitar os termos antes de dar um lance.';
            exibirMensagem(mensagem, false);
            abrirModal();
            return;
        }

        // Validação do valor do lance
        if (valorLance <= maiorLanceAtual) {
            const mensagem = `O valor do lance deve ser maior que o atual (R$ ${maiorLanceAtual.toFixed(2)}).`;
            exibirMensagem(mensagem, false);
            return;
        }

        // Sucesso
        maiorLanceAtual = valorLance;
        maiorLanceSpan.textContent = maiorLanceAtual.toFixed(2);
        
        const mensagemSucesso = `Lance de R$ ${valorLance.toFixed(2)} registrado com sucesso para o comprador ${compradorId}!`;
        exibirMensagem(mensagemSucesso, true);

        valorInput.value = '';
        termosCheckbox.checked = false;
    });

    function exibirMensagem(texto, sucesso) {
        mensagemBox.textContent = texto;
        mensagemBox.classList.remove('hidden', 'sucesso', 'erro');
        mensagemBox.classList.add(sucesso ? 'sucesso' : 'erro');
    }
});