package faktura;



public class Test {

	public static void main(String[] args) {
		// tworzymy kontrahenta
		Kontrahent k1 = new Kontrahent("ABS sp. z o.o. \nul. Platna 12, 00-000 Warszawa", "123-123-123");
		
		// tworzymy faktury
		Faktura f1 = new Faktura(k1);
		Faktura f2 = new Faktura(k1);
		
		// dodajemy pozycje fo faktury:
		// nie interesuje nas wewnêtrzna reprezentacja faktury oprócz wewnêtrznych enum Miara i VAT
		f1.addPozycja("Kredki 3szt", Faktura.Miara.SZT, 5, 3.2, Faktura.VAT.s23 );
		f1.addPozycja("Flamastry 6szt", Faktura.Miara.SZT, 5, 4.59,Faktura.VAT.s23 );
		f1.addPozycja("Plastelina 12 kolorów", Faktura.Miara.SZT, 2, 8.22,Faktura.VAT.s23 );
		f1.addPozycja("O³ówki 3szt", Faktura.Miara.SZT, 1, 6.00,Faktura.VAT.s23 );
		f1.addPozycja("O³ówkek HB", Faktura.Miara.SZT, 5, 1.2, Faktura.VAT.s08 );
		
		System.out.println(f1);
		
		f2.addPozycja("Kredki 3szt", Faktura.Miara.SZT, 5, 3.2,Faktura.VAT.s23 );
		f2.addPozycja("Flamastry 6szt", Faktura.Miara.SZT, 5, 4.59,Faktura.VAT.s23 );
		f2.addPozycja("Plastelina 12 kolorów", Faktura.Miara.SZT, 2, 8.22,Faktura.VAT.s23 );
		f2.zamknij();
		f2.addPozycja("O³ówki 3szt", Faktura.Miara.SZT, 1, 6.00,Faktura.VAT.s23 );
		f2.addPozycja("O³ówkek HB", Faktura.Miara.SZT, 5, 1.2, Faktura.VAT.s08 );
		
		System.out.println(f2);
		System.out.println(Math.round(2022.0000000000002)/100.0);
	}

}
