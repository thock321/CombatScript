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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractListModel;

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
		setBounds(100, 100, 450, 300);
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
		JList<Object> list = new JList<Object>();
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
		list.setBounds(131, 12, 111, 84);
		contentPane.add(list);
		
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
		
		JLabel lblEnterPathFrom = new JLabel("Enter path from monsters to bank (Leave blank if you don't want to bank)");
		lblEnterPathFrom.setBounds(10, 156, 414, 14);
		contentPane.add(lblEnterPathFrom);
		
		JLabel lblForExampleNew = new JLabel("For example: new Tile(0, 0, 0);new Tile(20, 20, 0);new Tile(40, 40, 0)");
		lblForExampleNew.setBounds(10, 172, 345, 14);
		contentPane.add(lblForExampleNew);
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 197, 414, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Start Script");
		final JList<Object> list1 = list;
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
					if (textField_2.getText() != null && textField_2.getText().length() > 0) {
						String[] tiles = textField_2.getText().split(";");
						Tile[] bankPath = new Tile[tiles.length];
						for (int i = 0; i < bankPath.length; i++) {
								tiles[i] = tiles[i].replace(" ", "");
								bankPath[i] = new Tile(Integer.parseInt(tiles[i].substring(7, tiles[i].indexOf(","))), 
										Integer.parseInt(tiles[i].substring(tiles[i].indexOf(",") + 1, tiles[i].indexOf(",", tiles[i].indexOf(",")))), 
												Integer.parseInt(tiles[i].substring(tiles[i].indexOf(",", tiles[i].indexOf(",")), tiles[i].length() - 1)));
						}
						CombatScript.getInstance().getVars().setBankPath(bankPath);
					}
				} catch (Exception e) {
					CombatScript.getInstance().log.severe("Error occured whilst loading variables from GUI.");
					e.printStackTrace();
				}
				dispose();
			}
		});
		btnNewButton.setBounds(10, 228, 414, 23);
		contentPane.add(btnNewButton);
	}
}
