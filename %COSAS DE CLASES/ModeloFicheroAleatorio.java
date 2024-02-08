package BBDD;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MiFicheroRandom {
	
	private File fichero;
	private RandomAccessFile flujo;
	private int numRegistros;
	private long tamRegistro = 60; //long xq el m�todo length devuelve long
	
	public BBDD(File fichero) {
		this.fichero = fichero;
		
		//m�todo length
		//m�todo seek(long pos): se desplaza: flujo.seek(n�registro*tama�oRegistro)
		
		try {
			flujo = new RandomAccessFile(fichero,"rw"); //en modo lectura/escritura
			long numBytes = flujo.length();
			
			numRegistros = (int)Math.ceil(double)numBytes / (double)tamRegistro);
		}
		catch(IOException error) {
			System.out.println(error.getMessage());
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	//xa leer, lo primero es situarse en el registro. Le pasamos al m�todo la posici�n del registro
	public Registro leerRegistro(int posicion) {
		
		//las posiciones v�lidas ser�n mayor o igual a cero y menor q el numRegistros
		if(posicion >=0 && posicion < numRegistros) {
			
			try {
				//nos situamos en la posici�n:
				flujo.seek(posicion * tamRegistro);
				
				//devuelve lo q hay en el registro (como son 3 cadenas necesitamos 3 read:				
				return new Registro(flujo.readUTF(), flujo.readUTF(), flujo.readUTF());			
			}
			catch(IOException error) {
				System.out.println(error.getMessage());
				return null;
			}
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean escribirRegistro(int posicion, Registro nuevo) {
		
		//el m�todo a�adir llamar� a este m�todo
		
		if(posicion >= 0 && posicion <= numRegistros) {			
			try {				
				flujo.seek(posicion * tamRegistro);
				//escribimos:
				flujo.writeUTF(nuevo.getCodigo());
				flujo.writeUTF(nuevo.getNombre());
				flujo.writeUTF(nuevo.getPrecio());
				
				return true;
			}
			catch (IOException error){
				System.out.println(error.getMessage());
				return false;
			}
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////	
	public boolean anadirRegistro(Registro nuevo) {
		
		if (escribirRegistro(numRegistros, nuevo)) { //xa q a�ada al final 
			numRegistros++;
			return true;
		}
		return false;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////	
	public int buscar(String codigo) {
		
		//recorremos los registros:
		for (int i = 0; i < numRegistros; i++) {
			//creamos objeto de tipo Registro y lo leemos con el m�todo leer
			Registro registro = leerRegistro(i);
			//comparamos el codigo q le hemos pasado como par�metro con el codigo del objeto registro:
			if(codigo.equals(registro.getCodigo()))
				return i;
		}
		return -1;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	public void eliminar() {
		
		//xa borrarlo lo marcamos (ej con cadena vac�a
		//creamos un fichero aux, recorro la original y a�ado de la original lo q vale
		//metodo delete
		//metodo renamingTo xa cambiar nombre
		
		
		
	}
	
	
	
	

}
