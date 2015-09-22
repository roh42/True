package roh.funfacts;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roh on 05/04/2015.
 */
public class FactBook {


    Random randomGenerator = new Random();
    public int randomNumber = 0;
    public ArrayList<Integer> rdmNumber = new ArrayList();


   /**
    public String [] mfacts = new String[]{
            "Existem 7 bilhões de pessoas no mundo, e nenhuma pediu sua opinião.",
            "O mundo da voltas, queridinha...",
            "Eu não sou obrigada.",
            "Objeto: Tidal. Reação: Caguei.",
            "I give 0 fucks to your opinion.",
            "Eu não tenho paciência pra quem ta começando.",
            "Deus não se importa se você da o cu/buceta.",
            "Tenho mais o que fazer do que escutar essa merda.",
            "Beatles não inventaram o Rock.",
            "A cena pop não é uma monarquia, Madonna não é rainha, Britney não é princesa.",
            "Opinião é igual cu: tem gente que usa pra cagar tudo por onde passa.",
            "Fala que arte contemporânea é uma merda, mas ta la lotando a fila da exposição.",
            "Gisele esta cada vez mais rica. Ela é poderosíssima.",
            "Trixie não deveria ter saido.",
            "Só vale chamar o coleguinha de alienado por assistir novela se você tiver lido um livro inteiro de filosofia hoje.", //twitter
            "É preciso amar as pessoas como se não houvesse as pessoas.", // twitter
            "Azul e preto",
            "Branco e dourado",
            "Nínguem se importa.",
            "Se você acha Iggy Azaela boa, sai agora do meu aplicativo e da minha vida.",
            "Para, só para.",
            "Você não ta no Power Rangers, você não tem inimigas.",
            "Boyhood – nada acontece, igualzinho a sua vida.", //Bomdiaporque
            "Da o cu que é bom, nada.",
            "Honey, don't",
            "3 pessoas não é meeting.",
            "Stop trying to make your life happen. It's not gonna happen.",
            "Do you even go here?.",
            "aham, ta ta.",
            "\" Eu sou bonzinho(a), mas quando fico bravo... sai da frente\". Nossa, que pessoa diferente e única você é, ninguém mais no mundo é assim, parabéns.",
            "No Destiny Child que é a vida, você é a Michelle.",
            "Inveja eu tenho é da Beyoncé.",
            "Bebe duas Smirnoff Ice e ja acha que ta \"Causando na balada\".",
            "Você é muito bonitx quando não tem ninguém olhando pra sua cara.",
            "A vida é uma calcinha enfiada no cu.",
            "Você não é melhor do que ninguém por gostar de rock.",
            "Você não é melhor do que ninguém por gostar de jazz.",
            "Você não é melhor do que ninguém por gostar de mpb.",
            "Existe vida além da sua diva do pop.",
            "Você não é inteligente, você simplesmente tem acesso a internet.",
            "Sabe aquela coisa embaraçosa que você fez aquela vez? Todo mundo ainda se lembra.",
            "Pare de gostar da sua banda preferida caso ela fique famosa.",
            "Ela não é obrigada a gostar de você.",
            "Você é a apoteose da frase \"Ninguém é perfeito\"."


    };
*/

    public String getFact(String[] mfacts){

        String fact = "";
        generateRandomNumber(mfacts);

        fact = mfacts[randomNumber];

        return fact;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public int generateRandomNumber(String[] mfacts){
        randomNumber = randomGenerator.nextInt(mfacts.length);
        if (rdmNumber.contains(randomNumber)){
            generateRandomNumber(mfacts);
        } else {
            storeRdmNumber(randomNumber);
        }

        if (rdmNumber.size() == mfacts.length){
            rdmNumber.removeAll(rdmNumber);
        }
        return randomNumber;
    }

    public void storeRdmNumber (int randomNumber) {
        rdmNumber.add(randomNumber);

    }
}
