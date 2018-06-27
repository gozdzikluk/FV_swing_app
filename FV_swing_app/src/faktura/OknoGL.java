package faktura;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import faktura.Faktura.Miara;
import faktura.Faktura.VAT;

public class OknoGL extends JFrame{
	private JList<String> lista;
	private DefaultListModel<String> listModel;
	public JFrame fakturowanie;
	private NowaFV nowaFV;
	private Wydruk wydruk;
	private Edycja edycja;
	private Kontrahent kontrahent;
	private VAT podatekv;
	private Miara miara;
	private Faktura faktura;
	private List<Faktura> faktury = new LinkedList<>();
	
	;
	
	public static void main(String[] args) {
		new OknoGL();
	}
	
	public OknoGL() {
		
		fakturowanie = new JFrame();
		fakturowanie.setTitle("Fakturowanie 1.0");
		fakturowanie.getContentPane().setLayout(null);
		
		
		JButton nowaFV = new JButton("Nowa FV");
		nowaFV.setBounds(10, 11, 115, 23);
		fakturowanie.getContentPane().add(nowaFV);
		
		JButton usun = new JButton("Usuñ");
		usun.setBounds(10, 45, 115, 23);
		usun.setEnabled(false);
		fakturowanie.getContentPane().add(usun);
		
		JButton drukuj = new JButton("Drukuj");
		drukuj.setBounds(10, 79, 115, 23);
		drukuj.setEnabled(false);
		fakturowanie.getContentPane().add(drukuj);
		
		JButton edytuj = new JButton("Edytuj");
		edytuj.setBounds(10, 113, 115, 23);
		edytuj.setEnabled(false);
		fakturowanie.getContentPane().add(edytuj);
		
		listModel = new DefaultListModel<String>();
		
		lista = new JList<String>(listModel);
		lista.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lista.setLayoutOrientation(JList.VERTICAL);
		lista.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(lista);
		listScroller.setPreferredSize(new Dimension(120, 80));
		listScroller.setBounds(138, 5, 285, 138);
		listScroller.setVisible(true);
		
		fakturowanie.getContentPane().add(listScroller);
		
		 
		lista.addListSelectionListener(new ListSelectionListener() {
										@Override
										public void valueChanged(ListSelectionEvent arg0) {
											if(!listModel.isEmpty()) {
											drukuj.setEnabled(true);
											edytuj.setEnabled(true);
											usun.setEnabled(true);
											}
											else if (listModel.isEmpty()) {
												drukuj.setEnabled(false);
												edytuj.setEnabled(false);
												usun.setEnabled(false);
											}
										}
		                            });
		
		nowaFV.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new NowaFV();
			}
		});
		
		usun.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				faktury.remove(Math.abs(lista.getSelectedIndex()));
				listModel.removeElementAt(Math.abs(lista.getSelectedIndex()));
			}

			
		});
		
		edytuj.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!faktury.get(lista.getSelectedIndex()).isZamknieta()) {
					edycja = new Edycja();
				}
			}

			
		});
		
		drukuj.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				wydruk = new Wydruk();
			}

			
		});
		
		
		
		fakturowanie.pack();
		fakturowanie.setVisible(true);
		fakturowanie.setLocation(960, 540);
		fakturowanie.setSize(450, 190);
		fakturowanie.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
	}
	
	
	
	private class NowaFV {
		private JFrame nowaFV;
		private int licznik = 0;
		private OknoGL oknoGL;
		private DefaultListModel<String> listaPoz;
		
		
		public NowaFV() {
			listaPoz = new DefaultListModel<String>();
			nowaFV = new JFrame();
			nowaFV.setTitle("Fakturowanie 1.0");
			nowaFV.getContentPane().setLayout(null);
			
			JLabel nazwa = new JLabel("Nazwa");
			nazwa.setBounds(10, 36, 58, 14);
			nowaFV.getContentPane().add(nazwa);
			
			JLabel adres = new JLabel("Adres");
			adres.setBounds(10, 58, 58, 14);
			nowaFV.getContentPane().add(adres);
			
			JLabel nip = new JLabel("NIP");
			nip.setBounds(10, 83, 58, 14);
			nowaFV.getContentPane().add(nip);
			
			JTextField nazwaK = new JTextField();
			nazwaK.setBounds(78, 33, 132, 20);
			nowaFV.getContentPane().add(nazwaK);
			nazwaK.setColumns(10);
			
			JTextField nipK = new JTextField();
			nipK.setBounds(78, 78, 132, 20);
			nowaFV.getContentPane().add(nipK);
			nipK.setColumns(10);
			
			JTextField adresK = new JTextField();
			adresK.setBounds(78, 55, 132, 20);
			nowaFV.getContentPane().add(adresK);
			adresK.setColumns(10);
			
			JLabel lblKontrahent = new JLabel("Kontrahent");
			lblKontrahent.setForeground(Color.BLACK);
			lblKontrahent.setBackground(Color.BLACK);
			lblKontrahent.setHorizontalAlignment(SwingConstants.CENTER);
			lblKontrahent.setBounds(10, 11, 200, 14);
			nowaFV.getContentPane().add(lblKontrahent);
			
			JButton dodajKontrahenta = new JButton("Dodaj kontrahenta");
			dodajKontrahenta.setBounds(10, 108, 200, 23);
			nowaFV.getContentPane().add(dodajKontrahenta);
			
			JLabel dodawaniePoz = new JLabel("Dodawanie pozycji FV");
			dodawaniePoz.setHorizontalAlignment(SwingConstants.CENTER);
			dodawaniePoz.setBounds(10, 142, 200, 14);
			nowaFV.getContentPane().add(dodawaniePoz);
			
			JTextField nazwaP = new JTextField();
			nazwaP.setBounds(78, 167, 132, 20);
			nowaFV.getContentPane().add(nazwaP);
			nazwaP.setColumns(10);
			
			JComboBox<String> miaraP = new JComboBox<String>();
			miaraP.setBounds(78, 198, 132, 20);
			nowaFV.getContentPane().add(miaraP);
			miaraP.addItem(Miara.KG.toString());
			miaraP.addItem(Miara.L.toString());
			miaraP.addItem(Miara.M.toString());
			miaraP.addItem(Miara.M2.toString());
			miaraP.addItem(Miara.SZT.toString());
			
			JLabel lblNewLabel = new JLabel("Nazwa");
			lblNewLabel.setBounds(10, 170, 46, 14);
			nowaFV.getContentPane().add(lblNewLabel);
			
			JTextField iloscSztuk = new JTextField();
			iloscSztuk.setBounds(78, 229, 132, 20);
			nowaFV.getContentPane().add(iloscSztuk);
			iloscSztuk.setColumns(10);
			
			JComboBox<String> podatekP = new JComboBox<String>();
			podatekP.setBounds(78, 291, 132, 20);
			nowaFV.getContentPane().add(podatekP);
			podatekP.addItem(VAT.s00.toString());
			podatekP.addItem(VAT.s05.toString());
			podatekP.addItem(VAT.s08.toString());
			podatekP.addItem(VAT.s23.toString());
			
			JTextField cenaP = new JTextField();
			cenaP.setBounds(78, 260, 132, 20);
			nowaFV.getContentPane().add(cenaP);
			cenaP.setColumns(10);
			
			JLabel nazwaPozycji = new JLabel("Nazwa");
			nazwaPozycji.setBounds(10, 170, 46, 14);
			nowaFV.getContentPane().add(nazwaPozycji);
			
			JLabel miaraPozycji = new JLabel("Miara");
			miaraPozycji.setBounds(10, 201, 46, 14);
			nowaFV.getContentPane().add(miaraPozycji);
			
			JLabel iloscP = new JLabel("Iloœæ");
			iloscP.setBounds(10, 232, 46, 14);
			nowaFV.getContentPane().add(iloscP);
			
			JLabel cenaJednostkowaPozycji = new JLabel("Cena Netto ");
			cenaJednostkowaPozycji.setBounds(10, 263, 58, 14);
			nowaFV.getContentPane().add(cenaJednostkowaPozycji);
			
			JLabel podatek = new JLabel("Podatek");
			podatek.setBounds(10, 294, 46, 14);
			nowaFV.getContentPane().add(podatek);
			
			//DefaultListModel<String> listaPoz = new DefaultListModel<String>();
			//listaPoz.addElement("Nazwa | Miara | Iloœæ szt. | Cena Netto | Podatek VAT");
			
			JList<String> listaPozycji = new JList<String>(listaPoz);
			listaPozycji.setBounds(227, 368, 464, -355);
			listaPozycji.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listaPozycji.setLayoutOrientation(JList.VERTICAL);
			listaPozycji.setVisibleRowCount(-1);
			//nowaFV.getContentPane().add(listaPozycji);
			
			JScrollPane scrollPane = new JScrollPane(listaPozycji);
			scrollPane.setBounds(220, 11, 471, 362);
			nowaFV.getContentPane().add(scrollPane);
			
			JButton zamknijFV = new JButton("Zamknij FV");
			zamknijFV.setBounds(10, 350, 200, 23);
			zamknijFV.setEnabled(false);
			nowaFV.getContentPane().add(zamknijFV);
			
			JButton dodajPozycje = new JButton("Dodaj pozycjê");
			dodajPozycje.setBounds(10, 322, 200, 23);
			dodajPozycje.setEnabled(false);
			nowaFV.getContentPane().add(dodajPozycje);
			
			
			dodajKontrahenta.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					kontrahent = new Kontrahent(nazwaK.getText()+" "+adresK.getText(), nipK.getText());
					faktura = new Faktura(kontrahent);
					faktury.add(faktura);
					
					JOptionPane.showMessageDialog(nowaFV,
						    "Kontrahent poprawnie dodany.\n"+nazwaK.getText()+", NIP: "+nipK.getText());
					listModel.addElement(faktura.getNr()+" | "+nazwaK.getText()+" | "+adresK.getText()+" | "+ nipK.getText());
					
					dodajPozycje.setEnabled(true);
				}
			});
			
			dodajPozycje.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(faktura.isZamknieta()==false) {
					if(miaraP.getSelectedIndex() == 0) {
						miara = Miara.KG;
					}
					else if(miaraP.getSelectedIndex() == 1) {
						miara = Miara.L;
					}
					else if(miaraP.getSelectedIndex() == 2) {
						miara = Miara.M;
					}
					else if(miaraP.getSelectedIndex() == 3) {
						miara = Miara.M2;
					}
					else if(miaraP.getSelectedIndex() == 4) {
						miara = Miara.SZT;
					}
					if(podatekP.getSelectedIndex() == 0) {
						podatekv = VAT.s00;
					}
					else if(podatekP.getSelectedIndex() == 1) {
						podatekv = VAT.s05;
					}
					else if(podatekP.getSelectedIndex() == 2) {
						podatekv = VAT.s08;
					}
					else if(podatekP.getSelectedIndex() == 3) {
						podatekv = VAT.s23;
					}
					faktura.addPozycja(nazwaP.getText(), miara, Double.parseDouble(iloscSztuk.getText()), Double.parseDouble(cenaP.getText()), podatekv);
					
					listaPoz.addElement(faktura.getPozycja(licznik++).toString());
					zamknijFV.setEnabled(true);
				}}
			});
			
			zamknijFV.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					faktura.zamknij();
					dodajPozycje.setEnabled(false);
				}
			});
			
			
			 
			nowaFV.pack();
			nowaFV.setVisible(true);
			nowaFV.setLocation(800, 440);
			nowaFV.setSize(717, 420);
			nowaFV.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		}	//NowaFV()
	}	//class NowaFV
	
	
	private class Wydruk extends JFrame{
		private JFrame wydruk;
		
		public Wydruk() {
			wydruk = new JFrame();
			wydruk.setTitle(faktura.getNr());
			wydruk.setLayout(new FlowLayout());
			
			
			JTextPane textPane = new JTextPane();
			StyledDocument doc = textPane.getStyledDocument();
			MutableAttributeSet standard = new SimpleAttributeSet();

			StyleConstants.setAlignment(standard, StyleConstants.ALIGN_CENTER);
			StyleConstants.setLeftIndent(standard, 40);
			StyleConstants.setRightIndent(standard, 40);

			Color szary = new Color(224, 226, 235);
			doc.setParagraphAttributes(0, 0, standard, true);
			textPane.setBackground(szary);
			textPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
			int index = lista.getSelectedIndex();
			
			textPane.setText(faktury.get(index).toString());

			wydruk.getContentPane().add(textPane);
			
			wydruk.pack();
			wydruk.setVisible(true);
			wydruk.setLocation(200, 100);
			wydruk.setSize(850, 900);
			wydruk.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		}	//Wydruk()
	}	//class Wydruk
	
	
	
	///////////////////////////////////////////
	
	
	
	
	
	
	
	private class Edycja extends JFrame{
		private JFrame edycja;
		private int licznik =0;
		//private OknoGL oknoGL;
		
		public Edycja() {
			
			faktura = faktury.get(lista.getSelectedIndex());
			licznik = faktura.getPozycje().size();
			edycja = new JFrame();
			edycja.setTitle("Fakturowanie 1.0");
			edycja.getContentPane().setLayout(null);
			
			JLabel nazwa = new JLabel("Nazwa");
			nazwa.setBounds(10, 36, 58, 14);
			edycja.getContentPane().add(nazwa);
			
			JLabel adres = new JLabel("Adres");
			adres.setBounds(10, 58, 58, 14);
			edycja.getContentPane().add(adres);
			
			JLabel nip = new JLabel("NIP");
			nip.setBounds(10, 83, 58, 14);
			edycja.getContentPane().add(nip);
			
			JTextField nazwaK = new JTextField();
			nazwaK.setBounds(78, 33, 132, 20);
			nazwaK.setEnabled(false);
			edycja.getContentPane().add(nazwaK);
			nazwaK.setColumns(10);
			
			JTextField nipK = new JTextField();
			nipK.setBounds(78, 78, 132, 20);
			nipK.setEnabled(false);
			edycja.getContentPane().add(nipK);
			nipK.setColumns(10);
			
			JTextField adresK = new JTextField();
			adresK.setBounds(78, 55, 132, 20);
			adresK.setEnabled(false);
			edycja.getContentPane().add(adresK);
			adresK.setColumns(10);
			
			JLabel lblKontrahent = new JLabel("Kontrahent");
			lblKontrahent.setForeground(Color.BLACK);
			lblKontrahent.setBackground(Color.BLACK);
			lblKontrahent.setHorizontalAlignment(SwingConstants.CENTER);
			lblKontrahent.setBounds(10, 11, 200, 14);
			edycja.getContentPane().add(lblKontrahent);
			
			JButton dodajKontrahenta = new JButton("Dodaj kontrahenta");
			dodajKontrahenta.setBounds(10, 108, 200, 23);
			dodajKontrahenta.setEnabled(false);
			edycja.getContentPane().add(dodajKontrahenta);
			
			JLabel dodawaniePoz = new JLabel("Dodawanie pozycji FV");
			dodawaniePoz.setHorizontalAlignment(SwingConstants.CENTER);
			dodawaniePoz.setBounds(10, 142, 200, 14);
			edycja.getContentPane().add(dodawaniePoz);
			
			JTextField nazwaP = new JTextField();
			nazwaP.setBounds(78, 167, 132, 20);
			edycja.getContentPane().add(nazwaP);
			nazwaP.setColumns(10);
			
			JComboBox<String> miaraP = new JComboBox<String>();
			miaraP.setBounds(78, 198, 132, 20);
			edycja.getContentPane().add(miaraP);
			miaraP.addItem(Miara.KG.toString());
			miaraP.addItem(Miara.L.toString());
			miaraP.addItem(Miara.M.toString());
			miaraP.addItem(Miara.M2.toString());
			miaraP.addItem(Miara.SZT.toString());
			
			JLabel lblNewLabel = new JLabel("Nazwa");
			lblNewLabel.setBounds(10, 170, 46, 14);
			edycja.getContentPane().add(lblNewLabel);
			
			JTextField iloscSztuk = new JTextField();
			iloscSztuk.setBounds(78, 229, 132, 20);
			edycja.getContentPane().add(iloscSztuk);
			iloscSztuk.setColumns(10);
			
			JComboBox<String> podatekP = new JComboBox<String>();
			podatekP.setBounds(78, 291, 132, 20);
			edycja.getContentPane().add(podatekP);
			podatekP.addItem(VAT.s00.toString());
			podatekP.addItem(VAT.s05.toString());
			podatekP.addItem(VAT.s08.toString());
			podatekP.addItem(VAT.s23.toString());
			
			JTextField cenaP = new JTextField();
			cenaP.setBounds(78, 260, 132, 20);
			edycja.getContentPane().add(cenaP);
			cenaP.setColumns(10);
			
			JLabel nazwaPozycji = new JLabel("Nazwa");
			nazwaPozycji.setBounds(10, 170, 46, 14);
			edycja.getContentPane().add(nazwaPozycji);
			
			JLabel miaraPozycji = new JLabel("Miara");
			miaraPozycji.setBounds(10, 201, 46, 14);
			edycja.getContentPane().add(miaraPozycji);
			
			JLabel iloscP = new JLabel("Iloœæ");
			iloscP.setBounds(10, 232, 46, 14);
			edycja.getContentPane().add(iloscP);
			
			JLabel cenaJednostkowaPozycji = new JLabel("Cena Netto ");
			cenaJednostkowaPozycji.setBounds(10, 263, 58, 14);
			edycja.getContentPane().add(cenaJednostkowaPozycji);
			
			JLabel podatek = new JLabel("Podatek");
			podatek.setBounds(10, 294, 46, 14);
			edycja.getContentPane().add(podatek);
			
			DefaultListModel<String> listaPoz = new DefaultListModel<String>();
			//listaPoz.addElement("Nr poz. | Nazwa | Miara | Iloœæ szt. | Cena Netto | Podatek VAT");
			
			for(int i =0; i < faktura.getPozycje().size(); i++) {
				listaPoz.addElement(faktura.getPozycja(i));
			}
			
			JList<String> listaPozycji = new JList<String>(listaPoz);
			listaPozycji.setBounds(227, 368, 464, -355);
			listaPozycji.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listaPozycji.setLayoutOrientation(JList.VERTICAL);
			listaPozycji.setVisibleRowCount(-1);
			//nowaFV.getContentPane().add(listaPozycji);
			
			JScrollPane scrollPane = new JScrollPane(listaPozycji);
			scrollPane.setBounds(220, 11, 471, 362);
			edycja.getContentPane().add(scrollPane);
			
			JButton zamknijFV = new JButton("Zamknij FV");
			zamknijFV.setBounds(10, 350, 200, 23);
			//zamknijFV.setEnabled(false);
			edycja.getContentPane().add(zamknijFV);
			
			JButton dodajPozycje = new JButton("Dodaj pozycjê");
			dodajPozycje.setBounds(10, 322, 200, 23);
			//dodajPozycje.setEnabled(false);
			edycja.getContentPane().add(dodajPozycje);
			
			
			dodajPozycje.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(faktura.isZamknieta()==false) {
					if(miaraP.getSelectedIndex() == 0) {
						miara = Miara.KG;
					}
					else if(miaraP.getSelectedIndex() == 1) {
						miara = Miara.L;
					}
					else if(miaraP.getSelectedIndex() == 2) {
						miara = Miara.M;
					}
					else if(miaraP.getSelectedIndex() == 3) {
						miara = Miara.M2;
					}
					else if(miaraP.getSelectedIndex() == 4) {
						miara = Miara.SZT;
					}
					if(podatekP.getSelectedIndex() == 0) {
						podatekv = VAT.s00;
					}
					else if(podatekP.getSelectedIndex() == 1) {
						podatekv = VAT.s05;
					}
					else if(podatekP.getSelectedIndex() == 2) {
						podatekv = VAT.s08;
					}
					else if(podatekP.getSelectedIndex() == 3) {
						podatekv = VAT.s23;
					}
					faktura.addPozycja(nazwaP.getText(), miara, Double.parseDouble(iloscSztuk.getText()), Double.parseDouble(cenaP.getText()), podatekv);
					
					listaPoz.addElement(faktura.getPozycja(licznik++).toString());
					zamknijFV.setEnabled(true);
				}}
			});
			
			zamknijFV.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					faktura.zamknij();
					dodajPozycje.setEnabled(false);
				}
			});
			
			
			 
			edycja.pack();
			edycja.setVisible(true);
			edycja.setLocation(960, 540);
			edycja.setSize(717, 420);
			edycja.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		}	//Edycja()
	}	//Edycja
	
	
	//////////////////////////////////////////////
	

}	// OknoGL
