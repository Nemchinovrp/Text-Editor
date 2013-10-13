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
