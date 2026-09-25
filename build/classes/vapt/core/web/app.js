/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', () => {
    const formLance = document.getElementById('form-lance');
    const compradorInput = document.getElementById('compradorId');
    const valorInput = document.getElementById('valorLance');
    const termosCheckbox = document.getElementById('aceitaTermos');
    const maiorLanceSpan = document.getElementById('maior-lance');
    const mensagemBox = document.getElementById('mensagem-status');

    let maiorLanceAtual = 1000.00;

    formLance.addEventListener('submit', (e) => {
        e.preventDefault();

        const compradorId = compradorInput.value;
        const valorLance = parseFloat(valorInput.value);
        const aceitouTermos = termosCheckbox.checked;

        // Validação obrigatória dos Termos de Uso
        if (!aceitouTermos) {
            const mensagemTermos = 'ATENÇÃO: O comprador deve aceitar os termos antes de dar um lance.';
            alert(mensagemTermos); // Notificação em pop-up na tela
            exibirMensagem(mensagemTermos, false);
            return;
        }

        // Validação do valor do lance
        if (valorLance <= maiorLanceAtual) {
            const mensagemValor = `O valor do lance deve ser superior ao maior lance atual (R$ ${maiorLanceAtual.toFixed(2)}).`;
            alert(mensagemValor);
            exibirMensagem(mensagemValor, false);
            return;
        }

        // Sucesso ao registrar lance
        maiorLanceAtual = valorLance;
        maiorLanceSpan.textContent = maiorLanceAtual.toFixed(2);
        
        const mensagemSucesso = `Lance de R$ ${valorLance.toFixed(2)} registrado com sucesso para o comprador ID: ${compradorId}!`;
        alert(mensagemSucesso);
        exibirMensagem(mensagemSucesso, true);

        // Limpeza dos campos
        valorInput.value = '';
        termosCheckbox.checked = false;
    });

    function exibirMensagem(texto, sucesso) {
        mensagemBox.textContent = texto;
        mensagemBox.classList.remove('hidden', 'sucesso', 'erro');
        mensagemBox.classList.add(sucesso ? 'sucesso' : 'erro');
    }
});