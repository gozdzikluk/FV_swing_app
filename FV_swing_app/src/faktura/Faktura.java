package faktura;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;





public class Faktura {
// ----------------------------------------------- POLA FAKTURY
	//dla kogo to jest faktura
	private Kontrahent klient;
	// kiedy zosta³a wystawiona
	private LocalDate dataWystawienia;
	
	
// ----------------------------------------------- IDENTYFIKATOR FAKTURY	
	// identyfikator, numer faktury
	private String nr;
	// klasa do sporz¹dzania identyfikatora: jest statyczna, bo nie potrzebuje obiektu Faktury by dzia³aæ
	private static class Identyfikator{
		// ma statyczne pola
		static int aktualnyNumer = 0;
		static int aktualnyMiesiac = 0;
		static int aktualnyRok = 0;
		
		// i metodê do generowania numeru
		// która dzia³a niezale¿nie od liczby ju¿ wystawionych faktur
		static String generateNr(){
			LocalDate ld = LocalDate.now();
			
			// aktualizacja roku
			int year = ld.getYear();
			int month = ld.getMonthValue();
			if(year > aktualnyRok){
				aktualnyRok = year;
				aktualnyMiesiac = ld.getMonthValue();
				aktualnyNumer = 0;
			}
			//aktualizacja miesi¹ca
			else if(month > aktualnyMiesiac){
				aktualnyMiesiac = month;
				aktualnyNumer = 0;
			}
			// zwracamy tekst sk³adaj¹cy siê z numeru dokumentu w danym miesi¹cu, miesi¹ca i roku
			return (++aktualnyNumer)+"/"+aktualnyMiesiac+"/"+aktualnyRok;
			
		}
	}
	
	
// ----------------------------------------------- POZYCJE FAKTURY	
	// typy wyliczeniowe potrzebne przy obs³udze pozycji fakturowej
	public enum Miara{SZT,M,L,KG,M2} // typ okreœlaj¹cy rodzaje jednostki
	public enum VAT{ // typ okreœlj¹cy wartoœci podatku
		s23(.23), s08(.08), s05(.05), s00(.0);
		// oprócz identyfikatora (np. s23) podajemy równie¿ jego odpowiednik typu double(stawka) by móc z niego korzystaæ przy wyznaczaniu wartoœci brutto
		// oraz wyznaczamy reprezentacjê znakow¹ tej stawki( w formie np 23%)
		public double stawka;
		public String str;
		VAT(double stawka){ // konstruktor ustawaj¹cy pola
			this.stawka = stawka;
			this.str = String.format("%.0f%%",100*stawka); // przeliczenie doubla na wartoœæ ca³kowit¹ ze znakiem %
		}
	}
	// prywatna klasa wewnêtrzna opisuj¹ca pojedyncz¹ pozycjê na fakturze
	private class Pozycja{
		private int pozycja;
		private String nazwa;
		private Miara miara;
		private double ilosc;
		private double cenaJednostkowaNetto;
		private VAT podatek;
		
		public Pozycja(String nazwa, Miara miara, double ilosc, double cena, VAT podatek) {
			this.nazwa = nazwa;
			this.miara = miara;
			this.ilosc = ilosc;
			this.cenaJednostkowaNetto = cena;
			this.podatek = podatek;
			this.pozycja = pozycje.size()+1; // klasa Pozycja nie mo¿e byæ statyczna poniewa¿ odwo³uje siê do niestatycznego pola klasy zewnêtrznej
		}
		public double getWartosc(){return ilosc*cenaJednostkowaNetto;}
		public double getPodatek(){
			double d = ilosc*cenaJednostkowaNetto*podatek.stawka; //tworzê obiekt d i ustawiam wartoœæ
			// zaokr¹glanie liczby do dwóch miejsc po przecinku
			d = Math.round(d*100)/100.;
			return d;
		}
		@Override
		public String toString(){
			return String.format("%5d | %30s | %10s | %10s | %10s | %10s | %10.2f",pozycja,nazwa,miara,ilosc,cenaJednostkowaNetto,podatek.str,getWartosc()+getPodatek());
		}
	}
	// metoda naglowek() nie mo¿e byæ (jako statyczna) w klasie Pozycja poniewa¿ klasa Pozycja nie jest statyczna
	// mo¿na by t¹ metodê wstawiæ do klasy Pozycja, ale jako niestatyczn¹ 
	// - niestety wtedy nie bêdziemy w stanie wydrukowaæ nag³ówka pustej faktury(w trakcie tworzenia)
	// poniewa¿ nie bêdzie ¿adnego obiektu typu Pozycja który by t¹ metodê wywo³a³.
	public static String naglowek(){
		return String.format("%5s | %30s | %10s | %10s | %10s | %10s | %10s","LP.","NAZWA","MIARA","ILOSC","CENA JEDN.","PODATEK","WARTOSC BRUTTO");
	}
	// pole zawieraj¹ce listê pozycji
	private ArrayList<Pozycja> pozycje = new ArrayList<Pozycja>();
	// metoda dodaj¹ca kolejn¹ pozycjê do faktury
	public void addPozycja(String nazwa,Miara m, double ilosc, double cena, VAT podatek ){
		if(zamknieta) return; // wyjdŸ jeœli faktury nie mo¿na edytowaæ
		
		Pozycja p = new Pozycja(nazwa, m, ilosc, cena, podatek);
		pozycje.add(p);
		// oblicznie podatku od tej pozycji i dodanie go do podsumy
		double d = podsumy.get(podatek);
		d += p.getPodatek();
		podsumy.put(podatek,d);
		// dodanie do sumy netto
		sumaNetto += p.getWartosc();
		// dodanie do sumy brutto
		sumaDoZaplaty += p.getWartosc()+ p.getPodatek();
	}
	
	public String getPozycja(int i) {
		return pozycje.get(i).toString();
	}
	
	public ArrayList getPozycje() {
		return pozycje;
	}
	
	
// ----------------------------------------------- DODATKOWE W£ASNOŒCI FAKTURY	
	
	// czy fakturê mo¿na jeszcze edytowaæ
	private boolean zamknieta = false;
	//metoda zamykaj¹ca fakturê, co zabrania j¹ edytowaæ
	public void zamknij(){
		zamknieta = true;
	}
	public boolean isZamknieta() {
		return zamknieta;
	}
	// sumy w poszczególnych stawkach
	private HashMap<VAT, Double> podsumy = new HashMap<>();
	private double sumaNetto = 0;
	private double sumaDoZaplaty = 0;
	
// ----------------------------------------------- KONSTUKTOR	
	
	public Faktura(Kontrahent k){
		nr = Identyfikator.generateNr();
		klient = k;
		dataWystawienia = LocalDate.now();
		
		//inicjowanie podsum stawkami VAT
		for(VAT v: VAT.values()){
			podsumy.put(v, 0.);
		}
	}
	
// ----------------------------------------------- WYPASANIE	
	
	
	public String getNr() {
		return nr;
		
	}
	@Override
	public String toString(){
		String faktura = "---------------------------------------------\n";
		faktura += (zamknieta?"Z-":"O-")+"Faktura nr."+ nr +"\n";
		faktura += "z dnia " + dataWystawienia.format(DateTimeFormatter.ofPattern("d-M-y")) + "\n";
		faktura += "\nDLA: \n"+klient +"\n\n";
		faktura += naglowek() +"\n"; 
		for(Pozycja p:pozycje){
			faktura += p.toString()+"\n";
		}
		faktura += "\n";
		faktura += String.format("%-15s: %10.2f\n", "SUMA NETTO", sumaNetto);
		for(HashMap.Entry<VAT,Double> e : podsumy.entrySet()){
			if(e.getValue()>0)
				faktura += String.format("%-15s: %10.2f\n", "SUMA "+e.getKey().str, e.getValue());
		}
		faktura += String.format("%27s\n", "+ ----------");
		faktura += String.format("%-15s: %10.2f\n", "SUMA DO ZAP£ATY", sumaDoZaplaty);
		faktura += "---------------------------------------------\n";
		return faktura;
	}

}

