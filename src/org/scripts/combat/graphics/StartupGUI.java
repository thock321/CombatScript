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
		setBounds(100, 100, 542, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectNpcsTo = new JLabel("Select NPC(s) to fight");
		lblSelectNpcsTo.setBounds(56, 43, 111, 26);
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
		
		JLabel lblEnterFoodId = new JLabel("Enter food id to eat");
		lblEnterFoodId.setBounds(56, 141, 111, 14);
		contentPane.add(lblEnterFoodId);
		
		textField = new JTextField();
		textField.setBounds(171, 138, 86, 20);
		textField.setText("385");
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterItemIds = new JLabel("Enter item ids to loot. Seperate with semi colons");
		lblEnterItemIds.setBounds(56, 166, 232, 14);
		contentPane.add(lblEnterItemIds);
		
		textField_1 = new JTextField();
		textField_1.setBounds(298, 163, 172, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		final JLabel lblState = new JLabel("Nothing");
		lblState.setBounds(206, 293, 274, 14);
		contentPane.add(lblState);
		
		final AutomaticPathMaker pathMaker = new AutomaticPathMaker();
		
		JButton btnStartBankPath = new JButton("Start Bank Path Generation");
		btnStartBankPath.setBounds(55, 194, 187, 23);
		btnStartBankPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pathMaker.startMakingPath(CombatScript.getInstance().getContainer());
				lblState.setText("Generating Path");
			}
		});
		contentPane.add(btnStartBankPath);

		textField_2 = new JTextField();
		textField_2.setBounds(56, 228, 414, 20);
		textField_2.setEditable(false);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnStopBankPath = new JButton("Stop Bank Path Generation");
		btnStopBankPath.setBounds(272, 194, 198, 23);
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
		contentPane.add(btnStopBankPath);
		
		JButton btnResetBankPath = new JButton("Reset Bank Path Generation");
		btnResetBankPath.setBounds(56, 259, 414, 23);
		btnResetBankPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pathMaker.reset();
				lblState.setText("Path Reset And Cleared");
				textField_2.setText("");
				CombatScript.getInstance().getVars().clearBankPath();
			}
		});
		contentPane.add(btnResetBankPath);
		
		JLabel lblPathGenerationState = new JLabel("Bank Path Generation State: ");
		lblPathGenerationState.setBounds(61, 293, 156, 14);
		contentPane.add(lblPathGenerationState);
		
		JLabel lblNotePleaseWalk = new JLabel("NOTE: Please RETURN TO THE AREA YOU STARTED THE SCRIPT after path generation.");
		lblNotePleaseWalk.setBounds(56, 318, 424, 14);
		contentPane.add(lblNotePleaseWalk);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(179, 48, 291, 82);
		contentPane.add(scrollPane);
		final JList<Object> list = new JList<Object>();
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
		JButton btnNewButton = new JButton("Start Script");
		btnNewButton.setBounds(56, 343, 414, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					for (int i = 0; i < list.getModel().getSize(); i++) {
						CombatScript.getInstance().getVars().addNpcTypeToAttack(new NPCType(((String) list.getModel().getElementAt(i)).substring(0, 
								((String) list.getModel().getElementAt(i)).indexOf("::level - ")), 
								Integer.parseInt(((String) list.getModel().getElementAt(i)).substring(((String) list.getModel().getElementAt(i))
										.indexOf("::level - ") + 10))));
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
		contentPane.add(btnNewButton);
	}
}
