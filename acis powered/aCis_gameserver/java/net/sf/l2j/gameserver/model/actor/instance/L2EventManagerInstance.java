package net.sf.l2j.gameserver.model.actor.instance;

import Extensions.Events.Phoenix.EventManager;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class L2EventManagerInstance extends L2NpcInstance
{
	private int objectId;
	
	public L2EventManagerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
		this.objectId = objectId;
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("reg"))
			EventManager.getInstance().registerPlayer(player);
		else if (command.startsWith("unreg"))
			EventManager.getInstance().unregisterPlayer(player);
		if (command.startsWith("list"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			StringBuilder sb = new StringBuilder();
			sb.append("<html><body><center>Select an event to vote for:<br>");
			int i = 0;
			for (String name : EventManager.getInstance().getEventNames())
			{
				i++;
				sb.append(" <a action=\"bypass -h npc_" + objectId + "_" + i + "\">- " + name + " -</a>  <br>");
			}
			sb.append("</center></body></html>");
			html.setHtml(sb.toString());
			player.sendPacket(html);
		}
		else
			EventManager.getInstance().addVote(player, Integer.parseInt(command));
	}
}