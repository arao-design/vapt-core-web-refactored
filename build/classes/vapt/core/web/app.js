/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {

    // ==========================================
    // ELEMENTOS
    // ==========================================

    const timer = document.getElementById("timer");

    const currentBid = document.getElementById("currentBid");

    const currentBidBox =
        document.getElementById("currentBidBox");

    const bidInput =
        document.getElementById("bidInput");

    const bidButton =
        document.getElementById("bidButton");

    const bidCount =
        document.getElementById("bidCount");

    const auctionStatus =
        document.getElementById("auctionStatus");

    const minimumBid =
        document.getElementById("minimumBid");


    // ==========================================
    // CONFIGURAÇÕES
    // ==========================================

    let maiorLance = 4250.00;

    let tempoRestante = 2 * 60 + 30;

    let totalLances = 18;


    // ==========================================
    // FORMATAÇÃO DE MOEDA
    // ==========================================

    function formatarMoeda(valor) {

        return valor.toLocaleString("pt-BR", {
            style: "currency",
            currency: "BRL"
        });

    }


    // ==========================================
    // ATUALIZAÇÃO DO CRONÔMETRO
    // ==========================================

    function atualizarCronometro() {

        const minutos =
            Math.floor(tempoRestante / 60);

        const segundos =
            tempoRestante % 60;


        timer.textContent =
            String(minutos).padStart(2, "0") +
            ":" +
            String(segundos).padStart(2, "0");


        if (tempoRestante <= 0) {

            clearInterval(cronometro);

            timer.textContent = "00:00";

            auctionStatus.innerHTML =
                '<span class="status-dot"></span> ENCERRADO';

            auctionStatus.style.background = "#ef4444";

            bidButton.disabled = true;

            bidButton.innerHTML =
                '<i class="fa-solid fa-lock"></i> Leilão encerrado';

            bidInput.disabled = true;

            return;
        }

        tempoRestante--;

    }


    atualizarCronometro();


    const cronometro =
        setInterval(atualizarCronometro, 1000);


    // ==========================================
    // FORMATAÇÃO DO CAMPO DE LANCE
    // ==========================================

    bidInput.addEventListener("input", function () {

        let valor =
            this.value.replace(/\D/g, "");


        if (valor === "") {

            this.value = "";

            return;
        }


        valor =
            parseInt(valor, 10) / 100;


        this.value =
            formatarMoeda(valor);

    });


    // ==========================================
    // OBTER VALOR DIGITADO
    // ==========================================

    function obterValorInput() {

        let valor =
            bidInput.value
            .replace("R$", "")
            .replace(/\./g, "")
            .replace(",", ".")
            .trim();


        return parseFloat(valor);

    }


    // ==========================================
    // ANIMAÇÃO DO LANCE
    // ==========================================

    function animarNovoLance() {

        currentBidBox.classList.remove("bid-flash");


        // Força o navegador a reiniciar a animação
        void currentBidBox.offsetWidth;


        currentBidBox.classList.add("bid-flash");

    }


    // ==========================================
    // ATUALIZAR MAIOR LANCE
    // ==========================================

    function atualizarMaiorLance(novoLance) {

        maiorLance = novoLance;


        currentBid.textContent =
            formatarMoeda(maiorLance);


        minimumBid.textContent =
            formatarMoeda(maiorLance + 50);


        animarNovoLance();

    }


    // ==========================================
    // BOTÃO ENVIAR LANCE
    // ==========================================

    bidButton.addEventListener("click", function () {

        const valorLance =
            obterValorInput();


        // Campo vazio
        if (isNaN(valorLance)) {

            alert(
                "Digite o valor do seu lance."
            );

            bidInput.focus();

            return;
        }


        // Lance inválido
        if (valorLance <= maiorLance) {

            alert(
                "Seu lance precisa ser maior que " +
                formatarMoeda(maiorLance) +
                "."
            );

            bidInput.focus();

            return;
        }


        // Atualiza o lance
        atualizarMaiorLance(valorLance);


        // Incrementa quantidade de lances
        totalLances++;

        bidCount.textContent =
            totalLances;


        // Limpa o campo
        bidInput.value = "";


        // Feedback
        alert(
            "Lance realizado com sucesso!\n\n" +
            "Novo maior lance: " +
            formatarMoeda(valorLance)
        );

    });


    // ==========================================
    // ENTER NO CAMPO
    // ==========================================

    bidInput.addEventListener("keydown", function (event) {

        if (event.key === "Enter") {

            bidButton.click();

        }

    });


    // ==========================================
    // MENU MOBILE
    // ==========================================

    const menuMobile =
        document.getElementById("menuMobile");


    menuMobile.addEventListener("click", function () {

        alert(
            "Menu mobile: aqui você poderá adicionar " +
            "a navegação para as páginas do sistema."
        );

    });

});