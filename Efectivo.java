/**
 * @title Efectivo.java
 * @Descripcion: Efectivo en el dispensador de dinero
 * @author Alejandro Farre P.
 * @version 1.0 12 de Marzo de 2025
 */
package cajamiga;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;


public class Efectivo {
   String va[];
   int AB[], EB[], RB[];
   int AM[], EM[], RM[], EX[];
   private final String etXML = "<?xml version=\"1.0\"?>";
   private final String etGES = "GestionSCO";
   private final String etDAT = "DatosSCO";
   private final String etEFE = "DatosEfectivoSCO";
   private final String etVA[] = {
      "NumeroCentro",
      "NumeroCaja",
      "IPTerminal",
      "NombreTerminal",
      "TipoTerminal",
      "NumeroSerieTerminal",
      "VersionSoftware"
   };
   private final String etDI[] = {
      "AceptadorBilletes",
      "ExpendedorBilletes",
      "RecicladorBilletes",
      "AceptadorMonedas",
      "ExpendedorMonedas",
      "RecicladorMonedas",
      "ExcesoMonedas"
   };
   private final String etBI[] = {"5E", "10E", "20E", "50E", "100E", "200E", "500E"};
   private final String etMO[] = {"1c", "2c", "5c", "10c", "20c", "50c", "1E", "2E"};
   private final String etER[] = {
      "DatosError",
      "ErrorGeneral",
      "NumeroError",
      "DescripcionError"
   };
   String erGE, nuER, deER;

   /**
    * Constructor de la clase
    * @param centro numero de centro
    * @param caja numero de terminal
    */
   public Efectivo(String centro, String caja) {
      va = new String[7];
      AB = new int[7];
      EB = new int[7];
      RB = new int[7];
      AM = new int[8];
      EM = new int[8];
      RM = new int[8];
      EX = new int[8];
      //NumeroCentro, NumeroCaja, IPTerminal, NombreTerminal, TipoTerminal, NumeroSerieTerminal, VersionSoftware;
      for(int i = 0; i < 7; i++) va[i] = "";
      va[0] = formateaIzq(centro, 3);
      va[1] = formateaIzq(caja, 3);
      erGE = "";
      nuER = "";
      deER = "";
   }

   /**
    * define la cantidad de billetes para cada denominacion
    *        del aceptador de billetes
    * @param indice indice a la denominacion del billete
    * @param cantidad cantidad de billetes
    */
   public void defineAB(int indice, int cantidad) {
      AB[indice] = cantidad;
   }

   /**
    * define la cantidad de billetes para cada denominacion
    *        del expendedor de billetes
    * @param indice indice a la denominacion del billete
    * @param cantidad cantidad de billetes
    */
   public void defineEB(int indice, int cantidad) {
      if(indice < 0 || indice > 6) return;
      EB[indice] = cantidad;
   }

   /**
    * define la cantidad de billetes para cada denominacion
    *        del reciclador de billetes
    * @param indice indice a la denominacion del billete
    * @param cantidad cantidad de billetes
    */
   public void defineRB(int indice, int cantidad) {
      if(indice < 0 || indice > 6) return;
      RB[indice] = cantidad;
   }

   /**
    * define la cantidad de monedas para cada denominacion
    *       del aceptador de monedas
    * @param indice indice a la denominacion de la moneda
    * @param cantidad cantidad de monedas
    */
   public void defineAM(int indice, int cantidad) {
      if(indice < 0 || indice > 6) return;
      AM[indice] = cantidad;
   }

   /**
    * define la cantidad de monedas para cada denominacion
    *       del expendedor de monedas
    * @param indice indice a la denominacion de la moneda
    * @param cantidad cantidad de monedas
    */
   public void defineEM(int indice, int cantidad) {
      if(indice < 0 || indice > 7) return;
      EM[indice] = cantidad;
   }

   /**
    * define la cantidad de monedas para cada denominacion
    *       del reciclador de monedas
    * @param indice indice a la denominacion de la moneda
    * @param cantidad cantidad de monedas
    */
   public void defineRM(int indice, int cantidad) {
      if(indice < 0 || indice > 7) return;
      RM[indice] = cantidad;
   }

   /**
    * define la cantidad de monedas para cada denominacion
    *       del exceso de monedas
    * @param indice indice a la denominacion de la moneda
    * @param cantidad cantidad de monedas
    */
   public void defineEX(int indice, int cantidad) {
      if(indice < 0 || indice > 7) return;
      EX[indice] = cantidad;
   }

   /**
    * Genera archivo XML
    */
   public void generaXML() {
      BufferedWriter archivo;
      String nomArc;
      int i;

      nomArc = va[0] + va[1] + nombreHoy() + ".XML";

      try {
         archivo = new BufferedWriter(new FileWriter(nomArc));
         archivo.write(etXML);
         archivo.newLine();

         //inicia etiqueta GestionSCO
         graba(archivo, etGES, "", 0, 1);

         //inicia etiqueta DatosSCO
         graba(archivo, etDAT, "", 3, 1);

         //incorpora datos
         for(i = 0; i < 7; i++) {
            graba(archivo, etVA[i], va[i], 6, 3);
         }

         //finaliza etiqueta DatosSCO
         graba(archivo, etDAT, "", 3, 2);

         //inicia etiqueta DatosEfectivo
         graba(archivo, etEFE, "", 3, 1);

         //incorpora fecha y hora
         graba(archivo, "FechaHora", fechaHoy(), 6, 3);

         //AceptadorBilletes
         graba(archivo, etDI[0], "", 6, 1);
         for(i = 0; i < 7; i++) {
            graba(archivo, etBI[i], String.valueOf(AB[i]), 9, 3);
         }
         graba(archivo, etDI[0], "", 6, 2);

         //ExpendedorBilletes
         graba(archivo, etDI[1], "", 6, 1);
         for(i = 0; i < 7; i++) {
            graba(archivo, etBI[i], String.valueOf(EB[i]), 9, 3);
         }
         graba(archivo, etDI[1], "", 6, 2);

         //RecicladorBilletes
         graba(archivo, etDI[2], "", 6, 1);
         for(i = 0; i < 7; i++) {
            graba(archivo, etBI[i], String.valueOf(RB[i]), 9, 3);
         }
         graba(archivo, etDI[2], "", 6, 2);

         //AceptadorMonedas
         graba(archivo, etDI[3], "", 6, 1);
         for(i = 0; i < 8; i++) {
            graba(archivo, etMO[i], String.valueOf(AM[i]), 9, 3);
         }
         graba(archivo, etDI[3], "", 6, 2);

         //ExpendedorMonedas
         graba(archivo, etDI[4], "", 6, 1);
         for(i = 0; i < 8; i++) {
            graba(archivo, etMO[i], String.valueOf(EM[i]), 9, 3);
         }
         graba(archivo, etDI[4], "", 6, 2);

         //RecicladorMonedas
         graba(archivo, etDI[5], "", 6, 1);
         for(i = 0; i < 8; i++) {
            graba(archivo, etMO[i], String.valueOf(RM[i]), 9, 3);
         }
         graba(archivo, etDI[5], "", 6, 2);

         //ExcesoMonedas
         graba(archivo, etDI[6], "", 6, 1);
         for(i = 0; i < 8; i++) {
            graba(archivo, etMO[i], String.valueOf(EX[i]), 9, 3);
         }
         graba(archivo, etDI[6], "", 6, 2);

         //finaliza etiqueta DatosEfectivo
         graba(archivo, etEFE, "", 3, 2);

         //Datos error
         graba(archivo, etER[0], "", 3, 1);
         graba(archivo, etER[1], erGE, 6, 3);
         graba(archivo, etER[2], nuER, 6, 3);
         graba(archivo, etER[3], deER, 6, 3);
         graba(archivo, etER[0], "", 3, 2);

         //finaliza etiqueta GestionSCO
         graba(archivo, etGES, "", 0, 2);

         archivo.close();

      } catch(java.io.FileNotFoundException fnfex) {
         dialogoError(fnfex.getMessage());
      } catch(java.io.IOException ioex) {
         dialogoError(ioex.getMessage());
      }
   }

   /**
    * Muestra un dialogo de error
    * @param mensaje mensaje a poner en el dialogo
    */
   public void dialogoError(String mensaje) {
      JOptionPane.showMessageDialog(new javax.swing.JFrame() ,mensaje, "Error", JOptionPane.ERROR_MESSAGE);
   }

   /**
    * Obtiene la fecha y hora para el nombre de archivo
    * @return String con la fecha y hora
    */
   private String nombreHoy() {
      SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd-hhmmss");
      Date fechaDate = new Date();
      String f = formateador.format( fechaDate );
      return(f);
   }

   /**
    * Obtiene la fecha y hora para incluir en el archivo XML
    * @return String con la fecha y hora
    */
   private String fechaHoy() {
      SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
      Date fechaDate = new Date();
      String f = formateador.format( fechaDate );
      return(f);
   }

   /**
    * Formatea un numero con ceros a la izquierda
    * @param texto numero a formatear
    * @param num numero total de digitos
    * @return numero formateado con ceros a la izquierda
    */
   private String formateaIzq(String texto, int num) {
      String r;
      int k;
      k = texto.length();
      if(k >= num) {
         r = texto.substring(k - num, k);
      } else {
         r = formateaC(texto, num - k);
      }
      
      return r;
   }

   /**
    * formatea con ceros a la izquierda
    * @param s String a formatear
    * @param n numero de caracteres
    * @return String formateado con n ceros a la izquierda
    */
   private String formateaC(String s, int n) {
      String formato = "%0" + Integer.toString(n) + "s";
      return String.format(formato, s);
   }

   /**
    * formatea con blancos a la izquierda
    * @param s String a formatear
    * @param n numero de caracteres
    * @return String formateado con n blancos a la izquierda
    */
   private String formateaB(String s, int n) {
      String formato = "%" + Integer.toString(n) + "s";
      return String.format(formato, s);
   }

   private String blancos(int n) {
      String s = "";
      for(int i = 0; i < n; i++) s += " ";
      return(s);
   }

   /**
    * escribe una etiqueta XML 
    * @param arc manejador del archivo
    * @param tag etiqueta
    * @param valor valor
    * @param esp numero de espacios antes de la etiqueta
    * @param tipo 1 escribe solo la etiqueta de inicio
    *             2 escribe solo la etiqueta de fin 
    *             3 escribe etiqueta de inicio, el valor y la etiqueta de fin
    * @throws java.io.IOException 
    */
   private void graba(BufferedWriter arc, String tag, String valor, int esp, int tipo) throws java.io.IOException {
      String linea;
      
      linea = "<";
      if(tipo == 2) {
         linea ="</";
      }
      if(esp > 0) {
         linea = blancos(esp) + linea;
      }
      linea = linea + tag + ">";
      if(tipo == 3) {
         linea += valor + "</" + tag + ">";
      }
      arc.write(linea);
      arc.newLine();
   }

/*
   public void getDatos() {

      //creamos las variables que se van a utilizar teniendo en cuenta
      //su dato primitivo (String, int, float, etc...)
      String var1;
      int var2;
      float var3;

      //encerramos el bloque del codigo en un try/catch para manejar las excepciones
      try {
         // Realizamos la conexion a la BD mediante un método especifico para ello.
         Conectar();

         //Creamos el Statement y el ResulSet necesarios para leer y guardar la informacion de la tabla
         Statement stm = conn.createStatement(); //donde "conn" es una variable del tipo "Connection".
         ResultSet rs = stm.executeQuery("SELECT * FROM DATOS");

         //Toda la información está contenida en el ResulSet, ahora procedemos a pasar la info a el ArrayList
         // nos ubicamos en la primera posición del ResultSet
         rs.first();

         var1 = rs.getString(1); //primer dato (observa los datos primitivos)
         var2 = rs.getInt(2); //segundo dato
         var3 = rs.getFloat(3); //tercer dato

         //manejamos la excepcion del tipo "SQLException"
      } catch (SQLException ex) {
         System.out.println("Error: " + ex);

      } finally {

         //cerramos la conexión a la BD
         Desconectar();
      }
   }
*/

}
