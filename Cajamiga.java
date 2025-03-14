/**
 * @title Cajamiga.java
 * @Descripcion: Programa para generar informe de efectivo
 * @author Alejandro Farre P.
 * @version 1.0 12 de Marzo de 2025
 */
package cajamiga;

//import com.ibm.OS4690.TerminalApplicationServices;
//import com.ibm.OS4690.TerminalStatusData;
//import com.ibm.OS4690.ControllerApplicationServices;
//import com.ibm.OS4690.ControllerStatusData;

public class Cajamiga {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      String centro, terminal;
      int i;

      //if(ControllerApplicationServices.isController()) {
      //   System.out.println("es un controlador");
         //ControllerStatusData contro;
         //contro = 
         //System.out.println("tienda " + contro.getStoreNumber());
      //}

      //if(TerminalApplicationServices.isTerminal()) {
      //   System.out.println("es un terminal");
         //TerminalStatusData termi;
         //byte datos[] = new byte[64];
         //termi = new TerminalStatusData(datos);
         //System.out.println("ambiente " + termi.getApplicationEnvironment());
         //System.out.println("partner terminal Address " + termi.getPartnerTerminalAddress());
         //System.out.println("numero tienda " + termi.getStoreNumber());
         //System.out.println("tipo de terminal " + termi.getTerminalType());
      //}

      i = 0;
      if(args.length > i) {
         centro = args[i++];
      } else {
         centro = "001";
      }

      if(args.length > i) {
         terminal = args[i++];
      } else {
         terminal = "001";
      }

      Efectivo efe = new Efectivo(centro, terminal);
      efe.defineAB(0, 25);
      efe.defineAB(1, 20);
      efe.defineAB(2, 15);
      efe.defineAB(3, 10);
      efe.defineAB(4, 8);
      efe.defineAB(5, 6);
      efe.defineAB(6, 0);
      efe.generaXML();

   }

}
