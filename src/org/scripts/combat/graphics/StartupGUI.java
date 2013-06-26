package org.scripts.combat.graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.scripts.combat.CombatScript;
import org.scripts.combat.Loot;
import org.scripts.combat.NPCType;
import org.scripts.combat.util.AutomaticPathMaker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;

/**
 * The GUI shown on startup.
 * @author Albert's_Computer
 *
 */
public class StartupGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4790856485575790217L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the frame.
	 */
	public StartupGUI() {
		setTitle("Thock's Combat Script");
		setBounds(100, 100, 450, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectNpcsTo = new JLabel("Select NPC(s) to fight");
		lblSelectNpcsTo.setBounds(10, 12, 111, 26);
		contentPane.add(lblSelectNpcsTo);
		
		List<NPCType> npcs = new LinkedList<NPCType>();
		NPCType temp = null;
		for (NPC npc : NPCs.getLoaded(new Filter<NPC>() {

			@Override
			public boolean accept(NPC arg0) {
				return arg0 != null && arg0.getLevel() > 2;
			}
			
		})) {
			temp = new NPCType(npc.getName(), npc.getLevel());
			if (!npcs.contains(temp))
				npcs.add(temp);
		}
		System.out.println(npcs.size());
		String[] npcNamesAndLevels = new String[npcs.size()];
		for (int i = 0; i < npcs.size(); i++) {
			npcNamesAndLevels[i] = npcs.get(i).getName() + "::level - " + npcs.get(i).getLevel();
		}
		final String[] npcList = npcNamesAndLevels;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(131, 12, 293, 84);
		contentPane.add(scrollPane);
		JList<Object> list = new JList<Object>();
		scrollPane.setViewportView(list);
		list.setModel(new AbstractListModel<Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8857896545065046889L;
			String[] values = npcList;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		final JList<Object> list1 = list;
		
		JLabel lblEnterFoodId = new JLabel("Enter food id to eat");
		lblEnterFoodId.setBounds(10, 106, 111, 14);
		contentPane.add(lblEnterFoodId);
		
		textField = new JTextField();
		textField.setText("385");
		textField.setBounds(131, 103, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterItemIds = new JLabel("Enter item ids to loot. Seperate with semi colons");
		lblEnterItemIds.setBounds(10, 131, 232, 14);
		contentPane.add(lblEnterItemIds);
		
		textField_1 = new JTextField();
		textField_1.setBounds(252, 128, 172, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Start Script");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					for (int i = 0; i < list1.getModel().getSize(); i++) {
						CombatScript.getInstance().getVars().addNpcTypeToAttack(new NPCType(((String) list1.getModel().getElementAt(i)).substring(0, ((String) list1.getModel().getElementAt(i)).indexOf("::level - ")), 
								Integer.parseInt(((String) list1.getModel().getElementAt(i)).substring(((String) list1.getModel().getElementAt(i)).indexOf("::level - ") + 10))));
					}
					if (textField_1.getText().length() > 0) {
						for (String lootId : textField_1.getText().split(";")) {
							CombatScript.getInstance().getVars().addLootToLoot(new Loot(Integer.parseInt(lootId)));
						}
					}
					CombatScript.getInstance().getVars().setFoodToEat(Integer.parseInt(textField.getText()));
				} catch (Exception e) {
					CombatScript.getInstance().log.severe("Error occured whilst loading variables from GUI.");
					e.printStackTrace();
				}
				dispose();
			}
		});
		btnNewButton.setBounds(10, 295, 414, 23);
		contentPane.add(btnNewButton);
		
		final JLabel lblState = new JLabel("Nothing");
		lblState.setBounds(150, 255, 274, 14);
		contentPane.add(lblState);
		
		final AutomaticPathMaker pathMaker = new AutomaticPathMaker();
		
		JButton btnStartBankPath = new JButton("Start Bank Path Generation");
		btnStartBankPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pathMaker.startMakingPath(CombatScript.getInstance().getContainer());
				lblState.setText("Generating Path");
			}
		});
		btnStartBankPath.setBounds(10, 156, 187, 23);
		contentPane.add(btnStartBankPath);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(10, 190, 414, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnStopBankPath = new JButton("Stop Bank Path Generation");
		btnStopBankPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pathMaker.setStop(true);
				CombatScript.getInstance().getVars().setBankPath(pathMaker.getPath());
				lblState.setText("Path Generated");
				for (Tile t : pathMaker.getPath()) {
					textField_2.setText(textField_2.getText() + " (" + t.getX() + "," + t.getY() + ")");
				}
			}
		});
		btnStopBankPath.setBounds(226, 156, 198, 23);
		contentPane.add(btnStopBankPath);
		
		JButton btnResetBankPath = new JButton("Reset Bank Path Generation");
		btnResetBankPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pathMaker.reset();
				lblState.setText("Path Reset And Cleared");
				textField_2.setText("");
				CombatScript.getInstance().getVars().clearBankPath();
			}
		});
		btnResetBankPath.setBounds(10, 221, 414, 23);
		contentPane.add(btnResetBankPath);
		
		JLabel lblPathGenerationState = new JLabel("Bank Path Generation State: ");
		lblPathGenerationState.setBounds(10, 255, 156, 14);
		contentPane.add(lblPathGenerationState);
	}
}
