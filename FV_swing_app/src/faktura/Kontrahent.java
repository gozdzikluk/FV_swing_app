package faktura;

//klasa opisuj¹ca kontrahenta
//dane wystawczaj¹ do wystawienia faktury
//dla innych celów nale¿a³oby t¹ klasê rozbudowaæ
public class Kontrahent {
	String dane;
	String NIP;
	
	public Kontrahent(String dane, String NIP){
		this.dane = dane;
		this.NIP = NIP;
	}
	
	@Override
	public String toString(){
		return dane +"\n" + "NIP: "+ NIP;
	}

}
