/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cap7_Array;


public class IntArrayy {
    
    public static void main(String[] args){
        // A lista de inicializador especifica o valor inicial de cada elementoÿ
        int[] array = { 32, 27, 64, 18, 95, 14, 90, 70, 60, 37 }; 

        System.out.printf("%s%8s%n", "Index", "Value"); // títulos de coluna

        // gera saída do valor de cada elemento do array
        for (int counter = 0; counter < array.length; counter++){
        System.out.printf("%5d%8d%n", counter, array[counter]);

        } // fim da classe InitArray
    }
}
