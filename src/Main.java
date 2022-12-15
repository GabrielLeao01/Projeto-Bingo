import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) {

        Random gerador = new Random();
        System.out.println("BEM VINDO AO BINGO!!");
        String []players=criarPLayers();
        System.out.println("Modo seleção Cartela");
        int modoCartela=modoJogo();
        System.out.println("Modo seleção Sorteio");
        int modoSorteio=modoJogo();
        int[][] cartelaScore = geraScore(players.length);
        int round = 1;
        int ganhador = 0;
        int acabar = 0;
        int[][] cartelas=new int[players.length][5];

        if(modoCartela==1){
            cartelas = geraCartelaManual(players);
        }
        else if(modoCartela==2){
            cartelas = geraCartelaRandom(players);

        }
        while(ganhador==0 && acabar ==0){

        if(modoSorteio==1){
            cartelaScore=sorteioManual(cartelas,cartelaScore);
        }
        else if(modoSorteio==2){
            cartelaScore=sorteioAleatorio(cartelas,cartelaScore);
        }
            acabar= imprimeCartela(round,cartelas,cartelaScore,players);
            ganhador = checaVencedor(cartelaScore,players);
            round++;
        }
        if(ganhador != 0) {
            System.out.println("PARABENS! O GANHADOR É O PLAYER " + players[ganhador]);
            System.out.printf("Cartela {");
            for (int j = 0; j < cartelas[ganhador].length; j++) {
                System.out.printf("%d,", cartelas[ganhador][j]);
            }
            System.out.println("}");
        }
    }
    private static int modoJogo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecione 1 para manual e 2 para automático: ");
        return sc.nextInt();
    }
    private static String[] criarPLayers(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nDigite os Nomes:  (Exemplo: jogador1-jogador2-jogador3)");
        String input = sc.nextLine();
        String[] nomes = input.split("-");
        System.out.println("\nJogadores: " + Arrays.toString(nomes));
        return nomes;
    }
    private static int[][] geraScore(int tam){
        int[][]scores = new int[tam][5];
        for(int i=0;i<tam;i++){
            for(int j=0;j<5;j++){
                scores[i][j]=0;
            }
        }
        return scores;
    }
    private static int[][] geraCartelaManual(String []players){
        Scanner sc = new Scanner(System.in);
        int cart[][] = new int[players.length][5];
        for(int i=0;i<cart.length;i++){
            System.out.printf("Player %d - %s\n",i+1,players[i]);
            System.out.println("Insira os números seguindo a sintaxe (1,2,3,4,5)");
            String input = sc.nextLine();
            String[] separaVirgula = input.split(",");
            for(int j=0;j<cart[i].length;j++)
            {
                cart[i][j] = Integer.parseInt(separaVirgula[j]);
            }
        }
        return cart;
    }
    private static int[][] geraCartelaRandom(String []players){
        Random rand = new Random();
        int n;
        int cart[][] = new int[players.length][5];
        int[] numAleat = {0,0,0,0,0};
        for(int i=0;i<cart.length;i++){
            for(int j=0;j<cart[i].length;j++)
            {
                n=rand.nextInt(61);
                while(verificaRepetido(n,numAleat)==1){
                    n=rand.nextInt(61);
                }
                numAleat[j]=n;
            }
            for (int x=0;x<cart[i].length;x++){
                cart[i][x]=numAleat[x];
            }
        }
        return cart;
    }
    public static int verificaRepetido(int n, int[] naleatorio){
        int i = 0;
        for(int j = 0; j < naleatorio.length; j++){
            if(naleatorio[j] == n){
                i = 1;
            }
        }
        return i;
    }

    private static int[][] sorteioManual(int[][] cartelas, int[][] cartelaScore){
        Scanner sc = new Scanner(System.in);
        System.out.println("Sorteie manualmente um numero para o bingo:");
        System.out.println("Insira os números seguindo a sintaxe (1,2,3,4,5)");
        String input = sc.nextLine();
        String[] separaVirgula = input.split(",");
        int num[] = new int[5];
        for(int i=0;i<num.length;i++){
            num[i] = Integer.parseInt(separaVirgula[i]);
        }
        for(int i=0;i<num.length;i++){
            for(int j=0;j<cartelas.length;j++) {
                for(int x=0;x<cartelas[j].length;x++)
                if(num[i]==cartelas[j][x]){
                    cartelaScore[j][x]=1;
                }
            }
        }
        return cartelaScore;
    }
    private static int[][] sorteioAleatorio(int[][] cartelas, int[][] cartelaScore){
        Random rand = new Random();
        int nAleatorio;
        int num[] = {0,0,0,0,0};
        for(int i=0;i<num.length;i++){
            nAleatorio=rand.nextInt(61);
            while(verificaRepetido(nAleatorio,num)==1){
                nAleatorio=rand.nextInt(61);
            }
            num[i]=nAleatorio;
        }
        for(int i=0;i<num.length;i++){
            for(int j=0;j<cartelas.length;j++) {
                for(int x=0;x<cartelas[j].length;x++)
                    if(num[i]==cartelas[j][x]){
                        cartelaScore[j][x]=1;
                    }
            }
        }
        System.out.printf("Numeros sorteados {");
        for(int i=0;i<num.length;i++){
            System.out.printf("%d,",num[i]);
        }
        System.out.printf("}\n");
        return cartelaScore;
    }

    private static int imprimeCartela(int round,int[][] cartelas, int[][] score, String[] players){
        Scanner sc = new Scanner(System.in);
        System.out.println("Round "+ round);
        for(int i=0;i<cartelas.length;i++){
            System.out.printf("%s ={",players[i]);
            for(int j=0;j<cartelas[i].length;j++)
            {
                System.out.printf("%d,",cartelas[i][j]);
            }
            System.out.printf("}\n");
        }
        System.out.println("///////////////////////////");
        System.out.println("SCORES: ");
        for(int i=0;i<cartelas.length;i++){
            System.out.printf("%s ={",players[i]);
            for(int j=0;j<cartelas[i].length;j++)
            {
                System.out.printf("%d,",score[i][j]);
            }
            System.out.printf("}\n");
        }
        System.out.println("Deseja continuar? Aperte X para abortar, pressione qualquer outra tecla para continuar");
        String pergunta = sc.next();
        if(pergunta.equals("X")||pergunta.equals("x")){
            return 1;
        }
        else{
            return 0;
        }
    }

    private static int checaVencedor(int [][]cartelaScore, String[] players){
        for(int i=0;i<cartelaScore.length;i++){
            int contador =0;
            for(int j=0;j<cartelaScore[i].length;j++){
                if(cartelaScore[i][j]==1){
                    contador++;
                }
            }
            if(contador==5){
                    return i;
                }
            }
        return 0;
        }
    }
