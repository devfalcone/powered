package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.TradeList;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.EnchantResult;

public final class TradeDone extends L2GameClientPacket
{
	private int _response;
	
	@Override
	protected void readImpl()
	{
		_response = readD();
	}
	
	@Override
	protected void runImpl()
	{
		final L2PcInstance player = getClient().getActiveChar();
		if (player == null)
			return;
		
		final TradeList trade = player.getActiveTradeList();
		if (trade == null)
		{
			return;
		}
		
		if (trade.isLocked())
			return;
		
		if (_response == 1)
		{
			final L2PcInstance owner = trade.getOwner();
			final L2PcInstance partner = trade.getPartner();
			
			// Trade owner not found, or owner is different of packet sender.
			if (owner == null || !owner.equals(player))
				return;
			
			// Trade partner not found, cancel trade
			if (partner == null || L2World.getInstance().getPlayer(partner.getObjectId()) == null)
			{
				player.cancelActiveTrade();
				player.sendPacket(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
				return;
			}
			
			if (!player.getAccessLevel().allowTransaction())
			{
				player.cancelActiveTrade();
				player.sendMessage("Transactions are disabled for your Access Level.");
				return;
			}
			
			if (player.isSubmitingPin())
					{
						player.sendMessage("Unable to do any action while PIN is not submitted");
						return;
					}
			
			// Sender under enchant process, close it.
			if (owner.getActiveEnchantItem() != null)
			{
				owner.setActiveEnchantItem(null);
				owner.sendPacket(EnchantResult.CANCELLED);
				owner.sendPacket(SystemMessageId.ENCHANT_SCROLL_CANCELLED);
			}
			
			// Partner under enchant process, close it.
			if (partner.getActiveEnchantItem() != null)
			{
				partner.setActiveEnchantItem(null);
				partner.sendPacket(EnchantResult.CANCELLED);
				partner.sendPacket(SystemMessageId.ENCHANT_SCROLL_CANCELLED);
			}
			
			trade.confirm();
		}
		else
			player.cancelActiveTrade();
	}
}