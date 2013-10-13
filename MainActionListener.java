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
