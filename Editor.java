package text.mma;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.print.*;
import java.text.*;

@SuppressWarnings("serial")
public class Editor extends JFrame {
	final int height = 100;
	final int width = 400;
	// Глобальные переменные
	private String filename, worktext;
	private JTextArea text;
	private JComboBox<String> fonts, styles, color;
	private Hashtable<String, Action> table;

	public static void main(String[] args) {
		Editor e = new Editor("Текстовый редактор");
		e.setLocation(100, 100);
		e.pack();
		e.setVisible(true);

	}

	Editor(String titel) {
		super(titel);

		JMenuBar menulist = new JMenuBar();
		setJMenuBar(menulist);

		JMenu menu1 = new JMenu("Файл");
		JMenuItem item1_1 = new JMenuItem("Открыть");
		JMenuItem item1_2 = new JMenuItem("Сохранить");
		JMenuItem item1_3 = new JMenuItem("Печать");
		JMenuItem item1_4 = new JMenuItem("Закрыть");
		menu1.add(item1_1);
		menu1.add(item1_2);
		menu1.add(item1_3);
		menu1.add(item1_4);
		menulist.add(menu1);

		JMenu menu2 = new JMenu("Правка");
		JMenuItem item2_1 = new JMenuItem("Открыть");
		JMenuItem item2_2 = new JMenuItem("Сохранить");
		JMenuItem item2_3 = new JMenuItem("Печать");
		menu2.add(item2_1);
		menu2.add(item2_2);
		menu2.add(item2_3);
		menulist.add(menu2);

		JMenu menu3 = new JMenu("Поиск");
		JMenuItem item3_1 = new JMenuItem("Найти");
		menu3.add(item3_1);
		menulist.add(menu3);
		// Панель для области текста
		JPanel panelt = new JPanel();
		panelt.setLayout(new BorderLayout());

		text = new JTextArea(height, width);
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().add(text);

		// Внутрення панель для размешения списков
		JPanel panels = new JPanel();
		panels.setLayout(new GridLayout(1, 4));

		fonts = new JComboBox<String>();
		fonts.addItem(Font.SERIF);
		fonts.addItem(Font.SANS_SERIF);
		fonts.addItem(Font.MONOSPACED);
		
		styles = new JComboBox<String>();
		styles.addItem("обычный");
		styles.addItem("курсив");
		styles.addItem("полужирный");
		
		color = new JComboBox<String>();
		color.addItem("Черный");
		color.addItem("Красный");
		color.addItem("Зеленый");
		color.addItem("Синий");
		color.addItem("Золотой");
		color.addItem("Небесный");
		color.addItem("Пурпурный");
		
		panels.add(fonts);
		panels.add(color);
		panels.add(styles);
		
		panelt.setPreferredSize(new Dimension(500,400));
		panelt.add("Center", scroll);
		panelt.add("North", panels);
		add(panelt);
		
		fonts.setSelectedItem("SansSerif");
		styles.setSelectedItem("normal");
		color.setSelectedIndex(0);
		fontListener();
		
			class MainItemAdapter implements ItemListener{

				public void itemStateChanged(ItemEvent b) {
					fontListener();
					}
			}
		class MainActionListener implements ActionListener{
			public void actionPerformed(ActionEvent a) {
			String Label;
			Label = a.getActionCommand();
			if(Label.equals("Открыть"))
				try {
					fileLoad();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(Label.equals("Сохранить"))
				try {
					fileRecord();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(Label.equals("Печать"))
				filePrint();
			if(Label.equals("Закрыть"))
				System.exit(0);
			if(Label.equals("Найти"))
				stringSearch();
			}
			}
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		MainActionListener actionlistener = new MainActionListener();
		item1_1.addActionListener(actionlistener);
		item1_2.addActionListener(actionlistener);
		item1_3.addActionListener(actionlistener);
		item1_4.addActionListener(actionlistener);
		item3_1.addActionListener(actionlistener);
		
		MainItemAdapter itemlistener = new MainItemAdapter();
		fonts.addItemListener(itemlistener);
		color.addItemListener(itemlistener);
		styles.addItemListener(itemlistener);
		
		table = new Hashtable<String, Action>();
		Action[] actionsArray = text.getActions();
		for(int i=0; i<actionsArray.length; i++){
			Action c = actionsArray[i];
			table.put((String) c.getValue(Action.NAME), c);
			}
		item2_1.addActionListener(table.get(DefaultEditorKit.cutAction));
		item2_2.addActionListener(table.get(DefaultEditorKit.copyAction));
		item2_3.addActionListener(table.get(DefaultEditorKit.pasteAction));
	}
	void fontListener(){
		int fontstyle;
		String colors, fontname, style;
		fontname = (String) fonts.getSelectedItem();
		style = (String) styles.getSelectedItem();
		colors = (String) color.getSelectedItem();
		
		if(colors.equals("Черный"))
			text.setForeground(Color.black);
		if(colors.equals("Красный"))
			text.setForeground(Color.red);
		if(colors.equals("Зеленый"))
			text.setForeground(Color.green);
		if(colors.equals("Синий"))
			text.setForeground(Color.blue);
		if(colors.equals("Пумпурный"))
			text.setForeground(Color.magenta);
		if(colors.equals("Голубой"))
			text.setForeground(Color.cyan);
		if(colors.equals("Желтый"))
			text.setForeground(Color.yellow);
		
		fontstyle = Font.PLAIN;
		
		if(style.equals("курсив"))
			fontstyle += Font.ITALIC;
		if(style.equals("полужирный"))
			fontstyle += Font.BOLD;
			text.setFont(new Font(fontname, fontstyle, 14));
			}
	void fileLoad() throws FileNotFoundException, IOException{
			FileDialog d = new FileDialog(this, "Открыть", FileDialog.LOAD);
			d.setVisible(true);
			filename = d.getDirectory();
			filename += d.getFile();
			if(filename ==null)
				return;
			StringBuffer buf = new StringBuffer(height*width);
			try(FileReader input = new FileReader(filename);){
				char mark = 0;
				int read;
				@SuppressWarnings("unused")
				int line = 0;
				boolean more = true;
				while(more){
					read = input.read();
					if(read==-1){
						more = false;
						continue;
					}
					line = (char) read;
					buf.append(mark);
					}
				worktext = new String(buf);
				text.setText(worktext);
				text.setCaretPosition(0);
			}
			catch(EOFException e){}
			catch(FileNotFoundException e){
				System.err.println("Файл отсутствует или не читается");
				filename = null;
				}
			}
	void fileRecord() throws IOException{

		int mark, i;
		FileDialog d = new FileDialog(this, "Сохранить", FileDialog.SAVE);
		d.setVisible(true);
		filename = d.getDirectory();
		filename += d.getFile();
		if(filename==null)
			return;
		try(FileWriter writer = new FileWriter(filename);){
			worktext =text.getText();
			for(i=0;i<worktext.length();i++){
				mark = (int) worktext.charAt(i);
				writer.write(mark);
			}
		}
		catch(IOException e){
			System.err.println("Ошибка при сохранении файла"+filename);
			filename=null;
		}
	}
	void filePrint(){
		if(filename == null)
			filename = "Без имени";
		try{
			text.print(new MessageFormat(filename), new MessageFormat("Страница {0}"), true, null, null, false);
		}catch(PrinterException e){
			System.err.println("Печать невозможна");
			}
	}
	void stringSearch(){
		String suchstring;
		PointDialog point;
		int index;
		
		point = new PointDialog(this, "Найти");
		point.setLocation(150,150);
		point.pack();
		point.setVisible(true);
		suchstring = point.getString();
		
		if(suchstring == null)
			return;
		
		worktext = text.getText();
		index = worktext.indexOf(suchstring);
		if(index ==-1){
			JOptionPane.showMessageDialog(null, "Текст не найден", "Meldung", JOptionPane.INFORMATION_MESSAGE);
			
		}else
			text.select(index, index + suchstring.length());
		
	}
	}

	@SuppressWarnings("serial")
	class PointDialog extends JDialog implements ActionListener{
		private JTextField textfield;
		private JButton ok, cut;
		private String suchstring;
		
			PointDialog(JFrame f, String titel){
				super(f, titel,true);
				setResizable(false);
				setLayout(new BorderLayout());
				
				JPanel panel1 = new JPanel();
				JLabel label = new JLabel("Введите строку для поиска");
				panel1.add(label);
				textfield = new JTextField(40);
				panel1.add(textfield);
				add("Center", panel1);
				JPanel panel2 = new JPanel();
				ok = new JButton("ok");
				cut = new JButton("Отмена");
				panel2.add(ok);
				panel2.add(cut);
				add("South", panel2);
				pack();
				ok.addActionListener(this);
				cut.addActionListener(this);
				setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			}

			
			public void actionPerformed(ActionEvent e) {
				String Label;
				Label = e.getActionCommand();
				if(Label.equals("Отмена")){
					suchstring =null;
					setVisible(false);
					return;
			}
			if(Label.equals("ok")){
				suchstring =textfield.getText();
				setVisible(false);
				return;
		}
		
		
			}
			public String getString(){
				return suchstring;
			}
	}
