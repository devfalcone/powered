package quests.Q049_TheRoadHome;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.base.Race;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;

public class Q049_TheRoadHome extends Quest
{
	private static final String qn = "Q049_TheRoadHome";
	
	// NPCs
	private static final int GALLADUCCI = 30097;
	private static final int GENTLER = 30094;
	private static final int SANDRA = 30090;
	private static final int DUSTIN = 30116;
	
	// Items
	private static final int ORDER_DOCUMENT_1 = 7563;
	private static final int ORDER_DOCUMENT_2 = 7564;
	private static final int ORDER_DOCUMENT_3 = 7565;
	private static final int MAGIC_SWORD_HILT = 7568;
	private static final int GEMSTONE_POWDER = 7567;
	private static final int PURIFIED_MAGIC_NECKLACE = 7566;
	private static final int MARK_OF_TRAVELER = 7570;
	private static final int SCROLL_OF_ESCAPE_SPECIAL = 7558;
	
	public Q049_TheRoadHome()
	{
		super(49, qn, "The Road Home");
		
		setItemsIds(ORDER_DOCUMENT_1, ORDER_DOCUMENT_2, ORDER_DOCUMENT_3, MAGIC_SWORD_HILT, GEMSTONE_POWDER, PURIFIED_MAGIC_NECKLACE);
		
		addStartNpc(GALLADUCCI);
		addTalkId(GALLADUCCI, GENTLER, SANDRA, DUSTIN);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30097-03.htm"))
		{
			st.setState(STATE_STARTED);
			st.set("cond", "1");
			st.playSound(QuestState.SOUND_ACCEPT);
			st.giveItems(ORDER_DOCUMENT_1, 1);
		}
		else if (event.equalsIgnoreCase("30094-02.htm"))
		{
			st.set("cond", "2");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.takeItems(ORDER_DOCUMENT_1, 1);
			st.giveItems(MAGIC_SWORD_HILT, 1);
		}
		else if (event.equalsIgnoreCase("30097-06.htm"))
		{
			st.set("cond", "3");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.takeItems(MAGIC_SWORD_HILT, 1);
			st.giveItems(ORDER_DOCUMENT_2, 1);
		}
		else if (event.equalsIgnoreCase("30090-02.htm"))
		{
			st.set("cond", "4");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.takeItems(ORDER_DOCUMENT_2, 1);
			st.giveItems(GEMSTONE_POWDER, 1);
		}
		else if (event.equalsIgnoreCase("30097-09.htm"))
		{
			st.set("cond", "5");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.takeItems(GEMSTONE_POWDER, 1);
			st.giveItems(ORDER_DOCUMENT_3, 1);
		}
		else if (event.equalsIgnoreCase("30116-02.htm"))
		{
			st.set("cond", "6");
			st.playSound(QuestState.SOUND_MIDDLE);
			st.takeItems(ORDER_DOCUMENT_3, 1);
			st.giveItems(PURIFIED_MAGIC_NECKLACE, 1);
		}
		else if (event.equalsIgnoreCase("30097-12.htm"))
		{
			st.takeItems(MARK_OF_TRAVELER, -1);
			st.takeItems(PURIFIED_MAGIC_NECKLACE, 1);
			st.rewardItems(SCROLL_OF_ESCAPE_SPECIAL, 1);
			st.playSound(QuestState.SOUND_FINISH);
			st.exitQuest(false);
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		String htmltext = getNoQuestMsg();
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case STATE_CREATED:
				if (player.getRace() == Race.Dwarf && player.getLevel() >= 3)
				{
					if (st.hasQuestItems(MARK_OF_TRAVELER))
						htmltext = "30097-02.htm";
					else
						htmltext = "30097-01.htm";
				}
				else
					htmltext = "30097-01a.htm";
				break;
			
			case STATE_STARTED:
				int cond = st.getInt("cond");
				switch (npc.getNpcId())
				{
					case GALLADUCCI:
						if (cond == 1)
							htmltext = "30097-04.htm";
						else if (cond == 2)
							htmltext = "30097-05.htm";
						else if (cond == 3)
							htmltext = "30097-07.htm";
						else if (cond == 4)
							htmltext = "30097-08.htm";
						else if (cond == 5)
							htmltext = "30097-10.htm";
						else if (cond == 6)
							htmltext = "30097-11.htm";
						break;
					
					case GENTLER:
						if (cond == 1)
							htmltext = "30094-01.htm";
						else if (cond > 1)
							htmltext = "30094-03.htm";
						break;
					
					case SANDRA:
						if (cond == 3)
							htmltext = "30090-01.htm";
						else if (cond > 3)
							htmltext = "30090-03.htm";
						break;
					
					case DUSTIN:
						if (cond == 5)
							htmltext = "30116-01.htm";
						else if (cond == 6)
							htmltext = "30116-03.htm";
						break;
				}
				break;
			
			case STATE_COMPLETED:
				htmltext = getAlreadyCompletedMsg();
				break;
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q049_TheRoadHome();
	}
}